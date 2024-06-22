package todolist.domain.task;

import todolist.infrastructure.exception.DomainErrorCode;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@QuarkusTest
class TaskServiceTest {

    @Inject
    TaskService taskService;

    Task firstTask = new Task("read", LocalDate.now(), TaskStatus.SCHEDULED);
    Task seconedTask = new Task("sleep", LocalDate.now(), TaskStatus.SCHEDULED);
    Task thirdTask = new Task("eat", LocalDate.now().plusDays(7L), TaskStatus.CANCELED);

    @BeforeEach
    void setup() {
        taskService.deleteAll();
        taskService.createTask(firstTask);
    }

    @Test
    void createTaskButAlreadyExist() {
        taskService.createTask(firstTask);
        assertThat(taskService.findAll()).containsOnly(firstTask);
    }

    @Test
    void createTaskButNull() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() -> taskService.createTask(null))
                .withMessage("Can't add new task because task is null.");
    }

    @Test
    void deleteTask() {
        taskService.deleteTask(firstTask);
        assertThat(taskService.findAll()).doesNotContain(firstTask);
    }

    @Test
    void deleteTaskButTaskNotFound() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() ->  taskService.deleteTask(seconedTask))
                .withMessage("Can't delete task because it does not exist.");
    }

    @Test
    void updateTask() {
        taskService.updateTask(firstTask, thirdTask);
        assertThat(taskService.findAll()).containsOnly(thirdTask);
    }

    @Test
    void updateTaskButNewTaskIsNull() {
        taskService.updateTask(firstTask, null);
        assertThat(taskService.findAll()).containsOnly(firstTask);
    }

    @Test
    void updateTaskButTaskNotFound() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() ->  taskService.updateTask(seconedTask, thirdTask))
                .withMessage("Can't update task because it does not exist.");
    }

    @Test
    void updateTaskButElapsedExecutionDate() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() ->  taskService.updateTask(firstTask, new Task("read more", LocalDate.now().minusDays(7L), TaskStatus.SCHEDULED)))
                .withMessage(DomainErrorCode.TASK_INVALID_EXECUTION_DATE.getMessage());
    }

    @Test
    void updateTaskButInvalidDescription() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() ->  taskService.updateTask(firstTask, new Task(null, LocalDate.now(), TaskStatus.SCHEDULED)))
                .withMessage(DomainErrorCode.TASK_INVALID_DESCRIPTION.getMessage());
    }
}
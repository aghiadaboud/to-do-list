package fida.domain.task;

import fida.infrastructure.exception.DomainErrorCode;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class TaskService {

    private static final Map<Task, Task> tasks = new TreeMap<>();

    public Set<Task> findAll() {
        return tasks.keySet();
    }

    public void deleteAll() {
        tasks.clear();

        log.info("Deleted all tasks");
    }

    public TaskStatus[] findAllTaskStatuses() {
        return TaskStatus.values();
    }

    public Task createTask(Task task) {
        if (task == null) {
            throw new TaskException("Can't add new task because task is null.", DomainErrorCode.TASK_IS_NULL);
        }
        tasks.put(task, task);

        log.info("Created task {}", task);

        return task;
    }

    public void deleteTask(Task task) {

        if(!tasks.containsKey(task)) {
            throw new TaskException("Can't delete task because it does not exist.", DomainErrorCode.TASK_NOT_FOUND);
        }

        tasks.remove(task);
        log.info("Deleted task {}", task);
    }

    public Task updateTask(Task oldTask, Task newTask) {
        if (!tasks.containsKey(oldTask)) {
            throw new TaskException("Can't update task because it does not exist.", DomainErrorCode.TASK_NOT_FOUND);
        }
        if (newTask == null) {
            log.info("Task not updated because new-task is null");
            return oldTask;
        }

        tasks.remove(oldTask);

        Task mergedTask = mergeTasks(oldTask, newTask);
        tasks.put(mergedTask, mergedTask);

        log.info("Updated task {} to {}", oldTask, mergedTask);

        return mergedTask;
    }

    private Task mergeTasks(Task oldTask, Task newTask) {
        final String oldDescription = oldTask.getDescription();
        final LocalDate oldExecutionDate = oldTask.getExecutionDate();
        final TaskStatus oldTaskStatus = oldTask.getTaskStatus();

        final String newDescription = newTask.getDescription();
        final LocalDate newExecutionDate = newTask.getExecutionDate();
        final TaskStatus newTaskStatus = newTask.getTaskStatus();

        return Task.builder()
                .description(oldDescription.equals(newDescription) ? oldDescription : newDescription)
                .executionDate(oldExecutionDate.equals(newExecutionDate) ? oldExecutionDate : newExecutionDate)
                .taskStatus(oldTaskStatus.equals(newTaskStatus) ? oldTaskStatus : newTaskStatus).build();
    }
}

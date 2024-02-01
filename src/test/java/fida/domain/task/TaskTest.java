package fida.domain.task;

import fida.infrastructure.exception.DomainErrorCode;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@QuarkusTest
class TaskTest {

    @Test
    void createTaskWithEmptyDescription() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() -> Task.builder().description("").build())
                .withMessage(DomainErrorCode.TASK_INVALID_DESCRIPTION.getMessage());
    }

    @Test
    void createTaskWithDescriptionAsNull() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() -> Task.builder().description(null).build())
                .withMessage(DomainErrorCode.TASK_INVALID_DESCRIPTION.getMessage());
    }

    @Test
    void createTaskWithDescriptionAsOnlySpaces() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() -> Task.builder().description("    ").build())
                .withMessage(DomainErrorCode.TASK_INVALID_DESCRIPTION.getMessage());
    }

   @Test
    void createTaskWithDescriptionWithLeadingAndTrailingSpaces() {
       Task task = Task.builder().description("  test  ").build();
       assertThat(task.getDescription()).isEqualTo("test");
   }

    @Test
    void createTaskWithElapsedExecutionDate() {
        LocalDate weekBeforeNow = LocalDate.now().minusDays(7L);
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() -> Task.builder().executionDate(weekBeforeNow).build())
                .withMessage(DomainErrorCode.TASK_INVALID_EXECUTION_DATE.getMessage());
    }

    @Test
    void createTaskWithNullExecutionDate() {
        assertThatExceptionOfType(TaskException.class)
                .isThrownBy(() -> Task.builder().executionDate(null).build())
                .withMessage(DomainErrorCode.TASK_INVALID_EXECUTION_DATE.getMessage());
    }
}
package fida.domain.task;

import fida.infrastructure.exception.DomainErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Builder
public class Task implements Comparable<Task> {

    private String description;

    private LocalDate executionDate;

    private TaskStatus taskStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getDescription().equals(task.getDescription()) && getExecutionDate().equals(task.getExecutionDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getExecutionDate());
    }

    @Override
    public int compareTo(Task o) {
        if (o == null) {
            return 1;
        }
        int primary = this.getExecutionDate().compareTo(o.getExecutionDate());

        return primary != 0 ? primary :
                this.getDescription().compareTo(o.getDescription());
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", executionDate=" + executionDate +
                ", taskStatus=" + taskStatus +
                '}';
    }

    public static class TaskBuilder {

        String description;
        LocalDate executionDate;

        public TaskBuilder description(String description) {
            if (description == null || description.trim().isEmpty()) {
                throw new TaskException(DomainErrorCode.TASK_INVALID_DESCRIPTION.getMessage(), DomainErrorCode.TASK_INVALID_DESCRIPTION);
            }
            this.description = description.trim();
            return this;
        }

        public TaskBuilder executionDate(LocalDate executionDate) {
            if (executionDate == null || elapsedExecutionDate(executionDate)) {
                throw new TaskException(DomainErrorCode.TASK_INVALID_EXECUTION_DATE.getMessage(), DomainErrorCode.TASK_INVALID_EXECUTION_DATE);
            }
            this.executionDate = executionDate;
            return this;
        }

        private boolean elapsedExecutionDate(LocalDate executionDate) {
            return executionDate.isBefore(LocalDate.now());
        }
    }
}

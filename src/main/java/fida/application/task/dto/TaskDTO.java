package fida.application.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fida.domain.task.Task;
import fida.domain.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskDTO {
    @JsonProperty(value = "description")
    private final String description;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @JsonProperty(value = "executionDate")
    private final LocalDate executionDate;

    @JsonProperty(value = "taskStatus")
    private final TaskStatus taskStatus;

    public static TaskDTO of(Task task) {
        return new TaskDTO(task.getDescription(), task.getExecutionDate(), task.getTaskStatus());
    }
}

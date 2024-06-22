package todolist.application.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import todolist.domain.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateTaskDTO {

    @JsonProperty(value = "taskDTO")
    private final TaskDTO taskDTO;

    @JsonProperty(value = "newDescription")
    private final String newDescription;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @JsonProperty(value = "newExecutionDate")
    private final LocalDate newExecutionDate;

    @JsonProperty(value = "newTaskStatus")
    private final TaskStatus newTaskStatus;
}

package fida.application.task;

import fida.application.task.dto.TaskDTO;
import fida.application.task.dto.UpdateTaskDTO;
import fida.domain.task.Task;
import fida.domain.task.TaskException;
import fida.domain.task.TaskService;
import fida.domain.task.TaskStatus;
import fida.infrastructure.exception.DomainErrorCode;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@Path("/tasks")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskService taskService;

    @GET
    public RestResponse<List<TaskDTO>> findAll() {
        final Set<Task> tasks = taskService.findAll();
        final List<TaskDTO> taskDTOS = tasks.stream().map(TaskDTO::of).toList();
        return RestResponse.ok(taskDTOS);
    }

    @POST
    public RestResponse<TaskDTO> createTask(@NotNull @Valid TaskDTO taskDTO) {
        validateTaskRequestObject(taskDTO);
        final Task task = taskService.createTask(Task.builder()
                .description(taskDTO.getDescription())
                .executionDate(taskDTO.getExecutionDate())
                .taskStatus(TaskStatus.SCHEDULED)
                .build());
        return RestResponse.ok(TaskDTO.of(task));
    }

    @PUT
    public RestResponse<TaskDTO> updateTask(@NotNull UpdateTaskDTO updateTaskDTO) {
        final TaskDTO taskDTO = updateTaskDTO.getTaskDTO();
        final Task task = taskService.updateTask(Task.builder().description(taskDTO.getDescription())
                .executionDate(taskDTO.getExecutionDate())
                .taskStatus(taskDTO.getTaskStatus())
                .build(),
                Task.builder().description(updateTaskDTO.getNewDescription())
                .executionDate(updateTaskDTO.getNewExecutionDate())
                .taskStatus(updateTaskDTO.getNewTaskStatus())
                .build());
        return RestResponse.ok(TaskDTO.of(task));
    }

    @DELETE
    @Path("/all")
    public RestResponse<Void> deleteAll() {
        taskService.deleteAll();
        return RestResponse.ok();
    }

    @DELETE
    public RestResponse<Void> deleteTask(@NotNull @QueryParam("description") String description,
                                            @NotNull @QueryParam("executionDate") String executionDate) {
        taskService.deleteTask(Task.builder().description(description)
                .executionDate(LocalDate.parse(executionDate))
                .build());
        return RestResponse.ok();
    }

    @GET
    @Path("/statuses")
    public RestResponse<TaskStatus[]> findAllTaskStatuses() {
        final TaskStatus[] statuses = taskService.findAllTaskStatuses();
        return RestResponse.ok(statuses);
    }

    @ServerExceptionMapper
    public Response handleResourceException(Throwable e) throws Throwable {
        if (e instanceof TaskException) {
            final JsonObject entity = Json.createObjectBuilder()
                    .add("message", e.getMessage())
                    .add("error_code", ((TaskException) e).getDomainErrorCode().toString())
                    .build();
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(entity.toString())
                    .build();
        }
        throw e;
    }

    private void validateTaskRequestObject(TaskDTO taskDTO) {
        if (taskDTO == null) {
            log.error("Can't create task with null TaskDTO.");
            throw new TaskException(DomainErrorCode.TASK_NULL_TASK_REQUEST_DTO.getMessage(), DomainErrorCode.TASK_NULL_TASK_REQUEST_DTO);
        }
    }
}

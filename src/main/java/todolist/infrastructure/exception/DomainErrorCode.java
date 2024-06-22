package todolist.infrastructure.exception;

import lombok.Getter;

public enum DomainErrorCode {
    TASK_INVALID_EXECUTION_DATE("Can't create task with an elapsed or null execution date."),
    TASK_INVALID_DESCRIPTION("Can't create task with empty or null description."),
    TASK_NULL_TASK_REQUEST_DTO("Can't create task with null TaskDTO."),
    TASK_NOT_FOUND(""),
    TASK_IS_NULL("");

    @Getter
    final String message;
    DomainErrorCode(String message) {
        this.message = message;
    }
}

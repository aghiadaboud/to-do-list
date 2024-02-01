package fida.domain.task;

import fida.infrastructure.exception.DomainErrorCode;
import lombok.Getter;

public class TaskException extends RuntimeException {

    @Getter
    private final DomainErrorCode domainErrorCode;

    public TaskException(String message, DomainErrorCode domainErrorCode) {
        super(message);
        this.domainErrorCode = domainErrorCode;
    }

}

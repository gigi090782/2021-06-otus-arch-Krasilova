package ru.krasilova.otus.microservices.exeptions;

public class IdempotenceKeyConflictException extends RuntimeException {

    public IdempotenceKeyConflictException() {
        super("refresh_version");
    }
}

package ru.krasilova.otus.microservices.exeptions;
public class IdempotenceKeyMissingException extends RuntimeException {
    public IdempotenceKeyMissingException() {
        super("idempotence_key_missing");
    }
}

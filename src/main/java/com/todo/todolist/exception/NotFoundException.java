package com.todo.todolist.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Not Found Exception");
    }
}

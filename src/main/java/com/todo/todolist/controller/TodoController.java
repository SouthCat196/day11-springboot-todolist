package com.todo.todolist.controller;


import com.todo.todolist.entity.Todo;
import com.todo.todolist.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    private List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @DeleteMapping("/{id}")
    private void deleteTodo(@PathVariable Integer id) {
        todoService.deleteTodo(id);
    }
}

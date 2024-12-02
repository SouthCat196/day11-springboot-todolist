package com.todo.todolist.service;

import com.todo.todolist.entity.Todo;
import com.todo.todolist.exception.NotFoundException;
import com.todo.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public void deleteTodo(Integer id) {
        Todo existTodo = todoRepository.findById(id).orElse(null);
        if (Objects.isNull(existTodo)) {
            throw new NotFoundException();
        }
        todoRepository.deleteById(id);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Integer id, Todo todo) {
        Todo existTodo = todoRepository.findById(id).orElse(null);
        if (Objects.isNull(existTodo)) {
            throw new NotFoundException();
        }
        return todoRepository.save(todo);
    }

    public Todo getTodoById(Integer id) {
        Todo existTodo = todoRepository.findById(id).orElse(null);
        if (Objects.isNull(existTodo)) {
            throw new NotFoundException();
        }
        return existTodo;
    }
}

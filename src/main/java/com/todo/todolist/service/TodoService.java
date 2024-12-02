package com.todo.todolist.service;

import com.todo.todolist.entity.Todo;
import com.todo.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        todoRepository.deleteById(id);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Integer id, Todo todo) {
        return todoRepository.save(todo);
    }
}

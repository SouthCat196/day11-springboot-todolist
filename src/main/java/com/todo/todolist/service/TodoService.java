package com.todo.todolist.service;

import com.todo.todolist.entity.Todo;
import com.todo.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    final private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public void deleteTodo(Integer id) {
        todoRepository.deleteById(id);
    }
}

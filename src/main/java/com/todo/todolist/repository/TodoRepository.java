package com.todo.todolist.repository;

import com.todo.todolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}

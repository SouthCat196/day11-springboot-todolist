package com.todo.todolist.controller;

import com.todo.todolist.entity.Todo;
import com.todo.todolist.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TodoController {

    @Autowired
    private MockMvc client;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JacksonTester<List<Todo>> todoListjacksonTester;

    private Todo todo1;
    private Todo todo2;
    private Todo todo3;
    private Todo todo4;
    private Todo todo5;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
        todoRepository.flush();

        todo1 = new Todo(null, "Todo 1", false);
        todo2 = new Todo(null, "Todo 2", true);
        todo3 = new Todo(null, "Todo 3", false);
        todo4 = new Todo(null, "Todo 4", true);
        todo5 = new Todo(null, "Todo 5", true);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
        todoRepository.save(todo4);
        todoRepository.save(todo5);
    }

    @Test
    void should_return_all_todos() throws Exception {
        // Given
        final List<Todo> givenTodos = todoRepository.findAll();

        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.get("/todos")).andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        final List<Todo> fetchedTodos = todoListjacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedTodos).hasSameSizeAs(givenTodos);
        assertThat(fetchedTodos)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

}

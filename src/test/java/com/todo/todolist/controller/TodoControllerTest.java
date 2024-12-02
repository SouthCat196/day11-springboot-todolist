package com.todo.todolist.controller;

import com.todo.todolist.entity.Todo;
import com.todo.todolist.exception.NotFoundException;
import com.todo.todolist.repository.TodoRepository;
import org.assertj.core.api.AssertionsForClassTypes;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TodoControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JacksonTester<List<Todo>> todoListjacksonTester;

    @Autowired
    private JacksonTester<Todo> todojacksonTester;

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

    @Test
    void should_return_todo_when_add_todo_given_todo() throws Exception {
        //given
        final Todo todo = new Todo(null, "Task 6", false);

        //when
        //then
        final String jsonResponse = client.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType("application/json")
                        .content(todojacksonTester.write(todo).getJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        final Todo todoResult = todojacksonTester.parse(jsonResponse).getObject();
        assertThat(todoResult)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(todo);
    }

    @Test
    void should_return_todo_when_update_todo_given_todo() throws Exception {
        //given
        final Todo todo = todoRepository.findAll().get(0);
        todo.setText("Task 1 Updated");

        //when
        //then
        final String jsonResponse = client.perform(MockMvcRequestBuilders.put("/todos/" + todo.getId())
                        .contentType("application/json")
                        .content(todojacksonTester.write(todo).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        final Todo todoResult = todojacksonTester.parse(jsonResponse).getObject();
        assertThat(todoResult)
                .usingRecursiveComparison()
                .isEqualTo(todo);
    }

    @Test
    void should_return_todo_when_remove_todo_given_todo_id() throws Exception {
        //given
        final Todo todo = todoRepository.findAll().get(0);

        //when
        //then
        client.perform(MockMvcRequestBuilders.delete("/todos/" + todo.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(todoRepository.findById(todo.getId())).isEmpty();
    }

    @Test
    void should_return_NotFoundException_when_get_by_error_id() throws Exception {
        // Given
        final Integer errorId = -1;

        // When
        // Then
        String contentAsString = client.perform(MockMvcRequestBuilders.get("/todos/" + errorId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        AssertionsForClassTypes.assertThat(contentAsString).isEqualTo("Not Found Exception");
    }

    @Test
    void should_return_NotFoundException_when_update_by_error_id() throws Exception {
        // Given
        final Integer errorId = -1;
        Todo todo = new Todo(-1, "Todo", false);

        // When
        // Then
        String contentAsString = client.perform(MockMvcRequestBuilders.put("/todos/" + errorId)
                        .contentType("application/json")
                        .content(todojacksonTester.write(todo).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        AssertionsForClassTypes.assertThat(contentAsString).isEqualTo("Not Found Exception");
    }

    @Test
    void should_return_NotFoundException_when_delete_by_error_id() throws Exception {
        // Given
        final Integer errorId = -1;

        // When
        // Then
        String contentAsString = client.perform(MockMvcRequestBuilders.delete("/todos/" + errorId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        AssertionsForClassTypes.assertThat(contentAsString).isEqualTo("Not Found Exception");
    }
}
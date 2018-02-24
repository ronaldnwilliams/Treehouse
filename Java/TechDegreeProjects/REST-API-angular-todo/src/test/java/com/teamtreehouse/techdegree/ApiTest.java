package com.teamtreehouse.techdegree;

import com.google.gson.Gson;
import com.teamtreehouse.techdegree.Api;
import com.teamtreehouse.techdegree.model.Todo;
import com.teamtreehouse.techdegree.testing.ApiClient;
import com.teamtreehouse.techdegree.testing.ApiResponse;
import com.teamtreehouse.techdegree.dao.Sql2oTodoDao;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ApiTest {
    public static final String PORT = "4568";
    public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
    private Connection conn;
    private ApiClient client;
    private Gson gson;
    private Sql2oTodoDao todoDao;

    @BeforeClass
    public static void startServer() {
        String[] args = {PORT, TEST_DATASOURCE};
        Api.main(args);
    }

    @AfterClass
    public static void stopServer() {
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(TEST_DATASOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        todoDao = new Sql2oTodoDao(sql2o);
        conn = sql2o.open();
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    private Todo newTestTodo() {
        return new Todo("Test");
    }

    @Test
    public void todosReturnedForFindAll() throws Exception {
        Todo todo = newTestTodo();
        Todo todo2 = newTestTodo();
        todoDao.add(todo);
        todoDao.add(todo2);

        ApiResponse response = client.request("GET", "/api/v1/todos");
        Todo[] todos = gson.fromJson(response.getBody(), Todo[].class);

        assertEquals(2, todos.length);
    }

    @Test
    public void addingTodosReturnsCreatedStatus() throws Exception {
        Map<String, String> values = new HashMap<>();
        values.put("name", "Test");
        ApiResponse response = client.request("POST", "/api/v1/todos", gson.toJson(values));

        assertEquals(201, response.getStatus());
    }

    @Test
    public void updatingTodoUpdatesTodo() throws Exception {
        Todo todo = newTestTodo();
        todoDao.add(todo);
        int id = todo.getId();

        Map<String, String> values = new HashMap<>();
        values.put("name", "updated");
        ApiResponse response = client.request("PUT", "/api/v1/todos/" + todoDao.findById(id).getId(), gson.toJson(values));

        assertEquals("updated", todoDao.findById(id).getName());
    }

    @Test
    public void deletingTodoDeletesTodo() throws Exception {
        Todo todo = newTestTodo();
        todoDao.add(todo);

        ApiResponse response = client.request("DELETE", "/api/v1/todos/" + todo.getId());

        assertEquals(200, response.getStatus());
    }
}

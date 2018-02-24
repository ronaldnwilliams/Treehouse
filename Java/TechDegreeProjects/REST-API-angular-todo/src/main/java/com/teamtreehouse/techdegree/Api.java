package com.teamtreehouse.techdegree;


import com.google.gson.Gson;
import com.teamtreehouse.techdegree.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegree.dao.TodoDao;
import com.teamtreehouse.techdegree.exc.ApiError;
import com.teamtreehouse.techdegree.model.Todo;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Api {

    public static void main(String[] args) {
        staticFileLocation("/public");
        String datasource = "jdbc:h2:~/todos.db";
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("java Api <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }
        Sql2o sql2o = new Sql2o(String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource),
                "", "");
        TodoDao todoDao = new Sql2oTodoDao(sql2o);
        Gson gson = new Gson();

        get("api/v1/todos", "application/json", (req, res) -> todoDao.findAll(), gson::toJson);

        get("/api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            checkIfTodoNull(id, todo);
            return todo;
        }, gson::toJson);

        post("api/v1/todos", "application/json", (req, res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class);
            todoDao.add(todo);
            res.status(201);
            return todo;
        }, gson::toJson);

        put("api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            checkIfTodoNull(id, todo);
            res.status(200);
            Todo updateTodo = gson.fromJson(req.body(), Todo.class);
            todo.setName(updateTodo.getName());
            todo.setCompleted(updateTodo.isCompleted());
            todoDao.save(todo);
            return todo;
        }, gson::toJson);

        delete("api/v1/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            checkIfTodoNull(id, todo);
            res.status(200);
            todoDao.delete(todo);
            return todo;
        }, gson::toJson);

        exception(ApiError.class, (exc, req, res) -> {
            ApiError error = (ApiError) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", error.getStatus());
            jsonMap.put("errorMessage", error.getMessage());
            res.type("application/json");
            res.status(error.getStatus());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }

    private static void checkIfTodoNull(int id, Todo todo) {
        if (todo == null) throw new ApiError(404, "Could not find Todo with id: " + id);
    }

}

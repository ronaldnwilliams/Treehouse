package com.teamtreehouse.techdegree.dao;

import com.teamtreehouse.techdegree.exc.DaoException;
import com.teamtreehouse.techdegree.model.Todo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTodoDao implements TodoDao {

    private final Sql2o sql2o;

    public Sql2oTodoDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Todo todo) throws DaoException {
        String sql = "INSERT INTO todos(name) VALUES (:name)";
        try (Connection conn = sql2o.open()) {
            int id = (int) conn.createQuery(sql)
                    .bind(todo)
                    .executeUpdate()
                    .getKey();
            todo.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding Todo");
        }

    }

    @Override
    public void save(Todo todo) throws DaoException {
        String sql = "UPDATE todos SET name = :name, completed = :completed WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("name", todo.getName())
                    .addParameter("completed", todo.isCompleted())
                    .addParameter("id", todo.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem updating Todo");
        }
    }

    @Override
    public void delete(Todo todo) throws DaoException {
        String sql = "DELETE FROM todos WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("id", todo.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem deleting Todo");
        }
    }

    @Override
    public List<Todo> findAll() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT * FROM todos")
                    .executeAndFetch(Todo.class);
        }
    }

    @Override
    public Todo findById(int id) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT * FROM todos WHERE id = :id")
                .addParameter("id", id)
                .executeAndFetchFirst(Todo.class);
        }
    }
}

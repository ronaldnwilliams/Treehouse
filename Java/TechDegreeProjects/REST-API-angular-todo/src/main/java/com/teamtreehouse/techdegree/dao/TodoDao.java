package com.teamtreehouse.techdegree.dao;

import com.teamtreehouse.techdegree.exc.DaoException;
import com.teamtreehouse.techdegree.model.Todo;

import java.util.List;

public interface TodoDao {
    void add(Todo todo) throws DaoException;

    void save(Todo todo) throws DaoException;

    void delete(Todo todo) throws DaoException;

    List<Todo> findAll();

    Todo findById(int id);
}

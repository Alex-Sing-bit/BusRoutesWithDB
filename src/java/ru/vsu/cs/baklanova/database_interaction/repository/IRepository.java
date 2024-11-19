package ru.vsu.cs.baklanova.database_interaction.repository;

import java.util.List;

public interface IRepository<T> {
    boolean add(T entity);
    T getById(int id);
    boolean update(T entity);
    boolean delete(int id);
    List<T> findAll();

    void validate(T entity);
}
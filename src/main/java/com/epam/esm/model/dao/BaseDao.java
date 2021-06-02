package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends Entity> {
    Optional<T> create(T entity);

    Optional<T> findById(K id);

    List<T> findAll();

    Optional<T> update(T entity);

    void deleteById(K id);
}

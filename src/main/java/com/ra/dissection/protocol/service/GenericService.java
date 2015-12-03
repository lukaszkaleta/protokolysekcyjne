package com.ra.dissection.protocol.service;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 25.04.13 14:13
 */
public interface GenericService<T> {

    void create(T entity);

    T read(long id);

    void update(T entity);

    T delete(long id);

    List<T> getAll();

    List<T> getFiltered(String filter);
}

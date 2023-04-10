package org.olmedo.commands.jdbc.repositorio;

import java.util.List;

public interface Repositorio<T> {

    List<T> findAll();
    T byId(Long id);
    void save(T t);
    void delete(Long id);
}

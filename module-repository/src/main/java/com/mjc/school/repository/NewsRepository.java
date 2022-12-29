package com.mjc.school.repository;

import java.util.List;

public interface NewsRepository <T> {

    List<T> getAll();

    T getById(Long id);

    T create(T data);

    T update(T data);

    Boolean deleteById(Long id);

    Boolean newsExistsById(Long id);

}

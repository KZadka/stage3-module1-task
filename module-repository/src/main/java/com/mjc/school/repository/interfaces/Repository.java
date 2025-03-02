package com.mjc.school.repository.interfaces;

import java.util.List;

public interface Repository<T> {

    List<T> readAll();

    T readById(Long id);

    T createNews(T data);

    T updateNews(T data);

    Boolean deleteById(Long id);

    Boolean newsExistsById(Long id);

}

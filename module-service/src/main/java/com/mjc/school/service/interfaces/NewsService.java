package com.mjc.school.service.interfaces;

import java.util.List;

public interface NewsService<T1, T2> {

    List<T2> readAll();

    T2 readById(Long id);

    T2 create(T1 data);

    T2 update(T1 data);

    Boolean deleteById(Long id);
}

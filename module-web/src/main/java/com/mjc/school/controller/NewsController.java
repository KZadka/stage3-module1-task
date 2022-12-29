package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.interfaces.NewsService;
import com.mjc.school.service.implementation.NewsServiceImpl;

import java.util.List;

public class NewsController {

    private final NewsService<NewsDtoRequest, NewsDtoResponse> newsService = new NewsServiceImpl();

    public List<NewsDtoResponse> getAll() {
        return newsService.getAll();
    }

    public NewsDtoResponse getById(Long id) {
        return newsService.getById(id);
    }

    public NewsDtoResponse create(NewsDtoRequest request) {
        return newsService.create(request);
    }

    public NewsDtoResponse update(NewsDtoRequest request) {
        return newsService.update(request);
    }

    public Boolean deleteById(Long id) {
        return newsService.deleteById(id);
    }

}
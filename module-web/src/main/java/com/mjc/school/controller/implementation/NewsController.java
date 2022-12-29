package com.mjc.school.controller.implementation;

import com.mjc.school.controller.interfaces.Controller;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.interfaces.NewsService;
import com.mjc.school.service.implementation.NewsServiceImpl;

import java.util.List;

public class NewsController implements Controller<NewsDtoRequest, NewsDtoResponse> {

    private final NewsService<NewsDtoRequest, NewsDtoResponse> newsService = new NewsServiceImpl();

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        return newsService.readById(id);
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest request) {
        return newsService.create(request);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest request) {
        return newsService.update(request);
    }

    @Override
    public Boolean deleteById(Long id) {
        return newsService.deleteById(id);
    }

}
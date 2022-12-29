package com.mjc.school.service;

import com.mjc.school.repository.entity.News;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.implementation.NewsRepositoryImpl;
import com.mjc.school.service.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class NewsServiceImpl implements NewsService<NewsDtoRequest, NewsDtoResponse> {

    private final ModelMapper modelMapper = new ModelMapper();
    private final NewsRepository<News> repository = new NewsRepositoryImpl();
    private final Validator validator = new Validator();

    @Override
    public List<NewsDtoResponse> getAll() {
        return repository.readAll().stream()
                .map(news -> modelMapper.map(news, NewsDtoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public NewsDtoResponse getById(Long id) {
        validator.validateId(id);
        if (repository.newsExistsById(id)) {
            News news = repository.readById(id);
            return modelMapper.map(news, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest request) {
        validator.validateDto(request);
        News newsToCreate = modelMapper.map(request, News.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        newsToCreate.setCreateDate(date);
        newsToCreate.setLastUpdateDate(date);
        News newsToBeAdded = repository.createNews(newsToCreate);
        return modelMapper.map(newsToBeAdded, NewsDtoResponse.class);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest request) {
        validator.validateDto(request);
        if (repository.newsExistsById(request.getId())) {
            News newsToCreate = modelMapper.map(request, News.class);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            newsToCreate.setLastUpdateDate(date);
            News newsToBeAdded = repository.updateNews(newsToCreate);
            return modelMapper.map(newsToBeAdded, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        validator.validateId(id);
        if (repository.newsExistsById(id)) {
            return repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }
}
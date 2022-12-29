package com.mjc.school.service.implementation;

import com.mjc.school.repository.interfaces.Repository;
import com.mjc.school.repository.entity.NewsModel;
import com.mjc.school.repository.implementation.NewsRepositoryImpl;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.interfaces.NewsService;
import com.mjc.school.service.validator.NewsValidator;
import com.mjc.school.service.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NewsServiceImpl implements NewsService<NewsDtoRequest, NewsDtoResponse> {

    private final ModelMapper modelMapper = new ModelMapper();
    private final Repository<NewsModel> repository = new NewsRepositoryImpl();
    private final NewsValidator validator = new NewsValidator();

    @Override
    public List<NewsDtoResponse> readAll() {
        return repository.readAll().stream()
                .map(news -> modelMapper.map(news, NewsDtoResponse.class))
                .toList();
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        validator.validateId(id);
        if (repository.newsExistsById(id)) {
            NewsModel news = repository.readById(id);
            return modelMapper.map(news, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest request) {
        validator.validateDto(request);
        NewsModel newsToCreate = modelMapper.map(request, NewsModel.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        newsToCreate.setCreateDate(date);
        newsToCreate.setLastUpdateDate(date);
        NewsModel newsToBeAdded = repository.createNews(newsToCreate);
        return modelMapper.map(newsToBeAdded, NewsDtoResponse.class);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest request) {
        validator.validateDto(request);
        if (repository.newsExistsById(request.getId())) {
            NewsModel newsToCreate = modelMapper.map(request, NewsModel.class);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            newsToCreate.setLastUpdateDate(date);
            NewsModel newsToBeAdded = repository.updateNews(newsToCreate);
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
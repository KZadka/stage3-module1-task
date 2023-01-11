package com.mjc.school.service.implementation;

import com.mjc.school.repository.interfaces.Repository;
import com.mjc.school.repository.entity.NewsModel;
import com.mjc.school.repository.implementation.NewsRepositoryImpl;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.interfaces.NewsService;
import com.mjc.school.service.validator.Validator;
import com.mjc.school.service.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NewsServiceImpl implements NewsService<NewsDtoRequest, NewsDtoResponse> {

    private static NewsServiceImpl newsService;
    private final ModelMapper modelMapper;
    private final Repository<NewsModel> newsRepository;
    private final Validator newsValidator;

    public static NewsServiceImpl getNewsService() {
        if (newsService == null) {
            newsService = new NewsServiceImpl();
        }
        return newsService;
    }

    public NewsServiceImpl() {
        modelMapper = new ModelMapper();
        newsRepository = new NewsRepositoryImpl();
        newsValidator = new Validator();
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsRepository.readAll().stream()
                .map(news -> modelMapper.map(news, NewsDtoResponse.class))
                .toList();
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        newsValidator.validateId(id);
        if (newsRepository.newsExistsById(id)) {
            NewsModel news = newsRepository.readById(id);
            return modelMapper.map(news, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest request) {
        newsValidator.validateDto(request);
        NewsModel newsToCreate = modelMapper.map(request, NewsModel.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        newsToCreate.setCreateDate(date);
        newsToCreate.setLastUpdateDate(date);
        NewsModel newsToBeAdded = newsRepository.createNews(newsToCreate);
        return modelMapper.map(newsToBeAdded, NewsDtoResponse.class);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest request) {
        newsValidator.validateDto(request);
        if (newsRepository.newsExistsById(request.getId())) {
            NewsModel newsToCreate = modelMapper.map(request, NewsModel.class);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            newsToCreate.setLastUpdateDate(date);
            NewsModel newsToBeAdded = newsRepository.updateNews(newsToCreate);
            return modelMapper.map(newsToBeAdded, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        newsValidator.validateId(id);
        if (newsRepository.newsExistsById(id)) {
            return newsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }
}
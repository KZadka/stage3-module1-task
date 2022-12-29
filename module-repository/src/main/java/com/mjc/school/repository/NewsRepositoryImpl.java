package com.mjc.school.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NewsRepositoryImpl implements NewsRepository<News> {

    private final DataSource dataSource = new DataSource();

    @Override
    public List<News> getAll() {
        return dataSource.getNews();
    }

    @Override
    public News getById(Long id) {
        return dataSource.getNews().stream()
                .filter(news -> id.equals(news.getId()))
                .findFirst().get();
    }

    @Override
    public News create(News newsToAdd) {
        List<News> news = dataSource.getNews();
        news.sort(Comparator.comparing(News::getId));
        if (!news.isEmpty()) {
            newsToAdd.setId(news.get(news.size() - 1).getId() + 1L);
        } else {
            newsToAdd.setId(1L);
        }
        news.add(newsToAdd);
        return newsToAdd;
    }

    @Override
    public News update(News newsToUpdate) {
        News news = this.getById(newsToUpdate.getId());
        news.setTitle(newsToUpdate.getTitle());
        news.setContent(newsToUpdate.getContent());
        news.setLastUpdateDate(newsToUpdate.getLastUpdateDate());
        news.setAuthorId(newsToUpdate.getAuthorId());
        return news;
    }

    @Override
    public Boolean deleteById(Long id) {
        List<News> newsToDelete = new ArrayList<>();
        newsToDelete.add(getById(id));
        return dataSource.getNews().removeAll(newsToDelete);
    }

    @Override
    public Boolean newsExistsById(Long id) {
        return dataSource.getNews().stream()
                .anyMatch(news -> id.equals(news.getId()));
    }
}
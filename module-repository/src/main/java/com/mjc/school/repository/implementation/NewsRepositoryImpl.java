package com.mjc.school.repository.implementation;

import com.mjc.school.repository.data.DataSource;
import com.mjc.school.repository.entity.NewsModel;
import com.mjc.school.repository.interfaces.NewsRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NewsRepositoryImpl implements NewsRepository<NewsModel> {

    private final DataSource dataSource = new DataSource();

    @Override
    public List<NewsModel> readAll() {
        return dataSource.getNews();
    }

    @Override
    public NewsModel readById(Long id) {
        return dataSource.getNews().stream()
                .filter(news -> id.equals(news.getId()))
                .findFirst().get();
    }

    @Override
    public NewsModel createNews(NewsModel newsToAdd) {
        List<NewsModel> news = dataSource.getNews();
        news.sort(Comparator.comparing(NewsModel::getId));
        if (!news.isEmpty()) {
            newsToAdd.setId(news.get(news.size() - 1).getId() + 1L);
        } else {
            newsToAdd.setId(1L);
        }
        news.add(newsToAdd);
        return newsToAdd;
    }

    @Override
    public NewsModel updateNews(NewsModel newsToUpdate) {
        NewsModel news = this.readById(newsToUpdate.getId());
        news.setTitle(newsToUpdate.getTitle());
        news.setContent(newsToUpdate.getContent());
        news.setLastUpdateDate(newsToUpdate.getLastUpdateDate());
        news.setAuthorId(newsToUpdate.getAuthorId());
        return news;
    }

    @Override
    public Boolean deleteById(Long id) {
        List<NewsModel> newsToDelete = new ArrayList<>();
        newsToDelete.add(readById(id));
        return dataSource.getNews().removeAll(newsToDelete);
    }

    @Override
    public Boolean newsExistsById(Long id) {
        return dataSource.getNews().stream()
                .anyMatch(news -> id.equals(news.getId()));
    }
}
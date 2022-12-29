package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.ResourceNotFoundException;
import com.mjc.school.service.implementation.NewsServiceImpl;
import com.mjc.school.service.interfaces.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsServiceImplTest {

    private NewsService<NewsDtoRequest, NewsDtoResponse> service;

    @BeforeEach
    void setUp() {
        service = new NewsServiceImpl();
    }

    @Test
    void givenSizeOfFullList_whenGetAll_thenReturnListOfAllNews() {
        int expectedSize = 20;

        List<NewsDtoResponse> newsList = service.getAll();

        assertEquals(expectedSize, newsList.size());
    }

    @Test
    void givenNewsId_whenGetNewsById_thenReturnNewsDtoResponse() {
        long expectedId = 5;

        NewsDtoResponse response = service.getById(expectedId);

        assertNotEquals(null, response);
        assertEquals(expectedId, response.getId());
    }

    @Test
    void givenWrongNewsId_whenGetNewsById_thenThrowException() {
        long wrongNewsId = 21;

        assertThrows(ResourceNotFoundException.class, () -> service.getById(wrongNewsId));
    }

    @Test
    void givenNewsDtoRequest_whenCreateNews_thenReturnExpectedNewsDtoResponse() {
        NewsDtoResponse expected = new NewsDtoResponse(21L, "Title", "Content", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 10L);
        NewsDtoRequest request = new NewsDtoRequest(21L, "Title", "Content", 10L);

        NewsDtoResponse actual = service.create(request);

        assertEquals(expected, actual);
    }

    @Test
    void givenNewsDtoRequest_whenUpdate_thenReturnUpdatedNewsDtoResponse() {
        LocalDateTime timeOfCreation = service.getById(1L).getCreateDate();
        NewsDtoResponse expected = new NewsDtoResponse(1L, "Updated Title", "Updated Content", timeOfCreation, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 2L);

        NewsDtoResponse actual = service.update(new NewsDtoRequest(1L, "Updated Title", "Updated Content", 2L));

        assertEquals(expected, actual);

    }

    @Test
    void givenWrongNewsId_whenUpdateNews_thenThrowException() {
        long wrongNewsId = 32;

        assertThrows(ResourceNotFoundException.class, () ->
                service.update(new NewsDtoRequest(wrongNewsId, "Updated Title", "Updated Content", 2L)));
    }

    @Test
    void givenNewsId_whenDeleteNews_thenReturnTrue() {
        assertTrue(service.deleteById(1L));
    }

    @Test
    void givenWrongNewsId_whenDeleteNews_thenThrowException() {
        long wrongNewsId = 25;

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(wrongNewsId));
    }
}
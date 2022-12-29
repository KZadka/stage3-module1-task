package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exceptions.ResourceNotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.validator.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {

    private final Validator validator = new Validator();

    @Test
    void givenNullAsTitle_whenValidateNewsTitle_thenThrowException() {
        assertThrows(ValidatorException.class, () ->
                validator.validateTitle(null));
    }

    @Test
    void givenToShortTitle_whenValidateNewsTitle_thenThrowException() {
        String title = "a";

        assertThrows(ValidatorException.class, () ->
                validator.validateTitle(title));
    }

    @Test
    void givenToLongTitle_whenValidateNewsTitle_thenThrowException() {
        String title = "a".repeat(31);

        assertThrows(ValidatorException.class, () ->
                validator.validateTitle(title));
    }

    @Test
    void givenNullAsContent_whenValidateNewsContent_thenThrowException() {
        assertThrows(ValidatorException.class, () ->
                validator.validateContent(null));
    }

    @Test
    void givenToShortContent_whenValidateNewsContent_thenThrowException() {
        String content = "a";

        assertThrows(ValidatorException.class, () ->
                validator.validateContent(content));
    }

    @Test
    void givenToLongContent_whenValidateContent_thenThrowException() {
        String content = "a".repeat(256);

        assertThrows(ValidatorException.class, () ->
                validator.validateContent(content));
    }

    @Test
    void givenNullAsId_whenValidateId_thenThrowException() {
        assertThrows(ValidatorException.class, () ->
                validator.validateId(null));
    }

    @Test
    void givenNegativeId_whenValidateId_thenThrowException() {
        long id = -1L;

        assertThrows(ValidatorException.class, () ->
                validator.validateId(id));
    }

    @Test
    void givenNonExistedAuthorId_whenValidateAuthorId_thenThrowException() {
        long id = 25L;

        assertThrows(ResourceNotFoundException.class, () ->
                validator.validateAuthorId(id));
    }

    @Test
    void givenNewsDtoRequest_whenValidateNewsDto_thenCallOtherValidationMethods() {
        NewsDtoRequest request = new NewsDtoRequest(1L, "Title", "Content", 1L);

        assertAll(() -> validator.validateDto(request));
    }
}
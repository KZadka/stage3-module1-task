package com.mjc.school.repository.entity;

import java.util.Objects;

public class AuthorModel {

    private Long id;
    private String name;

    public AuthorModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorModel author = (AuthorModel) o;
        return id.equals(author.id) && name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

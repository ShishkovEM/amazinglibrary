package ru.sberbank.amazinglibrary.model;

import ru.sberbank.amazinglibrary.entity.LibraryEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private Long id;
    private String name;
    private List<Book> books;

    public static Library toModel(LibraryEntity entity) {
        Library model = new Library();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setBooks(entity.getBooks().stream().map(Book::toModel).collect(Collectors.toList()));
        return model;
    }

    public Library() {
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

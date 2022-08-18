package ru.sberbank.amazinglibrary.model;

import ru.sberbank.amazinglibrary.entity.BookEntity;
import java.util.List;
import java.util.stream.Collectors;

public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer total_amount;
    private Integer available_amount;
    private Long libraryId;

    public static Book toModel(BookEntity entity) {
        Book model = new Book();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setAuthor(entity.getAuthor());
        model.setTotal_amount(entity.getTotal_amount());
        model.setAvailable_amount(entity.getAvailable_amount());
        model.setLibraryId(entity.getLibrary().getId());
        return model;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public Integer getAvailable_amount() {
        return available_amount;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }

    public void setAvailable_amount(Integer available_amount) {
        this.available_amount = available_amount;
    }
}

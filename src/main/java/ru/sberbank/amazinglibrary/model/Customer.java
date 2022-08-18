package ru.sberbank.amazinglibrary.model;

import ru.sberbank.amazinglibrary.entity.CustomerEntity;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {
    private Long id;
    private String name;
    private Book book;
    private List<Grade> grades;

    public static Customer toModel(CustomerEntity entity) {
        Customer model = new Customer();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setBook(Book.toModel(entity.getBook()));
        model.setGrades(entity.getGrades().stream().map(Grade::toModel).collect(Collectors.toList()));
        return model;
    }

    public Customer() {
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}

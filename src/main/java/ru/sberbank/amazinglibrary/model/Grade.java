package ru.sberbank.amazinglibrary.model;

import ru.sberbank.amazinglibrary.entity.GradeEntity;

public class Grade {
    private Long id;
    private Byte value;
    private Customer customer;
    private Book book;

    public Grade() {
    }

    public static Grade toModel(GradeEntity entity) {
        Grade model = new Grade();
        model.setId(entity.getId());
        model.setValue(entity.getValue());
        model.setCustomer(Customer.toModel(entity.getCustomer()));
        model.setBook(Book.toModel(entity.getBook()));
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

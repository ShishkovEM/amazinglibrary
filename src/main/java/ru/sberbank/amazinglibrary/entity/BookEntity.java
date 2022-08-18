package ru.sberbank.amazinglibrary.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Integer total_amount;
    private Integer available_amount;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private LibraryEntity library;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<CustomerEntity> customers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<GradeEntity> grades;

    public BookEntity() {
    }

    public Long getId() {
        return id;
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

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public void setAvailable_amount(int available_amount) {
        this.available_amount = available_amount;
    }
    public Integer getAvailable_amount() {
        return available_amount;
    }

    public LibraryEntity getLibrary() {
        return library;
    }

    public void setLibrary(LibraryEntity library) {
        this.library = library;
    }

    public List<CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerEntity> customers) {
        this.customers = customers;
    }

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }
}
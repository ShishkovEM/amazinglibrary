package ru.sberbank.amazinglibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.amazinglibrary.entity.BookEntity;
import ru.sberbank.amazinglibrary.entity.CustomerEntity;
import ru.sberbank.amazinglibrary.entity.GradeEntity;
import ru.sberbank.amazinglibrary.exception.*;
import ru.sberbank.amazinglibrary.model.Customer;
import ru.sberbank.amazinglibrary.repository.BookRepo;
import ru.sberbank.amazinglibrary.repository.CustomerRepo;
import ru.sberbank.amazinglibrary.repository.GradeRepo;
import ru.sberbank.amazinglibrary.repository.LibraryRepo;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private LibraryRepo libraryRepo;

    public CustomerEntity createCustomer(CustomerEntity customer) throws CustomerAlreadyExistsException {
        if (customerRepo.findByName(customer.getName()) != null) {
            throw new CustomerAlreadyExistsException("Читатель с таким именем уже существует");
        }
        return customerRepo.save(customer);
    }

    public Customer getOne(Long id) throws CustomerNotFoundException {
        CustomerEntity customer = customerRepo.findById(id).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Читатель не найден");
        }
        return Customer.toModel(customer);
    }

    public Long delete(Long id) throws CustomerNotFoundException, CustomerIsHoldingBookException {
        if (!customerRepo.findById(id).isPresent()) {
            throw new CustomerNotFoundException("Читатель не найден");
        }
        if (customerRepo.findById(id).get().getBook() != null) {
            throw new CustomerIsHoldingBookException("У читателя имеется несданная книга");
        }
        customerRepo.deleteById(id);
        return id;
    }

    public CustomerEntity acquireBook(Long customerId,
                                      Long libraryId,
                                      String bookTitle) throws  CustomerNotFoundException,
                                                                LibraryNotFoundException,
                                                                BookNotFoundException,
                                                                NoBooksAvailableException,
                                                                CustomerIsHoldingBookException {
        if (!customerRepo.findById(customerId).isPresent()) {
            throw new CustomerNotFoundException("Читатель не найден");
        }
        if (!libraryRepo.findById(libraryId).isPresent()) {
            throw new LibraryNotFoundException("Библиотека не найдена");
        }
        if (bookRepo.findByTitleAndLibrary(bookTitle, libraryRepo.findById(libraryId).get()) == null) {
            throw new BookNotFoundException("Книги с названием \"" + bookTitle + "\" нет в библиотеке с id=" + libraryId);
        }
        if (bookRepo.findByTitleAndLibrary(bookTitle, libraryRepo.findById(libraryId).get()).getAvailable_amount() == 0) {
            throw new NoBooksAvailableException("Нет доступных экземпляров книги");
        }
        if (customerRepo.findById(customerId).get().getBook() != null) {
            throw new CustomerIsHoldingBookException("У читателя есть несданная книга");
        }
        CustomerEntity customer = customerRepo.findById(customerId).get();
        BookEntity book = bookRepo.findByTitleAndLibrary(bookTitle, libraryRepo.findById(libraryId).get());
        customer.setBook(book);
        book.setAvailable_amount(book.getAvailable_amount() - 1);
        bookRepo.save(book);
        return customerRepo.save(customer);
    }

    public CustomerEntity releaseBook(Long customerId, Byte gradeValue) throws CustomerHasNoBookException, IllegalArgumentException {
        if (customerRepo.findById(customerId).get().getBook() == null) {
            throw new CustomerHasNoBookException("У читателя нет книги на руках");
        }
        if (gradeValue < 0 || gradeValue > 10) {
            throw new IllegalArgumentException("Передана оценка < 0 или > 10");
        }
        CustomerEntity customer = customerRepo.findById(customerId).get();
        BookEntity book = customer.getBook();
        book.setAvailable_amount(book.getAvailable_amount() + 1);
        customer.setBook(null);
        GradeEntity grade = new GradeEntity();
        grade.setBook(book);
        grade.setCustomer(customer);
        grade.setValue(gradeValue);
        bookRepo.save(book);
        gradeRepo.save(grade);
        return customerRepo.save(customer);
    }
}
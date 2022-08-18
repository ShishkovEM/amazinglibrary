package ru.sberbank.amazinglibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.amazinglibrary.entity.BookEntity;
import ru.sberbank.amazinglibrary.exception.BookAlreadyExistsException;
import ru.sberbank.amazinglibrary.exception.BookNotFoundException;
import ru.sberbank.amazinglibrary.exception.LibraryNotFoundException;
import ru.sberbank.amazinglibrary.exception.NotAllBooksAreInLibraryException;
import ru.sberbank.amazinglibrary.model.Book;
import ru.sberbank.amazinglibrary.repository.BookRepo;
import ru.sberbank.amazinglibrary.repository.LibraryRepo;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private LibraryRepo libraryRepo;

    public Book createBook(BookEntity book, Long libraryId) throws LibraryNotFoundException, BookAlreadyExistsException {
        if (!libraryRepo.findById(libraryId).isPresent()) {
            throw new LibraryNotFoundException("Библиотека с таким ID не найдена");
        }
        if (bookRepo.findByTitle(book.getTitle()) != null && bookRepo.findByTitle(book.getTitle()).getLibrary().getId() == libraryId ) {
            throw new BookAlreadyExistsException("Книга с таким названием уже есть в этой библиотеке");
        }
        book.setLibrary(libraryRepo.findById(libraryId).get());
        bookRepo.save(book);
        return Book.toModel(book);
    }

    public Book getOne(Long id) throws BookNotFoundException {
        if (!bookRepo.findById(id).isPresent()) {
            throw new BookNotFoundException("Книга не найдена");
        }
        BookEntity book = bookRepo.findById(id).get();
        return Book.toModel(book);
    }

    public List<Book> getBooksByTitle(String title) throws BookNotFoundException {
        if (bookRepo.findAllByTitle(title) == null) {
            throw new BookNotFoundException("Книга не найдена");
        }
        return bookRepo.findAllByTitle(title).stream().map(Book::toModel).collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(String author) throws BookNotFoundException {
        if (bookRepo.findAllByAuthor(author) == null) {
            throw new BookNotFoundException("Книги указанного автора не найдены");
        }
        return bookRepo.findAllByAuthor(author).stream().map(Book::toModel).collect(Collectors.toList());
    }

    public String delete(String title, Long libraryId) throws   LibraryNotFoundException,
                                                                BookNotFoundException,
                                                                NotAllBooksAreInLibraryException {
        if (!libraryRepo.findById(libraryId).isPresent()) {
            throw new LibraryNotFoundException("Библиотека с таким ID не найдена");
        }
        BookEntity book = bookRepo.findByTitleAndLibrary(title, libraryRepo.findById(libraryId).get());
        if (book == null) {
            throw new BookNotFoundException("Книга с таким названием не найдена в этой библиотеке");
        }
        if (book.getTotal_amount() != book.getAvailable_amount()) {
            throw new NotAllBooksAreInLibraryException("Не все экземпляры книги сейчас находятся в библиотеке");
        }
        bookRepo.deleteById(book.getId());
        return "Книга \"" + title + "\" успешно удалена из библиотеки id=" + libraryId;
    }
}

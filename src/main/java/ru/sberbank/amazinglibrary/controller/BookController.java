package ru.sberbank.amazinglibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.amazinglibrary.entity.BookEntity;
import ru.sberbank.amazinglibrary.exception.BookAlreadyExistsException;
import ru.sberbank.amazinglibrary.exception.BookNotFoundException;
import ru.sberbank.amazinglibrary.exception.LibraryNotFoundException;
import ru.sberbank.amazinglibrary.exception.NotAllBooksAreInLibraryException;
import ru.sberbank.amazinglibrary.service.BookService;

@RestController
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity createBook(@RequestBody BookEntity book,
                                     @RequestParam Long libraryId) {
        try {
            return ResponseEntity.ok(bookService.createBook(book, libraryId));
        } catch (LibraryNotFoundException | BookAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping()
    public ResponseEntity getOneBook(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(bookService.getOne(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/byTitle")
    public ResponseEntity getBooksByTitle(@RequestParam String title) {
        try {
            return ResponseEntity.ok(bookService.getBooksByTitle(title));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/byAuthor")
    public ResponseEntity getBooksByAuthor(@RequestParam String author) {
        try {
            return ResponseEntity.ok(bookService.getBooksByAuthor(author));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping
    public ResponseEntity deleteBookByTitle(@RequestParam String title,
                                            @RequestParam Long libraryId) {
        try {
            bookService.delete(title, libraryId);
            return ResponseEntity.ok("Книга \"" + title + "\" успешно удалена из библиотеки id=" + libraryId);
        } catch (LibraryNotFoundException | BookNotFoundException | NotAllBooksAreInLibraryException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}

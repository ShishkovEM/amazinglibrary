package ru.sberbank.amazinglibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.amazinglibrary.entity.CustomerEntity;
import ru.sberbank.amazinglibrary.exception.*;
import ru.sberbank.amazinglibrary.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CustomerEntity customer) {
        try {
            customerService.createCustomer(customer);
            return ResponseEntity.ok("Читатель успешно сохранен");
        } catch (CustomerAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping()
    public ResponseEntity getOneCustomer(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(customerService.getOne(id));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping
    public ResponseEntity deleteCustomer(@RequestParam Long id) {
        try {
            customerService.delete(id);
            return ResponseEntity.ok("Читатель с id=" + id + " успешно удален");
        } catch (CustomerNotFoundException | CustomerIsHoldingBookException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity acquireBook(@PathVariable("id") Long customerId,
                                      @RequestParam Long libraryId,
                                      @RequestParam String bookTitle) {
        try {
            customerService.acquireBook(customerId, libraryId, bookTitle);
            return ResponseEntity.ok("Книга \"" + bookTitle + "\" из библиотеки id=" + libraryId + " успешно выдана читателю id=" + customerId);
        } catch (CustomerNotFoundException |
                 LibraryNotFoundException |
                 BookNotFoundException |
                 CustomerIsHoldingBookException |
                 NoBooksAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("release/{id}")
    public ResponseEntity releaseBook(@PathVariable("id") Long customerId,
                                      @RequestParam Byte gradeValue) {
        try {
            customerService.releaseBook(customerId, gradeValue);
            return ResponseEntity.ok("Читатель id=" + customerId + " вернул книгу и выставил ей оценку " + gradeValue);
        } catch (CustomerHasNoBookException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
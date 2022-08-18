package ru.sberbank.amazinglibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.amazinglibrary.entity.GradeEntity;
import ru.sberbank.amazinglibrary.exception.BookNotFoundException;
import ru.sberbank.amazinglibrary.exception.CustomerNotFoundException;
import ru.sberbank.amazinglibrary.exception.GradeAlreadyExistsException;
import ru.sberbank.amazinglibrary.exception.GradeNotFoundException;
import ru.sberbank.amazinglibrary.service.GradeService;

@RestController
@RequestMapping("/grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @PostMapping
    public ResponseEntity createGrade(@RequestBody GradeEntity grade,
                                      @RequestParam Long bookId,
                                      @RequestParam Long customerId) {
        try {
            return ResponseEntity.ok(gradeService.createGrade(grade, bookId, customerId));
        } catch (GradeAlreadyExistsException | BookNotFoundException | CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping()
    public ResponseEntity getOneGrade(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(gradeService.getOne(id));
        } catch (GradeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/average")
    public ResponseEntity getAverageGrade(@RequestParam Long bookId) {
        try {
            return ResponseEntity.ok(gradeService.getAverageGrade(bookId));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}

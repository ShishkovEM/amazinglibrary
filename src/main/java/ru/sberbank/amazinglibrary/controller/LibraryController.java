package ru.sberbank.amazinglibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.amazinglibrary.entity.LibraryEntity;
import ru.sberbank.amazinglibrary.exception.LibraryAlreadyExistsException;
import ru.sberbank.amazinglibrary.exception.LibraryNotFoundException;
import ru.sberbank.amazinglibrary.service.LibraryService;

@RestController
@RequestMapping("/libraries")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping
    public ResponseEntity createLibrary(@RequestBody LibraryEntity library) {
        try {
            libraryService.createLibrary(library);
            return ResponseEntity.ok("Библиотека успешно сохранена");
        } catch (LibraryAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping
    public ResponseEntity deleteLibrary(@RequestParam Long id) {
        try {
            libraryService.delete(id);
            return ResponseEntity.ok("Библиотека успешно удалена");
        } catch (LibraryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity renameLibrary(@PathVariable("id") Long id,
                                        @RequestParam String newName) {
        try {
            libraryService.renameLibrary(id, newName);
            return ResponseEntity.ok("Библиотека с id=" + id + " успешно переименована");
        } catch (LibraryNotFoundException | LibraryAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping()
    public ResponseEntity getOneLibrary(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(libraryService.getOne(id));
        } catch (LibraryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}

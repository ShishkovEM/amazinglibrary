package ru.sberbank.amazinglibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sberbank.amazinglibrary.entity.LibraryEntity;
import ru.sberbank.amazinglibrary.exception.LibraryAlreadyExistsException;
import ru.sberbank.amazinglibrary.exception.LibraryNotFoundException;
import ru.sberbank.amazinglibrary.model.Library;
import ru.sberbank.amazinglibrary.repository.LibraryRepo;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepo libraryRepo;

    public LibraryEntity createLibrary(LibraryEntity library) throws LibraryAlreadyExistsException {
        if (libraryRepo.findByName(library.getName()) != null) {
            throw new LibraryAlreadyExistsException("Библиотека с таким названием уже существует");
        }
        return libraryRepo.save(library);
    }

    public LibraryEntity renameLibrary(@PathVariable("id") Long id,
                                                     String newName) throws  LibraryNotFoundException,
                                                                             LibraryAlreadyExistsException {
        if (!libraryRepo.findById(id).isPresent()) {
            throw new LibraryNotFoundException("Библиотека не найдена");
        }
        if (libraryRepo.findByName(newName) != null) {
            throw new LibraryAlreadyExistsException("Библиотека с таким названием уже существует");
        }
        LibraryEntity library = libraryRepo.findById(id).get();
        library.setId(id);
        library.setName(newName);
        return libraryRepo.save(library);
    }

    public Library getOne(Long id) throws LibraryNotFoundException {
        if (!libraryRepo.findById(id).isPresent()) {
            throw new LibraryNotFoundException("Библиотека не найдена");
        }
        return Library.toModel(libraryRepo.findById(id).get());
    }

    public Long delete(Long id) throws LibraryNotFoundException {
        if (!libraryRepo.findById(id).isPresent()) {
            throw new LibraryNotFoundException("Библиотека не найдена");
        }
        libraryRepo.deleteById(id);
        return id;
    }
}

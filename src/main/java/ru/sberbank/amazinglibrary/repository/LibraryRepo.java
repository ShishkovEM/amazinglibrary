package ru.sberbank.amazinglibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.amazinglibrary.entity.LibraryEntity;

public interface LibraryRepo extends CrudRepository<LibraryEntity, Long> {
    LibraryEntity findByName(String name);
}
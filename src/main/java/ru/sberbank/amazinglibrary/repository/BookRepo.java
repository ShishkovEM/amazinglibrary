package ru.sberbank.amazinglibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.amazinglibrary.entity.BookEntity;
import ru.sberbank.amazinglibrary.entity.LibraryEntity;

import java.util.List;

public interface BookRepo extends CrudRepository<BookEntity, Long> {
    BookEntity findByTitle(String title);
    BookEntity findByTitleAndLibrary(String title, LibraryEntity library);
    List<BookEntity> findAllByTitle(String title);
    List<BookEntity> findAllByAuthor(String author);
}

package ru.sberbank.amazinglibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.amazinglibrary.entity.BookEntity;
import ru.sberbank.amazinglibrary.entity.GradeEntity;

import java.util.List;

public interface GradeRepo extends CrudRepository<GradeEntity, Long> {
    List<GradeEntity> findAllByBook(BookEntity book);
}

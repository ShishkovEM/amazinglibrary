package ru.sberbank.amazinglibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.amazinglibrary.entity.GradeEntity;
import ru.sberbank.amazinglibrary.exception.*;
import ru.sberbank.amazinglibrary.model.Grade;
import ru.sberbank.amazinglibrary.repository.BookRepo;
import ru.sberbank.amazinglibrary.repository.CustomerRepo;
import ru.sberbank.amazinglibrary.repository.GradeRepo;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private GradeRepo gradeRepo;

    public GradeEntity createGrade(GradeEntity grade, Long bookId, Long customerId) throws  GradeAlreadyExistsException,
                                                                                            BookNotFoundException,
                                                                                            CustomerNotFoundException {
        if (gradeRepo.findById(grade.getId()).isPresent()) {
            throw new GradeAlreadyExistsException("Оценка с таким ID уже существует");
        }
        if (!bookRepo.findById(bookId).isPresent()) {
            throw new BookNotFoundException("Книга с таким ID не найдена");
        }
        if (!customerRepo.findById(customerId).isPresent()) {
            throw new CustomerNotFoundException("Читатель с таким ID не найден");
        }
        return gradeRepo.save(grade);
    }

    public Grade getOne(Long id) throws GradeNotFoundException {
        GradeEntity grade = gradeRepo.findById(id).get();
        if (grade == null) {
            throw new GradeNotFoundException("Оценка не найдена");
        }
        return Grade.toModel(grade);
    }

    public Long delete(Long id) {
        gradeRepo.deleteById(id);
        return id;
    }

    public Double getAverageGrade(Long bookId) throws BookNotFoundException {
        List<GradeEntity> gradeEntityList = gradeRepo.findAllByBook(bookRepo.findById(bookId).get());
        if (gradeEntityList.isEmpty()) {
            throw new BookNotFoundException("В базе отсутствуют оценки для книги id=" + bookId);
        }
        double totalValue = 0.0;
        for (GradeEntity gradeEntity: gradeEntityList) {
            totalValue += gradeEntity.getValue();
        };
        return totalValue / gradeEntityList.size();
    }
}

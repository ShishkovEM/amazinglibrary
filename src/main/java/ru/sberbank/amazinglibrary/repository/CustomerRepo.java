package ru.sberbank.amazinglibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.amazinglibrary.entity.BookEntity;
import ru.sberbank.amazinglibrary.entity.CustomerEntity;

public interface CustomerRepo extends CrudRepository<CustomerEntity, Long> {
    CustomerEntity findByName(String name);
}

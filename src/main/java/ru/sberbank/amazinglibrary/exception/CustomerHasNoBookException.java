package ru.sberbank.amazinglibrary.exception;

public class CustomerHasNoBookException extends Exception {
    public CustomerHasNoBookException(String message) {
        super(message);
    }
}
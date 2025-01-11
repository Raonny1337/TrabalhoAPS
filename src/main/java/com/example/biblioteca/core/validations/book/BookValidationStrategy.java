package com.example.biblioteca.core.validations.book;

import com.example.biblioteca.model.book.Book;

public interface BookValidationStrategy {
    void validate(Book book) throws IllegalArgumentException;
}

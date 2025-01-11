package com.example.biblioteca.core.validations.book;

import com.example.biblioteca.model.book.Book;

public class ISBNValidationStrategy implements BookValidationStrategy {
    @Override
    public void validate(Book book) throws IllegalArgumentException {
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("ISBN é obrigatório.");
        }
    }
}
package com.example.biblioteca.service;

import com.example.biblioteca.core.validations.book.BookValidationStrategy;
import com.example.biblioteca.model.book.Book;
import com.example.biblioteca.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;
    private BookValidationStrategy isbnValidator;

    public BookService(BookRepository bookRepository,
            BookValidationStrategy isbnValidator) {
        this.bookRepository = bookRepository;
        this.isbnValidator = isbnValidator;
    }

    public Book registerBook(Book book) {
        isbnValidator.validate(book);
        Book bookResult = bookRepository.findByIsbn(book.getIsbn());
        if (bookResult != null) {
            throw new IllegalArgumentException("Livro já cadastrado.");
        }
        return bookRepository.save(book);
    }

}

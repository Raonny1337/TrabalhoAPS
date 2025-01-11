package com.example.biblioteca.repository;

import com.example.biblioteca.model.book.Book;

public class BookRepository {
    public Book save(Book book) {
        System.out.println("Mock do banco salvo com isbn: " + book.getIsbn());

        return book;
    }
}

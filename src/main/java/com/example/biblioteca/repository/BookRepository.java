package com.example.biblioteca.repository;

import com.example.biblioteca.model.book.Book;
import com.example.biblioteca.model.book.BookFactory;

public class BookRepository {
    public Book save(Book book) {
        System.out.println("Mock do banco salvo com isbn: " + book.getIsbn());

        return book;
    }

    public Book findByIsbn(String isbn) {
        System.out.println("Mock do banco buscando livro com isbn: " + isbn);
        return BookFactory.createBook(isbn, isbn, isbn, isbn, 0);
    }
}

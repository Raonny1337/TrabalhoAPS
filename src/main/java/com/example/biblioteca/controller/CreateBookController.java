package com.example.biblioteca.controller;

import com.example.biblioteca.core.utils.ChangeNotifier;
import com.example.biblioteca.model.book.Book;
import com.example.biblioteca.service.BookService;

public class CreateBookController extends ChangeNotifier {
    private BookService bookService;

    public boolean hasError = false;
    public String errorMessage = null;
    public boolean isLoading = false;
    public Book registerBook = null;

    public CreateBookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void registerBook(Book book) {
        try {
            isLoading = true;
            notifyObservers();

            bookService.registerBook(book);
            registerBook = book;
            hasError = false;
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
            hasError = true;
        } catch (Exception e) {
            hasError = true;
            errorMessage = e.getMessage();
        } finally {
            isLoading = false;
            notifyObservers();
        }
    }
}

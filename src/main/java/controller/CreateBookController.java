package controller;

import core.utils.ChangeNotifier;
import model.book.Book;
import service.BookService;

public class CreateBookController extends ChangeNotifier {
    private BookService bookService;

    private Book registerBook = null;

    public CreateBookController(BookService bookService) {
        this.bookService = bookService;
    }

    public Book getRegisterBook() {
        return registerBook;
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
        } catch (Throwable e) {
            hasError = true;
            errorMessage = e.getMessage();
        } finally {
            isLoading = false;
            notifyObservers();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        registerBook = null;
    }
}

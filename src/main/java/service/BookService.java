package service;

import core.validations.book.BookValidationStrategy;
import model.book.Book;
import repository.IBookRepository;

public class BookService {
    private IBookRepository bookRepository;
    private BookValidationStrategy isbnValidator;

    public BookService(IBookRepository bookRepository,
            BookValidationStrategy isbnValidator) {
        this.bookRepository = bookRepository;
        this.isbnValidator = isbnValidator;
    }

    public Book registerBook(Book book) throws Exception {
        isbnValidator.validate(book);
        Book bookResult = bookRepository.findByIsbn(book.getIsbn());
        if (bookResult != null) {
            throw new IllegalArgumentException("Livro já cadastrado.");
        }
        return bookRepository.save(book);
    }

}

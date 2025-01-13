package core.validations.book;

import model.book.Book;

public interface BookValidationStrategy {
    void validate(Book book) throws IllegalArgumentException;
}

package repository;

import model.book.Book;

public interface IBookRepository {

    Book save(Book book) throws Exception;

    Book findByIsbn(String isbn) throws Exception;

}
package com.example.biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.biblioteca.model.book.Book;
import com.example.biblioteca.model.book.BookFactory;
import com.example.biblioteca.model.loan.Loan;

public class LoanRepository {
    public Loan save(Loan loan) {
        System.out.println("Mock do banco salvo com id de empréstimo: " + loan.getId());
        return loan;
    }

    public List<Book> getAvaliableBooksToLoan() {
        // Implementar lógica para buscar livros disponíveis
        List<Book> books = new ArrayList<Book>();
        books.add(BookFactory.createBook("HarryPotter", "asdasd", "isbn", "asdasd", 0));
        books.add(BookFactory.createBook("Machado de Assis", "asdasd", "isbn", "asdasd", 1));
        return books;
    }

    public boolean bookIsValidForLoan(String isbn) {
        // Implementar validações para verificar se o livro está disponível
        return true;
    }

    public boolean userIsValidForLoan(int userId) {
        return true;
    }

    public boolean confirmLoan(int userId) {
        return true;
    }
}

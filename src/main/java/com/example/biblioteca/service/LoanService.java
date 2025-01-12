package com.example.biblioteca.service;

import java.util.List;

import com.example.biblioteca.model.book.Book;
import com.example.biblioteca.model.loan.Loan;
import com.example.biblioteca.model.loan.LoanFactory;
import com.example.biblioteca.repository.BookRepository;
import com.example.biblioteca.repository.LoanRepository;

public class LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public Loan loanBook(String isbn, int userId) {
        if (!loanRepository.bookIsValidForLoan(isbn)) {
            throw new IllegalArgumentException("O livro não está disponível");
        }
        if (!loanRepository.userIsValidForLoan(userId)) {
            throw new IllegalArgumentException("O usuário têm pendências que impedem o empréstimo");
        }

        Book book = bookRepository.findByIsbn(isbn);
        Loan loan = LoanFactory.createLoanSolicitation(userId, book.getId());
        return loanRepository.save(loan);
    }

    public List<Book> getAvaliableBooksToLoan() {
        return loanRepository.getAvaliableBooksToLoan();
    }

    public boolean confirmLoan(int loanId) {
        return true;
    }
}

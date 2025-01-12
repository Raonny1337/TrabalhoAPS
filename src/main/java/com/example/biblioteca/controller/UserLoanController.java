package com.example.biblioteca.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.biblioteca.core.utils.ChangeNotifier;
import com.example.biblioteca.model.book.Book;
import com.example.biblioteca.model.loan.Loan;
import com.example.biblioteca.service.LoanService;

public class UserLoanController extends ChangeNotifier {

    private LoanService loanService;

    private List<Book> avaliableBooks = new ArrayList<Book>();
    private Loan createdLoan = null;

    public UserLoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public Loan getCreatedLoan() {
        return createdLoan;
    }

    public void loanBook(String isbn, int userId) {
        isLoading = true;
        System.out.println("Notifying loanBook");
        notifyObservers();
        try {

            createdLoan = loanService.loanBook(isbn, userId);

        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
            hasError = true;
        } catch (Exception e) {
            hasError = true;
            errorMessage = e.getMessage();
        } finally {
            isLoading = false;
            System.out.println("Notifying loanBook isloading false");
            notifyObservers();
        }
    }

    public void getBooks() {
        isLoading = true;

        System.out.println("Notifying getBooks");
        notifyObservers();
        try {
            avaliableBooks = loanService.getAvaliableBooksToLoan();
        } catch (Exception e) {
            hasError = true;
            errorMessage = e.getMessage();
        } finally {
            isLoading = false;
            System.out.println("Notifying getBooks isloading false");
            notifyObservers();
        }
    }

    public List<Book> getAvaliableBooks() {
        return avaliableBooks;
    }

    @Override
    public void dispose() {
        avaliableBooks.clear();
        super.dispose();
    }
}

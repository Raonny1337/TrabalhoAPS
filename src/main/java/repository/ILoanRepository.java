package repository;

import java.util.List;

import model.book.Book;
import model.loan.Loan;

public interface ILoanRepository {

    Loan save(Loan loan) throws Exception;

    List<Book> getAvailableBooksToLoan() throws Exception;

    boolean bookIsValidForLoan(String isbn) throws Exception;

    boolean userIsValidForLoan(int userId) throws Exception;

    void confirmLoan(int loanId) throws Exception;

    List<Loan> getAllLoansByUserId(int userId) throws Exception;

    List<Loan> getAllLoans() throws Exception;

}
package service;

import java.util.List;

import model.book.Book;
import model.loan.Loan;
import model.loan.LoanFactory;
import repository.IBookRepository;
import repository.ILoanRepository;

public class LoanService {

    private ILoanRepository loanRepository;
    private IBookRepository bookRepository;

    public LoanService(ILoanRepository loanRepository, IBookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public Loan loanBook(String isbn, int userId) throws Exception {
        if (!loanRepository.bookIsValidForLoan(isbn)) {
            throw new IllegalArgumentException("O livro não está disponível");
        }
        if (!loanRepository.userIsValidForLoan(userId)) {
            throw new IllegalArgumentException("O usuário têm pendências que impedem o empréstimo");
        }

        Book book = bookRepository.findByIsbn(isbn);
        Loan loan = LoanFactory.createLoanSolicitation(userId, book.getId(), book.getTitle());
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoansByUserId(int userId) throws Exception {
        return loanRepository.getAllLoansByUserId(userId);
    }

    public List<Book> getAvailableBooksToLoan() throws Exception {
        return loanRepository.getAvailableBooksToLoan();
    }

    public void confirmLoan(int loanId) throws Exception {
        loanRepository.confirmLoan(loanId);
    }

    public List<Loan> getAllLoans() throws Exception {
        return loanRepository.getAllLoans();
    }
}

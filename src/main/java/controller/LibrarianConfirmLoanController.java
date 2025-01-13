package controller;

import java.util.List;

import model.loan.Loan;
import service.LoanService;

public class LibrarianConfirmLoanController {

    private LoanService loanService;

    public LibrarianConfirmLoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public List<Loan> getLoans() {
        try {
            return loanService.getAllLoans();
        } catch (Exception e) {
            System.out.println("Ocoreu um erro ao pegar os empréstimos");
            return null;
        }
    }

    public void confirmLoan(int loanId) {
        try {
            loanService.confirmLoan(loanId);
        } catch (Exception e) {
            System.out.println("Ocoreu um erro ao confirmar o empréstimo");

        }
    };
}
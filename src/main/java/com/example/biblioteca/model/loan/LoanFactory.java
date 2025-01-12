package com.example.biblioteca.model.loan;

import java.time.LocalDate;

public class LoanFactory {
    static public Loan createLoanSolicitation(int userId, int bookId) {
        Loan loan = new Loan();

        loan.setUserId(userId);
        loan.setBookId(bookId);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(loan.getLoanDate().plusDays(7));
        loan.setStatus(LoanStatus.PENDING_CONFIRMATION);
        return loan;
    }

    static public Proof createProff(Loan loan) {
        Proof proff = new Proof();
        proff.setUserId(loan.getUserId());
        proff.setBookTitle(loan.getBookTitle());
        proff.setLoanDate(loan.getLoanDate());
        proff.setReturnDate(loan.getReturnDate());

        return proff;
    }
}

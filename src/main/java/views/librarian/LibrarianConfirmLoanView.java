package views.librarian;

import java.util.List;
import java.util.Scanner;

import controller.LibrarianConfirmLoanController;
import core.utils.Utils;
import model.loan.Loan;

public class LibrarianConfirmLoanView {

    private LibrarianConfirmLoanController librarianController;

    LibrarianConfirmLoanView(LibrarianConfirmLoanController librarianController) {
        this.librarianController = librarianController;
    }

    public void confirmLoan(Scanner scanner) {

        List<Loan> loans = librarianController.getLoans();
        if (loans.size() <= 0) {
            System.out.println("Não há empréstimos cadastrados");
            return;
        }
        System.err.println("");
        System.err.println("==== Empréstimo para confirmar ===");
        int index = 0;
        for (Loan loan : loans) {
            System.out.println(
                    "[" + index + "] ID: " + loan.getId() + ", Título do livro: " + loan.getBookTitle() + ", Pedido: "
                            + loan.getLoanDate() + ", Data de Retorno: " + loan.getReturnDate() + ", Status: "
                            + loan.getStatus());
            index++;
        }
        System.out.println("Selecione o empréstimo para confirmar");
        int indexLoan = Utils.getValidIntegerInput(scanner);

        if (indexLoan < 0 || indexLoan >= loans.size()) {
            System.out.println("Empréstimo inválido.");
            confirmLoan(scanner);
            return;
        }
        Loan loan = loans.get(indexLoan);
        librarianController.confirmLoan(loan.getId());
        System.out.println("Empréstimo Confirmado!");
        System.out.println("");
    }
}

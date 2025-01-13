package views.reader;

import controller.UserLoanController;
import core.utils.Observer;
import core.utils.Utils;
import model.book.Book;
import model.loan.LoanFactory;
import model.loan.Proof;

import java.util.Scanner;

public class ReaderLoanView implements Observer {
    private int userId;

    private UserLoanController controller;
    private Scanner scanner;

    public boolean isOpen = false;

    public ReaderLoanView(UserLoanController controller, int userId) {
        this.controller = controller;
        this.controller.addObserver(this);
        this.userId = userId;
    }

    public void loanBook() {
        System.out.println("");
        System.out.println("==== Livros disponíveis ===");
        int index = 0;
        for (Book book : controller.getAvailableBooks()) {
            System.out.println("[" + index + "] - Title" + book.getTitle() + " - ISBN: " + book.getIsbn());
            index++;
        }
        System.out.println("Escolha o número do Livro que você que solicitar empréstimo:");
        int id = Utils.getValidIntegerInput(scanner);

        if (id < 0 || id >= controller.getAvailableBooks().size()) {
            System.out.println("Livro inválido.");
            loanBook();
            return;
        }
        Book book = controller.getAvailableBooks().get(id);
        controller.loanBook(book.getIsbn(), userId);

    }

    public void init(Scanner scanner) {
        isOpen = true;
        this.scanner = scanner;
        controller.getBooks();
    }

    public void showErrorMessage(String message) {
        if (!isOpen)
            return;

        try {
            if (controller.errorMessage == null || controller.errorMessage.isEmpty()) {
                System.out.println("Ocorreu um erro inesperado");
                return;
            }
            System.out.println("Error: " + message);
            return;
        } finally {
            controller.errorMessage = null;
            controller.hasError = false;
        }
    }

    private void generateProof() {
        Proof proff = LoanFactory.createProff(controller.getCreatedLoan());
        System.out.println(proff.toString());
    }

    @Override
    public void update() {
        if (controller.getCreatedLoan() != null) {
            generateProof();
            dispose();
            return;
        }

        if (controller.isLoading) {
            System.out.println("Carregando...");
            return;
        }

        if (controller.hasError) {
            showErrorMessage(controller.errorMessage);
            dispose();
            return;
        }

        boolean isAvailableBooksEmpty = controller.getAvailableBooks().isEmpty();

        if (controller.getAvailableBooks() == null || isAvailableBooksEmpty) {
            System.out.println("Não há livros disponíveis para empréstimo.");
            dispose();
            return;
        }

        loanBook();
        return;

    }

    void dispose() {
        System.out.println("");
        controller.dispose();
        isOpen = false;
    }
}

package com.example.biblioteca.views;

import com.example.biblioteca.controller.UserLoanController;
import com.example.biblioteca.core.utils.Observer;
import com.example.biblioteca.core.utils.Utils;
import com.example.biblioteca.model.book.Book;

import java.util.Scanner;

public class UserLoanView implements Observer {

    public void loanBook() {

        System.out.println("Escolha o número do Livro que você que solicitar empréstimo:");
        int index = 0;
        for (Book book : controller.getAvaliableBooks()) {
            System.out.println("[" + index + "] - Title" + book.getTitle() + " - ISBN: " + book.getIsbn());
            index++;
        }
        int id = Utils.getValidIntegerInput(scanner);

        if (id < 0 || id >= controller.getAvaliableBooks().size()) {
            System.out.println("Livro inválido.");
            loanBook();
            return;
        }
        Book book = controller.getAvaliableBooks().get(id);
        controller.loanBook(book.getIsbn(), 1);

    }

    private UserLoanController controller;
    private Scanner scanner;

    public boolean isOpen = false;

    public UserLoanView(UserLoanController controller) {
        this.controller = controller;
        this.controller.addObserver(this);
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

    @Override
    public void update() {

        if (controller.getCreatedLoan() != null) {
            System.err.println("cirou");
            dispose();
            return;
        }

        if (controller.isLoading) {
            System.out.println("Carregando...");
            return;
        }

        if (controller.hasError) {
            showErrorMessage(controller.errorMessage);
            return;
        }

        if (controller.getAvaliableBooks() == null || controller.getAvaliableBooks().isEmpty()) {
            System.out.println("Não há livros disponíveis para empréstimo.");
            return;
        }

        if (controller.getAvaliableBooks().size() > 0) {
            loanBook();
            return;
        }

    }

    void dispose() {
        controller.dispose();
        isOpen = false;
    }
}

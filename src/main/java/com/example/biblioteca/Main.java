package com.example.biblioteca;

import com.example.biblioteca.controller.CreateBookController;
import com.example.biblioteca.controller.UserLoanController;
import com.example.biblioteca.core.validations.book.ISBNValidationStrategy;
import com.example.biblioteca.repository.BookRepository;
import com.example.biblioteca.repository.LoanRepository;
import com.example.biblioteca.service.BookService;
import com.example.biblioteca.service.LoanService;
import com.example.biblioteca.views.CreateBookView;
import com.example.biblioteca.views.UserLoanView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        BookService bookService = new BookService(bookRepository, new ISBNValidationStrategy());
        LoanRepository loanRepository = new LoanRepository();
        LoanService loanService = new LoanService(loanRepository, bookRepository);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("===== Biblioteca Menu =====");
            System.out.println("1. Cadastrar Novo Livro");
            System.out.println("2. Solicitar empréstimo");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    CreateBookController createBookController = new CreateBookController(bookService);
                    CreateBookView createBookView = new CreateBookView(createBookController);
                    createBookView.getBookDetailsFromUser(scanner);
                    while (createBookView.isOpen) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 2:
                    UserLoanController userLoanController = new UserLoanController(loanService);
                    UserLoanView userLoanView = new UserLoanView(userLoanController);
                    userLoanView.init(scanner);
                    while (userLoanView.isOpen) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    System.out.println("Saindo...");

                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

}

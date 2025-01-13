package views.librarian;

import java.util.Scanner;

import controller.CreateBookController;
import controller.LibrarianConfirmLoanController;
import core.validations.book.ISBNValidationStrategy;
import repository.BookRepository;
import repository.IBookRepository;
import repository.ILoanRepository;
import repository.LoanRepository;
import service.BookService;
import service.LoanService;

public class LibrarianView {

    IBookRepository bookRepository = new BookRepository();
    BookService bookService = new BookService(bookRepository, new ISBNValidationStrategy());
    ILoanRepository loanRepository = new LoanRepository();
    LoanService loanService = new LoanService(loanRepository, bookRepository);

    public boolean running = false;

    public void displayView(Scanner scanner) {
        running = true;
        while (running) {
            System.out.println("===== Biblioteca Menu =====");
            System.out.println("1. Cadastrar Novo Livro");
            System.out.println("2. Confirmar empréstimo");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    CreateBookController createBookController = new CreateBookController(bookService);
                    CreateBookView createBookView = new CreateBookView(createBookController);
                    createBookView.getBookDetailsFromUser(scanner);
                    while (createBookView.isOpen) {
                        continue;
                    }
                    break;

                case 2:
                    LibrarianConfirmLoanController librarianConfirmLoanController = new LibrarianConfirmLoanController(
                            loanService);
                    LibrarianConfirmLoanView librarianConfirmLoanView = new LibrarianConfirmLoanView(
                            librarianConfirmLoanController);
                    librarianConfirmLoanView.confirmLoan(scanner);
                    break;
                case 3:
                    System.out.println("Saindo...");

                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

    }

}

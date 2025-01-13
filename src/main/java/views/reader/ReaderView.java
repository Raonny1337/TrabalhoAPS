package views.reader;

import java.util.Scanner;

import controller.UserLoanController;
import core.utils.Utils;
import model.user.User;
import repository.BookRepository;
import repository.IBookRepository;
import repository.ILoanRepository;
import repository.LoanRepository;
import service.LoanService;

public class ReaderView {

    private User user;

    public ReaderView(User user) {
        this.user = user;
    }

    IBookRepository bookRepository = new BookRepository();
    ILoanRepository loanRepository = new LoanRepository();
    LoanService loanService = new LoanService(loanRepository, bookRepository);

    public boolean running = false;

    public void displayView(Scanner scanner) {

        running = true;

        while (running) {
            System.out.println("===== Menu do Leitor =====");
            System.out.println("1. Solicitar empréstimo");
            System.out.println("2. Empéstimos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int option = Utils.getValidIntegerInput(scanner);

            switch (option) {
                case 1:
                    UserLoanController userLoanController = new UserLoanController(loanService);
                    ReaderLoanView userLoanView = new ReaderLoanView(userLoanController, user.getId());
                    userLoanView.init(scanner);
                    while (userLoanView.isOpen) {
                        continue;
                    }
                    break;
                case 2:
                    UserLoanController userLoanController2 = new UserLoanController(loanService);
                    userLoanController2.showLoans(user.getId());
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

import java.util.Scanner;

import controller.ReaderController;
import model.user.User;
import views.LoginView;
import views.librarian.LibrarianView;
import views.reader.ReaderView;

public class Main {
    public static void main(String[] args) {
        User currentUser;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("===== Home Menu =====");
            System.out.println("1. Realizar Cadastro de Leitor");
            System.out.println("2. Login de Leitor");
            System.out.println("3. Login de Administrador");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    ReaderController.registerReader();
                    break;

                case 2:
                    LoginView loginview = new LoginView();
                    currentUser = loginview.displayLoginFormReader(scanner);

                    if (currentUser != null) {
                        ReaderView readerView = new ReaderView(currentUser);
                        readerView.displayView(scanner);
                        while (readerView.running) {
                            continue;
                        }
                    }
                    break;
                case 3:
                    LoginView loginAdmView = new LoginView();
                    currentUser = loginAdmView.displayLoginFormAdministrator(scanner);
                    if (currentUser != null) {
                        LibrarianView librarianView = new LibrarianView();
                        librarianView.displayView(scanner);
                        while (librarianView.running) {
                            continue;
                        }
                    }
                    break;
                case 4:
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

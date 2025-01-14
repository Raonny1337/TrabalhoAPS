package views;

import java.util.Scanner;

import controller.LibrarianController;
import controller.ReaderController;
import model.user.User;
import model.user.UserType;

public class LoginView {

    public LoginView() {

    };

    public User displayLoginFormReader(Scanner scanner) {
        return displayLogin(scanner, UserType.READER);
    }

    public User displayLoginFormAdministrator(Scanner scanner) {
        return displayLogin(scanner, UserType.ADMINISTRATOR);
    }

    public User displayLogin(Scanner scanner, UserType type) {
        System.out.println("Digite o nome de usuário: ");
        String username = scanner.nextLine().toLowerCase();
        System.out.println("Digite a senha: ");
        String password = scanner.nextLine();
        if (type.equals(UserType.READER)) {
            return ReaderController.loginReader(username, password);
        } else {
            return LibrarianController.loginLibrarian(username, password);
        }
    }
}

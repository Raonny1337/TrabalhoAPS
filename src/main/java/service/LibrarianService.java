package service;

import model.user.Librarian;
import model.user.LibrarianFactory;
import model.user.User;
import repository.LibrarianRepository;

import java.util.List;
import java.util.Scanner;

public class LibrarianService {
    private static final Scanner scanner = new Scanner(System.in);

    public static List<Librarian> getLibrarians() {
        return LibrarianRepository.getLibrarians();
    }

    public static void registerLibrarian() {
        System.out.println("Digite o nome de usuário: ");
        String username = scanner.nextLine().toLowerCase();
        List<String> usernames = getLibrarians().stream().map(User::getUsername).toList();
        while(usernames.contains(username)) {
            System.out.println("Nome de usuário já cadastrado, digite outro:");
            username = scanner.nextLine().toLowerCase();
        }
        System.out.println("Digite uma senha: ");
        String password = scanner.nextLine();
        System.out.println("Digite seu nome: ");
        String name = scanner.nextLine();
        LibrarianRepository.registerReader(LibrarianFactory.createLibrarian(username,password,name));
    }

    public static Librarian loginLibrarian(String username, String password){
        List<Librarian> librarians = getLibrarians();
        Librarian librarian = librarians.stream()
                .filter(l -> l.getUsername().equals(username)).findAny().orElse(null);
        if(librarian == null) {
            System.out.println("Nome de usuário não encontrado.");
        }else {
            if(librarian.getPassword().equals(password)) {
                System.out.println("Bem vindo " + librarian.getName() + "!");
                return librarian;
            }else {
                System.out.println("Senha incorreta.");

            }
        }
        return null;
    }
}

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
        System.out.println("Enter the username: ");
        String username = scanner.nextLine().toLowerCase();
        List<String> usernames = getLibrarians().stream().map(User::getUsername).toList();
        while(usernames.contains(username)) {
            System.out.println("Username already registered, enter another one: ");
            username = scanner.nextLine().toLowerCase();
        }
        System.out.println("Enter a password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        LibrarianRepository.registerReader(LibrarianFactory.createLibrarian(username,password,name));
    }

    public static Librarian loginLibrarian(String username, String password){
        List<Librarian> librarians = getLibrarians();
        Librarian librarian = librarians.stream()
                .filter(l -> l.getUsername().equals(username)).findAny().orElse(null);
        if(librarian == null) {
            System.out.println("User not found.");
        }else {
            if(librarian.getPassword().equals(password)) {
                System.out.println("Welcome " + librarian.getName() + ".");
                return librarian;
            }else {
                System.out.println("Incorrect password.");

            }
        }
        return null;
    }
}

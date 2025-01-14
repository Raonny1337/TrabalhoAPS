package service;

import model.user.Reader;
import model.user.ReaderFactory;
import model.user.User;
import repository.ReaderRepository;

import java.util.List;
import java.util.Scanner;

public class ReaderService {
    private static final Scanner scanner = new Scanner(System.in);

    public static List<Reader> getReaders() {
        return ReaderRepository.getReaders();
    }

    public static void registerReader() {
        System.out.println("Digite o nome de usuário: ");
        String username = scanner.nextLine().toLowerCase();
        List<String> usernames = getReaders().stream().map(User::getUsername).toList();
        while(usernames.contains(username)) {
            System.out.println("Nome de usuário já cadastrado, digite outro: ");
            username = scanner.nextLine().toLowerCase();
        }
        System.out.println("Digite uma senha: ");
        String password = scanner.nextLine();
        System.out.println("Digite seu nome: ");
        String name = scanner.nextLine();
        ReaderRepository.registerReader(ReaderFactory.createReader(username,password,name));
    }

    public static Reader loginReader(String username, String password){
        List<Reader> readers = getReaders();
        Reader reader = readers.stream()
                .filter(r -> r.getUsername().equals(username)).findAny().orElse(null);
        if(reader == null) {
            System.out.println("Nome de usuário não encontrado.");
        }else {
            if(reader.getPassword().equals(password)) {
                System.out.println("Bem vindo " + reader.getName() + "!");
                return reader;
            }else {
                System.out.println("Senha incorreta.");

            }
        }
        return null;
    }
}

package views.librarian;

import java.util.Scanner;
import controller.CreateBookController;
import core.utils.Observer;
import core.utils.Utils;
import model.book.Book;
import model.book.BookFactory;

public class CreateBookView implements Observer {

    private CreateBookController controller;

    public boolean isOpen = false;

    public CreateBookView(CreateBookController controller) {
        this.controller = controller;
        controller.addObserver(this);
    }

    public void getBookDetailsFromUser(Scanner scanner) {
        isOpen = true;

        System.out.println("Digite título do livro");
        String title = scanner.nextLine();
        System.out.println("Digite o autor:");
        String author = scanner.nextLine();
        System.out.println("Digite o ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Digite a descrição:");
        String description = scanner.nextLine();

        int availableCopies = Utils.getValidIntegerInput("Digite o número de cópias: ", scanner);

        Book book = BookFactory.createBook(title, author, isbn, description, availableCopies);
        controller.registerBook(book);
    }

    public void showConfirmation(Book book) {
        if (!isOpen)
            return;
        System.out.println("==================");
        System.out.println("Livro registrado com sucesso!");
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Titulo: " + book.getTitle());
        System.out.println("Autor: " + book.getAuthor());
        System.out.println("Cópias: " + book.getAvailableCopies());
        System.out.println("");
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
        if (controller.hasError) {
            showErrorMessage(controller.errorMessage);
            dispose();
            return;
        }

        if (controller.isLoading) {
            System.out.println("Carregando...");
            return;
        }

        if (controller.getRegisterBook() != null) {
            showConfirmation(controller.getRegisterBook());
            dispose();
        }
    }

    public void dispose() {
        System.out.println("");
        isOpen = false;
        controller.dispose();
    }

}

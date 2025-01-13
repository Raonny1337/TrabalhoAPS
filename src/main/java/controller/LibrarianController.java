package controller;

import model.user.Librarian;
import service.LibrarianService;

public class LibrarianController {
    public static void registerLibrarian(){
        LibrarianService.registerLibrarian();
    }

    public static Librarian loginLibrarian(String username, String password){
        return LibrarianService.loginLibrarian(username, password);
    }
}

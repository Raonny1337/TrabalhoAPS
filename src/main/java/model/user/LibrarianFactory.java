package model.user;

public class LibrarianFactory {
    public static Librarian createLibrarian(String username, String password, String name) {
        Librarian librarian = new Librarian();
        librarian.setUsername(username);
        librarian.setPassword(password);
        librarian.setName(name);
        return librarian;
    }
}

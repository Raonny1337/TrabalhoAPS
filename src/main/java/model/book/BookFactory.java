package model.book;

public class BookFactory {
    static public Book createBook(String title, String author, String isbn, String description, int availableCopies) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setAvailableCopies(availableCopies);
        return book;
    }

    static public Book createBookFromData(int Id, String title, String author, String isbn, String description,
            int availableCopies) {
        Book book = new Book();
        book.setId(Id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setAvailableCopies(availableCopies);
        return book;
    }
}

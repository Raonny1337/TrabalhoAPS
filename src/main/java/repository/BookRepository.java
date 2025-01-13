package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import core.connection.DatabaseConnection;
import model.book.Book;
import model.book.BookFactory;

public class BookRepository implements IBookRepository {
    @Override
    public Book save(Book book) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = createSaveBookQuery(conn, book);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Falhou ao salvar o livro: Nenhuma linha foi afetada.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                book.setId(rs.getInt(1));
            }

            return book;
        } catch (SQLException e) {
            throw new Exception("Falhou ao salvar o livro: " + e.getMessage());
        }
    }

    private static PreparedStatement createSaveBookQuery(Connection conn, Book book) throws SQLException {
        String sql = "INSERT INTO book (isbn, title, author, description, available_copies) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, book.getIsbn());
        ps.setString(2, book.getTitle());
        ps.setString(3, book.getAuthor());
        ps.setString(4, book.getDescription());
        ps.setInt(5, book.getAvailableCopies());
        return ps;
    }

    @Override
    public Book findByIsbn(String isbn) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = createFindBookByIsbnQuery(conn, isbn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return BookFactory.createBookFromData(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getString("description"),
                        rs.getInt("available_copies"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new Exception("Falhou ao buscar o livro: " + e.getMessage());
        }
    }

    private static PreparedStatement createFindBookByIsbnQuery(Connection conn, String isbn) throws SQLException {
        String sql = "SELECT * FROM book WHERE isbn = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, isbn);
        return ps;
    }

}

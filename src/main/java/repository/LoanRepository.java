package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.connection.DatabaseConnection;
import model.book.Book;
import model.book.BookFactory;
import model.loan.Loan;
import model.loan.LoanStatus;

public class LoanRepository implements ILoanRepository {
    @Override
    public Loan save(Loan loan) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = createSaveLoanQuery(conn, loan);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Falhou ao salvar o empréstimo com ID: " + loan.getId());
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                loan.setId(generatedKeys.getInt(1));
            }

            return loan;
        } catch (SQLException e) {
            throw new Exception("Falhou ao salvar o empréstimo: " + e.getMessage());
        }
    }

    private static PreparedStatement createSaveLoanQuery(Connection conn, Loan loan) throws SQLException {
        String sql = "INSERT INTO biblioteca.loan (reader_id, loanDate, returnDate, status, book_id) "
                + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, loan.getUserId());
        ps.setDate(2, java.sql.Date.valueOf(loan.getLoanDate()));
        ps.setDate(3, java.sql.Date.valueOf(loan.getReturnDate()));
        ps.setString(4, loan.getStatus().name());
        ps.setInt(5, loan.getBookId());
        return ps;
    }

    @Override
    public List<Book> getAvailableBooksToLoan() throws Exception {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = createPrepareStatementFindBooks(conn);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = BookFactory.createBookFromData(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getString("description"),
                        rs.getInt("available_copies"));

                books.add(book);
            }
        } catch (SQLException e) {
            throw new Exception("Falhou para pegar os livros");
        }
        return books;
    }

    private static PreparedStatement createPrepareStatementFindBooks(Connection conn) throws SQLException {
        String sql = "SELECT b.* FROM book b LEFT JOIN loan l ON b.id = l.book_id AND l.status = 'CONFIRMED' GROUP BY b.id HAVING COUNT(l.id) < b.available_copies;";
        return conn.prepareStatement(sql);
    }

    @Override
    public boolean bookIsValidForLoan(String isbn) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = createQueryBookIsValid(conn, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_valid_for_loan");
            }

            return true;
        } catch (SQLException e) {
            throw new Exception("Falhou ao saber se o livro é válido para empréstimo" + e.getMessage());
        }
    }

    private static PreparedStatement createQueryBookIsValid(Connection conn, String isbn) throws SQLException {
        String sql = "SELECT CASE WHEN COUNT(l.id) < b.available_copies THEN TRUE ELSE FALSE END AS is_valid_for_loan FROM book b LEFT JOIN loan l ON b.id = l.book_id AND l.status = 'CONFIRMED' WHERE b.isbn = ? GROUP BY b.id;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, isbn);
        return ps;
    }

    @Override
    public boolean userIsValidForLoan(int userId) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = createQueryUserIsValid(conn, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_valid_for_loan");
            }
            return true;
        } catch (SQLException e) {
            throw new Exception("Falhou ao saber se o usuário é válido para empréstimo: " + e.getMessage());
        }
    }

    private static PreparedStatement createQueryUserIsValid(Connection conn, int userId) throws SQLException {
        String sql = "SELECT CASE WHEN COUNT(l.id) = 0 THEN TRUE ELSE FALSE END AS is_valid_for_loan " +
                "FROM loan l WHERE l.reader_id = ? AND l.status = 'CONFIRMED';";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        return ps;
    }

    @Override
    public void confirmLoan(int loanId) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = createConfirmLoanQuery(conn, loanId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Nenhum empréstimo encontrado com o ID: " + loanId);
            }
        } catch (SQLException e) {
            throw new Exception("Falhou ao confirmar o empréstimo: " + e.getMessage());
        }
    }

    private static PreparedStatement createConfirmLoanQuery(Connection conn, int loanId) throws SQLException {
        String sql = "UPDATE loan SET status = 'CONFIRMED' WHERE id = ? AND status != 'CONFIRMED';";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, loanId);
        return ps;
    }

    @Override
    public List<Loan> getAllLoansByUserId(int userId) throws Exception {
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = createPrepareStatementFindLoansByUserId(conn, userId);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("id"));
                loan.setUserId(rs.getInt("reader_id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setBookTitle(rs.getString("book_title"));
                loan.setLoanDate(rs.getDate("loanDate").toLocalDate());
                loan.setReturnDate(rs.getDate("returnDate").toLocalDate());
                loan.setStatus(LoanStatus.valueOf(rs.getString("status")));

                loans.add(loan);
            }
        } catch (SQLException e) {
            throw new Exception("Falhou ao pegar os empréstimos do usuário com ID: " + userId, e);
        }
        return loans;
    }

    private static PreparedStatement createPrepareStatementFindLoansByUserId(Connection conn, int userId)
            throws SQLException {
        String sql = "SELECT l.id, l.reader_id, l.book_id, b.title AS book_title, l.loanDate, l.returnDate, l.status " +
                "FROM loan l " +
                "JOIN book b ON l.book_id = b.id " +
                "WHERE l.reader_id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        return ps;
    }

    @Override
    public List<Loan> getAllLoans() throws Exception {
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = createPrepareStatementFindAllLoans(conn);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("id"));
                loan.setUserId(rs.getInt("reader_id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setBookTitle(rs.getString("book_title"));
                loan.setLoanDate(rs.getDate("loanDate").toLocalDate());
                loan.setReturnDate(rs.getDate("returnDate").toLocalDate());
                loan.setStatus(LoanStatus.valueOf(rs.getString("status")));

                loans.add(loan);
            }
        } catch (SQLException e) {
            throw new Exception("Falhou ao pegar todos os empréstimos", e);
        }
        return loans;
    }

    private static PreparedStatement createPrepareStatementFindAllLoans(Connection conn) throws SQLException {
        String sql = "SELECT l.id, l.reader_id, l.book_id, b.title AS book_title, l.loanDate, l.returnDate, l.status " +
                "FROM loan l " +
                "JOIN book b ON l.book_id = b.id where l.status = 'PENDING_CONFIRMATION' ";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps;
    }

}
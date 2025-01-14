package repository;

import core.connection.DatabaseConnection;
import model.user.Librarian;
import model.user.LibrarianFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianRepository {
    public static List<Librarian> getLibrarians() {
        List<Librarian> librarians = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = createPrepareStatementGetReaders(conn);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Librarian librarian = LibrarianFactory.createLibrarian(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"));
                librarian.setId(rs.getInt("id"));
                librarians.add(librarian);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao tentar encontrar bibliotecários.");
        }
        return librarians;
    }

    private static PreparedStatement createPrepareStatementGetReaders(Connection conn) throws SQLException {
        String sql = "SELECT * FROM biblioteca.librarian;";
        return conn.prepareStatement(sql);
    }

    public static void registerReader(Librarian librarian) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = createPrepareStatementRegisterReader(conn, librarian)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao tentar inserir o bibliotecário.");
        }
    }

    private static PreparedStatement createPrepareStatementRegisterReader(Connection conn, Librarian librarian)
            throws SQLException {
        String sql = "INSERT INTO `biblioteca`.`librarian` (`username`, `password`, `name`) VALUES (?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, librarian.getUsername());
        ps.setString(2, librarian.getPassword());
        ps.setString(3, librarian.getName());
        return ps;
    }
}

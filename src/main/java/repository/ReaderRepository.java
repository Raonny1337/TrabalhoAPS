package repository;

import core.connection.DatabaseConnection;
import model.user.Reader;
import model.user.ReaderFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderRepository {

    public static List<Reader> getReaders() {
        List<Reader> readers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = createPrepareStatementGetReaders(conn);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reader reader = ReaderFactory.createReader(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"));
                reader.setId(rs.getInt("id"));
                readers.add(reader);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao tentar encontrar leitores.");
        }
        return readers;
    }

    private static PreparedStatement createPrepareStatementGetReaders(Connection conn) throws SQLException {
        String sql = "SELECT * FROM biblioteca.reader;";
        return conn.prepareStatement(sql);
    }

    public static void registerReader(Reader reader) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = createPrepareStatementRegisterReader(conn, reader)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao tentar inserir o leitor.");
        }
    }

    private static PreparedStatement createPrepareStatementRegisterReader(Connection conn, Reader reader)
            throws SQLException {
        String sql = "INSERT INTO `biblioteca`.`reader` (`username`, `password`, `name`) VALUES (?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, reader.getUsername());
        ps.setString(2, reader.getPassword());
        ps.setString(3, reader.getName());
        return ps;
    }
}

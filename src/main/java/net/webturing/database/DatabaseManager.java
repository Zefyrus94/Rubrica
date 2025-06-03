package net.webturing.database;
import net.webturing.model.Persona;

import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/rubrica";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public void aggiungiPersona(Persona p) {
        String sql = "INSERT INTO persona (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, p.getNome());
                stmt.setString(2, p.getCognome());
                stmt.setString(3, p.getIndirizzo());
                stmt.setString(4, p.getTelefono());
                stmt.setInt(5, p.getEta());

                stmt.executeUpdate();

                // 🔑 Recupera l'ID generato
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    p.setId(id); // ← imposta l'ID sulla persona appena salvata
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aggiornaPersona(Persona p, long id) {
        String sql = "UPDATE persona SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getEta());
            ps.setLong(6, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminaPersona(long id) {
        String sql = "DELETE FROM persona WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Persona> caricaPersone() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM persona";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta")
                );
                p.setId(rs.getInt("id")); // aggiungi campo ID nella classe Persona
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean verificaCredenziali(String username, String password) {
        String sql = "SELECT COUNT(*) FROM utenti WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // esiste almeno 1 riga → credenziali corrette
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}


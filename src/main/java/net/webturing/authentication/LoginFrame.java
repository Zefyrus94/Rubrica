package net.webturing.authentication;

import net.webturing.persone.FinestraPrincipale;
import net.webturing.database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;

    public LoginFrame(File filePersone) {
        super("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Campi input
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        fieldsPanel.add(new JLabel("Utente:"));
        userField = new JTextField();
        fieldsPanel.add(userField);

        fieldsPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        fieldsPanel.add(passwordField);

        // Bottone login
        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passwordField.getPassword());

            if (checkLogin(username, password)) {
                dispose(); // chiudi login
                SwingUtilities.invokeLater(() -> {
                    FinestraPrincipale main = new FinestraPrincipale(filePersone);
                    main.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(fieldsPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
    }

    private boolean checkLogin(String username, String password) {
        // Semplice controllo hardcoded (puoi migliorarlo dopo)
        //return user.equals("admin") && pass.equals("1234");
        DatabaseManager db = new DatabaseManager();
        if (db.verificaCredenziali(username, password)) {
            // Login corretto → apri finestra principale
            return true;
        }/* else {
            JOptionPane.showMessageDialog(this, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE);
        }*/
        return false;
    }
}


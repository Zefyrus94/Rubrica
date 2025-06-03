package net.webturing.persone;

import net.webturing.model.Persona;

import javax.swing.*;
import java.awt.*;

// Finestra editor persona
class EditorPersona extends JFrame {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField indirizzoField;
    private JTextField telefonoField;
    private JTextField etaField;

    private FinestraPrincipale mainFrame;
    private int personaIndex; // -1 se nuova, altrimenti indice da modificare

    public EditorPersona(FinestraPrincipale mainFrame, Persona personaDaModificare, int index) {
        super("EDITOR PERSONA");
        this.mainFrame = mainFrame;
        this.personaIndex = index;

        setSize(400, 300);
        setLocationRelativeTo(mainFrame);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        nomeField = new JTextField();
        nomeField.setName("nomeField");
        cognomeField = new JTextField();
        cognomeField.setName("cognomeField");
        indirizzoField = new JTextField();
        indirizzoField.setName("indirizzoField");
        telefonoField = new JTextField();
        telefonoField.setName("telefonoField");
        etaField = new JTextField();
        etaField.setName("etaField");

        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(nomeField);
        inputPanel.add(new JLabel("Cognome:"));
        inputPanel.add(cognomeField);
        inputPanel.add(new JLabel("Indirizzo:"));
        inputPanel.add(indirizzoField);
        inputPanel.add(new JLabel("Telefono:"));
        inputPanel.add(telefonoField);
        inputPanel.add(new JLabel("Età:"));
        inputPanel.add(etaField);

        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(inputPanel, BorderLayout.CENTER);

        if (personaDaModificare != null) {
            nomeField.setText(personaDaModificare.getNome());
            cognomeField.setText(personaDaModificare.getCognome());
            indirizzoField.setText(personaDaModificare.getIndirizzo());
            telefonoField.setText(personaDaModificare.getTelefono());
            etaField.setText(String.valueOf(personaDaModificare.getEta()));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton salvaButton = new JButton("Salva");
        salvaButton.setName("salvaButton");
        JButton annullaButton = new JButton("Annulla");
        annullaButton.setName("annullaButton");
        buttonPanel.add(salvaButton);
        buttonPanel.add(annullaButton);
        add(buttonPanel, BorderLayout.SOUTH);

        salvaButton.addActionListener(e -> {
            try {
                String nome = nomeField.getText().trim();
                String cognome = cognomeField.getText().trim();
                String indirizzo = indirizzoField.getText().trim();
                String telefono = telefonoField.getText().trim();
                int eta = Integer.parseInt(etaField.getText().trim());

                Persona nuovaPersona = new Persona(nome, cognome, indirizzo, telefono, eta);

                if (personaIndex == -1) {
                    mainFrame.salvaPersonaSuFile(nuovaPersona);
                    mainFrame.aggiungiPersona(nuovaPersona);
                } else {
                    //rivedi
                    if (personaDaModificare != null) {
                        nuovaPersona.setId(personaDaModificare.getId());
                    }
                    mainFrame.aggiornaPersona(personaIndex, nuovaPersona);
                }

                // Dopo inserimento o modifica

                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Età deve essere un numero intero.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        annullaButton.addActionListener(e -> dispose());
    }
}


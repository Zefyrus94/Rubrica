package net.webturing.persone;

import net.webturing.database.DatabaseManager;
import net.webturing.model.Persona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Finestra principale con JTable
public class FinestraPrincipale extends JFrame {
    private JTable tabella;
    private DefaultTableModel tabellaModel;
    private List<Persona> persone;
    private DatabaseManager db = new DatabaseManager();

    private final File filePersone;

    private void caricaPersoneDaDatabase(){
        persone = db.caricaPersone();
        for(Persona p: persone){
            if (p != null) {
                tabellaModel.addRow(new Object[]{ p.getNome(), p.getCognome(), p.getTelefono() });
            }
        }

    }
    private void caricaPersoneDaFile() {
        if (!filePersone.exists()) return;

        try (Scanner scanner = new Scanner(filePersone)) {
            while (scanner.hasNextLine()) {
                String riga = scanner.nextLine();
                Persona p = Persona.fromString(riga);
                if (p != null) {
                    persone.add(p);
                    tabellaModel.addRow(new Object[]{ p.getNome(), p.getCognome(), p.getTelefono() });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Errore nella lettura del file.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void salvaPersoneSuFile() {
        try (PrintStream out = new PrintStream(filePersone)) {
            for (Persona p : persone) {
                out.println(p.toFileString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Errore nel salvataggio del file.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    public FinestraPrincipale(File filePersone) {
        super("Gestione Persone");
        this.filePersone = filePersone;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        persone = new ArrayList<>();

        String[] colonne = { "Nome", "Cognome", "Telefono" };
        tabellaModel = new DefaultTableModel(colonne, 0);
        tabella = new JTable(tabellaModel);
        tabella.setName("tabellaPersone");
        JScrollPane scrollPane = new JScrollPane(tabella);
        add(scrollPane, BorderLayout.CENTER);

        //vecchia gestione bottoni
        /*JButton nuovoButton = new JButton("Nuovo");
        nuovoButton.setName("nuovoButton");
        JButton modificaButton = new JButton("Modifica");
        modificaButton.setName("modificaButton");
        JButton eliminaButton = new JButton("Elimina");
        eliminaButton.setName("eliminaButton");

        JPanel btnPanel = new JPanel();
        btnPanel.add(nuovoButton);
        btnPanel.add(modificaButton);
        btnPanel.add(eliminaButton);
        add(btnPanel, BorderLayout.SOUTH);*/
        //nuova gestione bottoni
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // impedisce di spostare la toolbar

// Bottoni con testo e/o icone
        JButton nuovoButton = new JButton("Nuovo");
        nuovoButton.setName("nuovoButton");

        JButton modificaButton = new JButton("Modifica");
        modificaButton.setName("modificaButton");

        JButton eliminaButton = new JButton("Elimina");
        eliminaButton.setName("eliminaButton");
        //dim btn
        Dimension btnSize = new Dimension(300, 40); // larghezza, altezza

        nuovoButton.setPreferredSize(btnSize);
        modificaButton.setPreferredSize(btnSize);
        eliminaButton.setPreferredSize(btnSize);
        //fine dim btn
// Se hai immagini, puoi fare così:
        //nuovoButton.setIcon(new ImageIcon(getClass().getResource("/icons/nuovo.png")));
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/icons/nuovo.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        nuovoButton.setIcon(new ImageIcon(scaled));

        //modificaButton.setIcon(new ImageIcon(getClass().getResource("/icons/modifica.png")));
        rawIcon = new ImageIcon(getClass().getResource("/icons/modifica.png"));
        scaled = rawIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        modificaButton.setIcon(new ImageIcon(scaled));

        //eliminaButton.setIcon(new ImageIcon(getClass().getResource("/icons/elimina.png")));
        rawIcon = new ImageIcon(getClass().getResource("/icons/elimina.png"));
        scaled = rawIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        eliminaButton.setIcon(new ImageIcon(scaled));

// Aggiungi bottoni alla toolbar
        toolBar.add(nuovoButton);
        toolBar.add(modificaButton);
        toolBar.add(eliminaButton);

// Aggiungi la toolbar in alto
        add(toolBar, BorderLayout.NORTH);

        //fine nuova gestione
        nuovoButton.addActionListener(e -> {
            EditorPersona editor = new EditorPersona(this, null, -1);
            editor.setName("editorPersona");
            editor.setVisible(true);
        });

        modificaButton.addActionListener(e -> {
            int selectedRow = tabella.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Seleziona una persona da modificare.",
                        "Errore",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Persona p = persone.get(selectedRow);
            EditorPersona editor = new EditorPersona(this, p, selectedRow);
            editor.setName("editorPersona");
            editor.setVisible(true);
        });

        eliminaButton.addActionListener(e -> {
            int selectedRow = tabella.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Seleziona una persona da eliminare.",
                        "Errore",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Persona p = persone.get(selectedRow);
            String messaggio = String.format("Vuoi eliminare la persona %s %s?", p.getNome(), p.getCognome());

            int conferma = JOptionPane.showConfirmDialog(
                    this,
                    messaggio,
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION
            );

            if (conferma == JOptionPane.YES_OPTION) {
                //vecchia gestione da file
                /*

                salvaPersoneSuFile();                     // Salva aggiornato su file*/
                Long id = p.getId();
                db.eliminaPersona(id);
                //persone = db.caricaPersone();
                persone.remove(selectedRow);              // Rimuove dalla lista
                tabellaModel.removeRow(selectedRow);      // Rimuove dalla tabella
            }
        });

        //vecchia gestione da file
        //caricaPersoneDaFile();
        caricaPersoneDaDatabase();
    }

    public void aggiungiPersona(Persona p) {


        db.aggiungiPersona(p);
        //persone = db.caricaPersone(); // ricarica dal DB
        tabellaModel.addRow(new Object[]{ p.getNome(), p.getCognome(), p.getTelefono() });
        persone.add(p);

    }

    public void aggiornaPersona(int index, Persona aggiornata) {


        Long id = aggiornata.getId();
        db.aggiornaPersona(aggiornata, id);
        /*persone = db.caricaPersone();*/
        persone.set(index, aggiornata);

        tabellaModel.setValueAt(aggiornata.getNome(), index, 0);
        tabellaModel.setValueAt(aggiornata.getCognome(), index, 1);
        tabellaModel.setValueAt(aggiornata.getTelefono(), index, 2);

    }
}

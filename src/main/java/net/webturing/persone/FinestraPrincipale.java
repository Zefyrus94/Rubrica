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
    private final File filePersone;
    public List<Persona> caricaPersoneDaFile() {
        List<Persona> persone = new ArrayList<>();
        File dir = new File("informazioni");
        if (!dir.exists()) return persone;

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null) return persone;

        for (File file : files) {
            try (Scanner sc = new Scanner(file)) {
                if (sc.hasNextLine()) {
                    String linea = sc.nextLine();
                    String[] campi = linea.split(";");
                    if (campi.length == 5) {
                        String nome = campi[0];
                        String cognome = campi[1];
                        String indirizzo = campi[2];
                        String telefono = campi[3];
                        int eta = Integer.parseInt(campi[4]);

                        // Estrai ID dal nome file (ultima parte dopo il secondo '-')
                        String baseName = file.getName().replace(".txt", "");
                        String[] parts = baseName.split("-");
                        Long id = parts.length >= 3 ? Long.parseLong(parts[2]) : null;

                        Persona p = new Persona(id, nome, cognome, indirizzo, telefono, eta);
                        persone.add(p);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // ignora file corrotti o malformati
            }
        }

        return persone;
    }

    public void salvaPersonaSuFile(Persona p) {
        File dir = new File("informazioni");
        if (!dir.exists()) dir.mkdirs();

        // Genera nome file sicuro
        Long id = (p.getId() != null) ? p.getId() : System.currentTimeMillis();

        String fileName = String.format("%s-%s-%d.txt",
                p.getNome().replaceAll("\\s+", "_"),
                p.getCognome().replaceAll("\\s+", "_"),
                id
        );

        File outFile = new File(dir, fileName);

        try (PrintStream out = new PrintStream(outFile)) {
            String linea = String.join(";",
                    p.getNome(),
                    p.getCognome(),
                    p.getIndirizzo(),
                    p.getTelefono(),
                    String.valueOf(p.getEta())
            );
            out.println(linea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void aggiornaPersona(int index, Persona nuovaPersona) {
        Long id = persone.get(index).getId();
        nuovaPersona.setId(id); // Mantieni l'id esistente

        persone.set(index, nuovaPersona);

        eliminaFilePersona(id);              // elimina file vecchio
        salvaPersonaSuFile(nuovaPersona);    // salva file aggiornato
    }

    //tofix
    public void eliminaPersona(int index) {
        Persona p = persone.get(index);
        persone.remove(index);
        eliminaFilePersona(p.getId()); // rimuove il file associato
    }

    public void eliminaFilePersona(Long id) {
        File dir = new File("informazioni");
        if (!dir.exists()) return;

        File[] files = dir.listFiles((d, name) -> name.matches(".*_" + id + "\\.txt$"));
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
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
                Long id = p.getId();
                //tofix cancellazione
                eliminaPersona(selectedRow);
                tabellaModel.removeRow(selectedRow);      // Rimuove dalla tabella
            }
        });
        //vecchia gestione da file
        this.persone = caricaPersoneDaFile();
    }
}

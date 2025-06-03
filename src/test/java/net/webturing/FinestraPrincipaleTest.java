package net.webturing;

import net.webturing.persone.FinestraPrincipale;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.*;

import org.assertj.swing.edt.GuiActionRunner;

import java.io.File;
import java.io.IOException;
import static org.assertj.swing.data.TableCell.row;

public class FinestraPrincipaleTest {

    private File testFile;
    private FrameFixture window;

    @BeforeAll
    public static void installEDTCheck() {
        FailOnThreadViolationRepaintManager.install(); // obbliga tutto a stare nell'EDT
    }

    //se fosse un file unico
    /*@BeforeEach
    public void setUp() throws IOException {
        testFile = new File("test_informazioni.txt");

        // cancella eventuali contenuti precedenti
        if (testFile.exists()) {
            testFile.delete();
        }
        testFile.createNewFile();

        FinestraPrincipale frame = GuiActionRunner.execute(() -> new FinestraPrincipale(testFile));
        window = new FrameFixture(frame);
        window.show(); // mostra la finestra
    }*/

    public void setUpConFile(File file) throws IOException {
        testFile = file;
        if (testFile.exists()) testFile.delete();
        testFile.createNewFile();

        FinestraPrincipale frame = GuiActionRunner.execute(() -> new FinestraPrincipale(testFile));
        window = new FrameFixture(frame);
        window.show();
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    /*@Test
    public void testAggiuntaPersona() throws IOException {
        // test GUI qui
        // Clicca sul pulsante "Nuovo"
        window.button("nuovoButton").click();
        FrameFixture editor = WindowFinder.findFrame("editorPersona").using(window.robot());

        // Compila i campi nella finestra EditorPersona
        editor.textBox("nomeField").enterText("Mario");
        editor.textBox("cognomeField").enterText("Rossi");
        editor.textBox("indirizzoField").enterText("Via Roma 1");
        editor.textBox("telefonoField").enterText("1234567890");
        editor.textBox("etaField").enterText("30");

        // Clicca sul pulsante "Salva"
        editor.button("salvaButton").click();

        // Verifica che la tabella contenga la nuova persona
        window.table("tabellaPersone").requireContents(new String[][] {
                {"Mario", "Rossi", "1234567890"}
        });
    }*/
    /*@Test
    public void testModificaDopoAggiunta() {
        // Aggiungi una persona
        window.button("nuovoButton").click();

        FrameFixture editor = WindowFinder.findFrame("editorPersona").using(window.robot());

        // Compila i campi nella finestra EditorPersona
        editor.textBox("nomeField").enterText("Mario");
        editor.textBox("cognomeField").enterText("Rossi");
        editor.textBox("indirizzoField").enterText("Via Roma 1");
        editor.textBox("telefonoField").enterText("1234567890");
        editor.textBox("etaField").enterText("30");

        // Clicca sul pulsante "Salva"
        editor.button("salvaButton").click();

        // Seleziona la persona nella tabella
        window.table("tabellaPersone").selectRows(0);

        // Clic su Modifica
        window.button("modificaButton").click();

        // Recupera la finestra EditorPersona
        // Dopo aver cliccato su "Modifica"
        FrameFixture editorModifica = WindowFinder.findFrame("editorPersona").using(window.robot());

// Verifica che i campi siano valorizzati correttamente
        editorModifica.textBox("nomeField").requireText("Mario");
        editorModifica.textBox("cognomeField").requireText("Rossi");
        editorModifica.textBox("indirizzoField").requireText("Via Roma 1");
        editorModifica.textBox("telefonoField").requireText("1234567890");
        editorModifica.textBox("etaField").requireText("30");
        // Puoi chiudere cliccando su Annulla
        editorModifica.button("annullaButton").click();
    }*/
    /*@Test
    public void testEliminaEModificaPersone() {
        // 1. Aggiungi ANNA BIANCHI
        window.button("nuovoButton").click();
        FrameFixture editor1 = WindowFinder.findFrame("editorPersona").using(window.robot());
        editor1.textBox("nomeField").enterText("Anna");
        editor1.textBox("cognomeField").enterText("Bianchi");
        editor1.textBox("indirizzoField").enterText("Via A");
        editor1.textBox("telefonoField").enterText("1111");
        editor1.textBox("etaField").enterText("25");
        editor1.button("salvaButton").click();

        // 2. Aggiungi LUCA VERDI
        window.button("nuovoButton").click();

        FrameFixture editor2 = WindowFinder.findFrame("editorPersona").using(window.robot());

        editor2.textBox("nomeField").enterText("Luca");
        editor2.textBox("cognomeField").enterText("Verdi");
        editor2.textBox("indirizzoField").enterText("Via B");
        editor2.textBox("telefonoField").enterText("2222");
        editor2.textBox("etaField").enterText("28");
        editor2.button("salvaButton").click();
        // 3. Aggiungi PAOLO NERI
        window.button("nuovoButton").click();
        FrameFixture editor3 = WindowFinder.findFrame("editorPersona").using(window.robot());
        editor3.textBox("nomeField").enterText("Paolo");
        editor3.textBox("cognomeField").enterText("Neri");
        editor3.textBox("indirizzoField").enterText("Via C");
        editor3.textBox("telefonoField").enterText("3333");
        editor3.textBox("etaField").enterText("40");
        editor3.button("salvaButton").click();

        // 4. Elimina la persona al centro: LUCA VERDI (riga 1)
        window.table("tabellaPersone").selectRows(1);
        window.button("eliminaButton").click();
        // Conferma eliminazione
        window.dialog().optionPane().yesButton().click();

        // 5. Modifica ultima persona (che ora è in riga 1): PAOLO NERI → MARCO ROSSI
        window.table("tabellaPersone").selectRows(1);
        window.button("modificaButton").click();
        FrameFixture editorMod = WindowFinder.findFrame("editorPersona").using(window.robot());
        editorMod.textBox("nomeField").setText(""); // pulisci
        editorMod.textBox("nomeField").enterText("Marco");
        editorMod.textBox("cognomeField").setText("");
        editorMod.textBox("cognomeField").enterText("Rossi");
        editorMod.textBox("indirizzoField").setText("");
        editorMod.textBox("indirizzoField").enterText("Via D");
        editorMod.textBox("telefonoField").setText("");
        editorMod.textBox("telefonoField").enterText("9999");
        editorMod.textBox("etaField").setText("");
        editorMod.textBox("etaField").enterText("45");
        editorMod.button("salvaButton").click();

        // 6. Verifica la tabella finale
        window.table("tabellaPersone").requireContents(new String[][] {
                {"Anna", "Bianchi", "1111"},
                {"Marco", "Rossi", "9999"}
        });

        // 7. Verifica dati aprendo ANNA
        window.table("tabellaPersone").selectRows(0);
        window.button("modificaButton").click();
        FrameFixture editAnna = WindowFinder.findFrame("editorPersona").using(window.robot());
        editAnna.textBox("nomeField").requireText("Anna");
        editAnna.textBox("cognomeField").requireText("Bianchi");
        editAnna.textBox("indirizzoField").requireText("Via A");
        editAnna.textBox("telefonoField").requireText("1111");
        editAnna.textBox("etaField").requireText("25");
        editAnna.button("annullaButton").click();

        // 8. Verifica dati aprendo MARCO
        window.table("tabellaPersone").selectRows(1);
        window.button("modificaButton").click();
        FrameFixture editMarco = WindowFinder.findFrame("editorPersona").using(window.robot());
        editMarco.textBox("nomeField").requireText("Marco");
        editMarco.textBox("cognomeField").requireText("Rossi");
        editMarco.textBox("indirizzoField").requireText("Via D");
        editMarco.textBox("telefonoField").requireText("9999");
        editMarco.textBox("etaField").requireText("45");
        editMarco.button("annullaButton").click();
    }*/
    @Test
    public void testPersistenzaDatiSuFileTraRiavvii() throws IOException {
        //File testFile = new File("test_persistenza.txt");
        setUpConFile(new File("test_persistenza.txt"));
        /*if (testFile.exists()) testFile.delete();
        testFile.createNewFile();*/

        //FinestraPrincipale frame = GuiActionRunner.execute(() -> new FinestraPrincipale(testFile));
        //window = new FrameFixture(frame);
        //window.show(); // mostra la finestra
        // --- PRIMO AVVIO: aggiungi Mario Rossi ---
        /*FinestraPrincipale frame1 = GuiActionRunner.execute(() -> new FinestraPrincipale(testFile));
        FrameFixture window1 = new FrameFixture(frame1);
        window1.show();*/

        window.button("nuovoButton").click();
        FrameFixture editor1 = WindowFinder.findFrame("editorPersona").using(window.robot());
        editor1.textBox("nomeField").enterText("Mario");
        editor1.textBox("cognomeField").enterText("Rossi");
        editor1.textBox("indirizzoField").enterText("Via Uno");
        editor1.textBox("telefonoField").enterText("1111");
        editor1.textBox("etaField").enterText("40");
        editor1.button("salvaButton").click();

        window.cleanUp(); // chiudi la finestra (salva su file)

        // --- SECONDO AVVIO: verifica e aggiungi altri due ---
        FinestraPrincipale frame2 = GuiActionRunner.execute(() -> new FinestraPrincipale(testFile));
        FrameFixture window2 = new FrameFixture(frame2);
        window2.show();

        // Verifica che Mario Rossi sia nella tabella
        window2.table("tabellaPersone").requireRowCount(1);
        window2.table("tabellaPersone").cell(row(0).column(0)).requireValue("Mario");

        // Apri in modifica e verifica dati
        window2.table("tabellaPersone").selectRows(0);
        window2.button("modificaButton").click();
        FrameFixture editorMario = WindowFinder.findFrame("editorPersona").using(window2.robot());
        editorMario.textBox("nomeField").requireText("Mario");
        editorMario.textBox("telefonoField").requireText("1111");
        editorMario.button("annullaButton").click();

        // Aggiungi ANNA BIANCHI
        window2.button("nuovoButton").click();
        FrameFixture editorAnna = WindowFinder.findFrame("editorPersona").using(window2.robot());
        editorAnna.textBox("nomeField").enterText("Anna");
        editorAnna.textBox("cognomeField").enterText("Bianchi");
        editorAnna.textBox("indirizzoField").enterText("Via Due");
        editorAnna.textBox("telefonoField").enterText("2222");
        editorAnna.textBox("etaField").enterText("35");
        editorAnna.button("salvaButton").click();

        // Aggiungi LUCA NERI
        window2.button("nuovoButton").click();
        FrameFixture editorLuca = WindowFinder.findFrame("editorPersona").using(window2.robot());
        editorLuca.textBox("nomeField").enterText("Luca");
        editorLuca.textBox("cognomeField").enterText("Neri");
        editorLuca.textBox("indirizzoField").enterText("Via Tre");
        editorLuca.textBox("telefonoField").enterText("3333");
        editorLuca.textBox("etaField").enterText("28");
        editorLuca.button("salvaButton").click();

        window2.cleanUp(); // chiudi seconda finestra

        // --- TERZO AVVIO: verifica che ci siano tutti ---
        FinestraPrincipale frame3 = GuiActionRunner.execute(() -> new FinestraPrincipale(testFile));
        FrameFixture window3 = new FrameFixture(frame3);
        window3.show();

        // Verifica che la tabella contenga 3 persone
        window3.table("tabellaPersone").requireContents(new String[][] {
                {"Mario", "Rossi", "1111"},
                {"Anna", "Bianchi", "2222"},
                {"Luca", "Neri", "3333"}
        });

        // Apri e verifica tutti
        for (int i = 0; i < 3; i++) {
            window3.table("tabellaPersone").selectRows(i);
            window3.button("modificaButton").click();
            FrameFixture edit = WindowFinder.findFrame("editorPersona").using(window3.robot());
            String[] expected = window3.table("tabellaPersone").contents()[i];
            edit.textBox("nomeField").requireText(expected[0]);
            edit.textBox("cognomeField").requireText(expected[1]);
            edit.textBox("telefonoField").requireText(expected[2]);
            edit.button("annullaButton").click();
        }

        window3.cleanUp();
        if (testFile.exists()) testFile.delete();
    }

}



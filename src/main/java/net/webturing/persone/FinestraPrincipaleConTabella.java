package net.webturing.persone;

import net.webturing.authentication.LoginFrame;

import javax.swing.*;
import java.io.File;

public class FinestraPrincipaleConTabella {
    public static void main(String[] args) {
        // Specifica il file che vuoi usare
        File filePersone = new File("informazioni.txt");

        // Crea la finestra passandole il file
        SwingUtilities.invokeLater(() -> {
            /*FinestraPrincipale finestra = new FinestraPrincipale(filePersone);
            finestra.setVisible(true);*/

            LoginFrame login = new LoginFrame(filePersone);
            login.setVisible(true);
        });
    }
}

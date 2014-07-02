package org.nathantehbeast.forbiddenclient;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import org.nathantehbeast.forbiddenclient.swing.Application;
import org.nathantehbeast.forbiddenclient.utils.Utilities;

import javax.swing.*;
import java.io.File;

public class Boot {

    private Application application;

    public Boot() {
        System.out.println(Utilities.getContentDirectory());
        final File iconImage = new File(Utilities.getContentDirectory() + "images/icon.png");
        if (!iconImage.exists()) {
            System.out.println("Downloading images...");
            Utilities.downloadFile("http://puu.sh/9TjJw.png", Utilities.getContentDirectory() + "images/icon.png");
        }

        SwingUtilities.invokeLater(() -> {
            application = new Application();
            application.setFocusable(true);
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        try {
            UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Boot();
    }

}
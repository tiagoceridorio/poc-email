package com.example.email;

import javax.swing.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class EmailTest {
    private static final Logger LOGGER = Logger.getLogger(EmailTest.class.getName());

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LOGGER.log(Level.SEVERE, "Erro não tratado na aplicação", e);
            JOptionPane.showMessageDialog(null,
                "Ocorreu um erro inesperado na aplicação:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        });

        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                EmailGUI gui = new EmailGUI();
                gui.setVisible(true);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Erro ao iniciar a aplicação", e);
                JOptionPane.showMessageDialog(null,
                    "Erro ao iniciar a aplicação:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}

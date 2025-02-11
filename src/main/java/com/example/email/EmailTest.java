package com.example.email;

public class EmailTest {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            EmailGUI gui = new EmailGUI();
            gui.setVisible(true);
        });
    }
}

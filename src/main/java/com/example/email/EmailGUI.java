package com.example.email;

import com.example.email.provider.*;
import javax.swing.*;
import java.awt.*;

public class EmailGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField toEmailField;
    private JComboBox<String> providerCombo;
    private JButton testButton;

    public EmailGUI() {
        setTitle("Teste de Email");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(400, 250);
        
        // Seleção de provedor
        add(new JLabel("Provedor:"));
        providerCombo = new JComboBox<>(new String[]{"Gmail", "SendGrid"});
        add(providerCombo);

        add(new JLabel("Email:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Senha/API Key:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Enviar para:"));
        toEmailField = new JTextField();
        add(toEmailField);

        add(new JLabel(""));
        testButton = new JButton("Enviar Email de Teste");
        testButton.addActionListener(e -> enviarEmail());
        add(testButton);

        setLocationRelativeTo(null);
    }

    private void enviarEmail() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String toEmail = toEmailField.getText();
        String provider = (String) providerCombo.getSelectedItem();

        try {
            EmailProvider emailProvider = provider.equals("Gmail") 
                ? new GmailProvider(username, password)
                : new SendGridProvider(password);

            emailProvider.sendEmail(
                username,
                toEmail,
                "Teste de E-mail",
                "Este é um e-mail de teste enviado através do " + provider + "!"
            );

            JOptionPane.showMessageDialog(this, 
                "E-mail enviado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao enviar e-mail: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

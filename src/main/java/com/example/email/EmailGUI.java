package com.example.email;

import javax.swing.*;
import java.awt.*;

public class EmailGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField toEmailField;
    private JButton testButton;

    public EmailGUI() {
        setTitle("Teste de Email");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(400, 200);
        
        // Campos do formulário
        add(new JLabel("Email (Gmail):"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Senha de Aplicativo:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Enviar para:"));
        toEmailField = new JTextField();
        add(toEmailField);

        // Espaço em branco para alinhamento
        add(new JLabel(""));
        
        // Botão de teste
        testButton = new JButton("Enviar Email de Teste");
        testButton.addActionListener(e -> enviarEmail());
        add(testButton);

        // Centralizar na tela
        setLocationRelativeTo(null);
    }

    private void enviarEmail() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String toEmail = toEmailField.getText();

        try {
            EmailSender emailSender = new EmailSender(username, password);
            emailSender.sendEmail(
                username,
                toEmail,
                "Teste de E-mail",
                "Este é um e-mail de teste enviado através do Java!"
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

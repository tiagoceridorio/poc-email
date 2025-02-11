package com.example.email;

import com.example.email.provider.*;
import com.example.email.exception.EmailException;
import com.example.email.auth.AuthType;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class EmailGUI extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(EmailGUI.class.getName());
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField toEmailField;
    private JComboBox<String> providerCombo;
    private JComboBox<AuthType> authTypeCombo;
    private JButton testButton;

    public EmailGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Não foi possível definir o look and feel nativo", e);
        }

        setTitle("Teste de Email");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Componentes existentes com melhor organização
        mainPanel.add(new JLabel("Provedor:"));
        providerCombo = new JComboBox<>(new String[]{"Gmail", "SendGrid"});
        providerCombo.addActionListener(e -> updateAuthTypeVisibility());
        mainPanel.add(providerCombo);

        mainPanel.add(new JLabel("Tipo de Autenticação:"));
        authTypeCombo = new JComboBox<>(AuthType.values());
        mainPanel.add(authTypeCombo);

        mainPanel.add(new JLabel("Email:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);

        mainPanel.add(new JLabel("Senha/API Key:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        mainPanel.add(new JLabel("Enviar para:"));
        toEmailField = new JTextField();
        mainPanel.add(toEmailField);

        mainPanel.add(new JLabel(""));
        testButton = new JButton("Enviar Email de Teste");
        testButton.addActionListener(e -> enviarEmail());
        mainPanel.add(testButton);

        add(mainPanel, BorderLayout.CENTER);
        
        setSize(450, 300);
        setLocationRelativeTo(null);

        updateAuthTypeVisibility();
    }

    private void updateAuthTypeVisibility() {
        boolean isGmail = "Gmail".equals(providerCombo.getSelectedItem());
        authTypeCombo.setVisible(isGmail);
    }

    private void enviarEmail() {
        try {
            testButton.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            validarCampos();
            
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String toEmail = toEmailField.getText().trim();
            String provider = (String) providerCombo.getSelectedItem();

            EmailProvider emailProvider = provider.equals("Gmail") 
                ? new GmailProvider(username, password, (AuthType) authTypeCombo.getSelectedItem())
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
                
        } catch (EmailException e) {
            LOGGER.log(Level.WARNING, "Erro de validação", e);
            JOptionPane.showMessageDialog(this, 
                e.getMessage(),
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao enviar e-mail", e);
            String errorMessage = "Erro ao enviar e-mail:\n";
            if (e.getCause() != null) {
                errorMessage += e.getCause().getMessage();
            } else {
                errorMessage += e.getMessage();
            }
            JOptionPane.showMessageDialog(this, 
                errorMessage,
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            testButton.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void validarCampos() throws EmailException {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String toEmail = toEmailField.getText().trim();

        if (username.isEmpty()) {
            throw new EmailException("O campo Email é obrigatório!");
        }

        if (!EMAIL_PATTERN.matcher(username).matches()) {
            throw new EmailException("Email inválido!");
        }

        if (password.isEmpty()) {
            throw new EmailException("O campo Senha/API Key é obrigatório!");
        }

        if (toEmail.isEmpty()) {
            throw new EmailException("O campo Enviar para é obrigatório!");
        }

        if (!EMAIL_PATTERN.matcher(toEmail).matches()) {
            throw new EmailException("Email de destino inválido!");
        }
    }
}

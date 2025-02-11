package com.example.email.provider;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class GmailProvider implements EmailProvider {
    private final String username;
    private final String password;
    private final Properties prop;

    public GmailProvider(String username, String password) {
        this.username = username;
        this.password = password;
        
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
    }

    @Override
    public void sendEmail(String from, String to, String subject, String content) throws MessagingException {
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }

    @Override
    public String getProviderName() {
        return "Gmail";
    }
}

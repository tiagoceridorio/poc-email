package com.example.email.provider;

import com.example.email.auth.AuthType;
import com.example.email.auth.OAuth2Authenticator;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.*;
import java.util.Properties;
import java.util.Arrays;

public class GmailProvider implements EmailProvider {
    private final String username;
    private final String password;
    private final Properties prop;
    private final AuthType authType;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final java.util.Collection<String> SCOPES = 
        Arrays.asList("https://mail.google.com/");

    public GmailProvider(String username, String password, AuthType authType) {
        this.username = username;
        this.password = password;
        this.authType = authType;
        
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
    }

    @Override
    public void sendEmail(String from, String to, String subject, String content) throws MessagingException {
        Session session = authType == AuthType.APP_PASSWORD ? 
            createPasswordSession() : createOAuth2Session();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }

    private Session createPasswordSession() {
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private Session createOAuth2Session() throws MessagingException {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            GoogleClientSecrets clientSecrets = loadClientSecrets();
            
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

            LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(8888)
                .build();

            Credential credential = new AuthorizationCodeInstalledApp(flow, receiver)
                .authorize("user");

            Properties props = new Properties();
            props.putAll(prop);
            props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
            props.put("mail.smtp.auth.xoauth2.disable", "false");

            return Session.getInstance(props, new OAuth2Authenticator(credential));
        } catch (Exception e) {
            throw new MessagingException("Erro na autenticação OAuth2", e);
        }
    }

    private GoogleClientSecrets loadClientSecrets() throws IOException {
        InputStream in = GmailProvider.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Arquivo de credenciais não encontrado: " + CREDENTIALS_FILE_PATH);
        }
        return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    }

    @Override
    public String getProviderName() {
        return "Gmail";
    }
}

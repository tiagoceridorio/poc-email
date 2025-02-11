package com.example.email.provider;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class SendGridProvider implements EmailProvider {
    private final String apiKey;

    public SendGridProvider(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void sendEmail(String from, String to, String subject, String content) throws Exception {
        Email fromEmail = new Email(from);
        Email toEmail = new Email(to);
        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(fromEmail, subject, toEmail, emailContent);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        
        Response response = sg.api(request);
        
        if (response.getStatusCode() >= 400) {
            throw new Exception("SendGrid erro: " + response.getBody());
        }
    }

    @Override
    public String getProviderName() {
        return "SendGrid";
    }
}

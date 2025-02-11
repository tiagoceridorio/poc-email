package com.example.email.provider;

public interface EmailProvider {
    void sendEmail(String from, String to, String subject, String content) throws Exception;
    String getProviderName();
}

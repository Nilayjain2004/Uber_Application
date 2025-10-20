package com.nilayjain.project.uber.uberApplication.services;

public interface EmailSenderService {
    void sendEmail(String toEmail, String subject, String body);

    void sendEmail(String toEmail[], String subject, String body);
}

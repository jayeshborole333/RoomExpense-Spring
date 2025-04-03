package com.example.RoomExpenses.service;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithAttachment(String[] recipients, File pdfFile) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(recipients);
        helper.setSubject("Room Expense Invoice");
        helper.setText("Please find attached the room expense invoice.");

        helper.addAttachment("Room_Expense_Invoice.pdf", pdfFile);

        javaMailSender.send(mimeMessage);
    }
}

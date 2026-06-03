package com.petnode.service.impl;

import com.petnode.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Override
    public void sendConfirmationEmail(String email, String appointmentDetails) {
        if (mailSender == null) {
            // Fallback if mail sender is not configured
            System.out.println("Confirmation email would be sent to: " + email);
            System.out.println("Details: " + appointmentDetails);
            return;
        }
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Appointment Confirmation - PetNode");
        message.setText("Your appointment has been confirmed.\n\n" + appointmentDetails);
        message.setFrom("noreply@petnode.com");
        
        mailSender.send(message);
    }
}

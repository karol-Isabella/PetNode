package com.petnode.notification.service;

public interface NotificationService {
    void sendConfirmationEmail(String email, String appointmentDetails);
}

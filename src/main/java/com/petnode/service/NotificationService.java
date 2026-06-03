package com.petnode.service;

public interface NotificationService {
    void sendConfirmationEmail(String email, String appointmentDetails);
}

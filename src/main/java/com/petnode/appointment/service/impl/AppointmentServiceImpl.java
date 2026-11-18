package com.petnode.appointment.service.impl;

import com.petnode.appointment.dto.AppointmentRequest;
import com.petnode.appointment.dto.AppointmentResponse;
import com.petnode.appointment.model.Appointment;
import com.petnode.appointment.model.AppointmentStatus;
import com.petnode.catalog.model.ServiceType;
import com.petnode.appointment.repository.AppointmentRepository;
import com.petnode.appointment.service.AppointmentService;
import com.petnode.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment(
                request.getPetId(), null, request.getDate(), request.getTime(), 
                ServiceType.GROOMING, request.getServiceIds());
        appointment.setDescription(request.getDescription());
        appointment.setObservations(request.getObservations());
        Appointment saved = appointmentRepository.save(appointment);
        return convertToResponse(saved);
    }

    @Override
    public List<AppointmentResponse> findAll() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse assignEmployee(String appointmentId, String employeeId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            appointment.setEmployeeId(employeeId);
            Appointment updated = appointmentRepository.save(appointment);
            return convertToResponse(updated);
        }
        return null;
    }

    @Override
    public AppointmentResponse confirmAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            appointment.setStatus(AppointmentStatus.CONFIRMED);
            Appointment updated = appointmentRepository.save(appointment);
            
            // Send confirmation email
            notificationService.sendConfirmationEmail(
                    "owner@email.com", 
                    "Your appointment has been confirmed for " + appointment.getDate());
            
            return convertToResponse(updated);
        }
        return null;
    }

    private AppointmentResponse convertToResponse(Appointment appointment) {
        return new AppointmentResponse(appointment.getId(), appointment.getDate(), 
                                       appointment.getTime(), appointment.getStatus().toString());
    }
}

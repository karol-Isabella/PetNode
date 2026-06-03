package com.petnode.service;

import com.petnode.dto.appointment.AppointmentRequest;
import com.petnode.dto.appointment.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request);
    List<AppointmentResponse> findAll();
    AppointmentResponse assignEmployee(String appointmentId, String employeeId);
    AppointmentResponse confirmAppointment(String appointmentId);
}

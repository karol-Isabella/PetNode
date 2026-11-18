package com.petnode.appointment.service;

import com.petnode.appointment.dto.AppointmentRequest;
import com.petnode.appointment.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request);
    List<AppointmentResponse> findAll();
    AppointmentResponse assignEmployee(String appointmentId, String employeeId);
    AppointmentResponse confirmAppointment(String appointmentId);
}

package com.petnode.controller;

import com.petnode.dto.appointment.AppointmentRequest;
import com.petnode.dto.appointment.AppointmentResponse;
import com.petnode.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        List<AppointmentResponse> response = appointmentService.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/assign-employee")
    public ResponseEntity<AppointmentResponse> assignEmployee(@PathVariable String id, @RequestParam String employeeId) {
        AppointmentResponse response = appointmentService.assignEmployee(id, employeeId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<AppointmentResponse> confirmAppointment(@PathVariable String id) {
        AppointmentResponse response = appointmentService.confirmAppointment(id);
        return ResponseEntity.ok(response);
    }
}

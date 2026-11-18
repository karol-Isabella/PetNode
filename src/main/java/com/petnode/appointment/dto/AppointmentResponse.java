package com.petnode.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponse {
    private String id;
    private LocalDate date;
    private LocalTime time;
    private String status;

    public AppointmentResponse() {}

    public AppointmentResponse(String id, LocalDate date, LocalTime time, String status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

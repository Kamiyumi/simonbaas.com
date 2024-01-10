package se.lu.ics.models;

import java.time.LocalDate;

public class MaintenanceSchedule {
    private Vehicle vehicle;
    private LocalDate scheduledMaintenanceDate;
    private String maintenanceDescription;
    private Workshop workshop;

    public MaintenanceSchedule(Vehicle scheduleForVehicle, LocalDate scheduledMaintenanceDate) {
        this.vehicle = scheduleForVehicle;
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
    }

    public MaintenanceSchedule() {
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicleForSchedule(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getScheduledMaintenanceDate() {
        return scheduledMaintenanceDate;
    }

    public void setScheduledMaintenanceDate(LocalDate scheduledMaintenanceDate) {
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
    }

    public String getMaintenanceDescription() {
        return maintenanceDescription;
    }

    public void setDate(LocalDate date) {
        this.scheduledMaintenanceDate = date;
    }

    public void setMaintenanceDescription(String maintenanceDescription) {
        this.maintenanceDescription = maintenanceDescription;
    }

    @Override
    public String toString() {
        return "MaintenanceSchedule" +
                "\nVehicle: " + (vehicle != null ? vehicle.getName() : "None") +
                "\nScheduled Date: " + scheduledMaintenanceDate +
                "\nResponsible Workshop: " + (workshop != null ? workshop.getName() : "None");
    }

    public boolean isEmpty() {
        return false;
    }
}

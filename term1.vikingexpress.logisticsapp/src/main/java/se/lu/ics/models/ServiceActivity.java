package se.lu.ics.models;

import java.time.LocalDate;

public class ServiceActivity {

    private Workshop workshop;
    private Vehicle serviceForVehicle;
    private ServiceHistory partOfServiceHistory;
    private LocalDate dateOfService;
    private String serviceDescription;
    private double serviceCost;
    private String serviceStatus;
    private String partsReplaced;

    public ServiceActivity(Workshop workshop, Vehicle serviceForVehicle, ServiceHistory partOfServiceHistory,
            LocalDate dateOfService, String serviceDescription, double serviceCost, String serviceStatus,
            String partsReplaced) {
        this.workshop = workshop;
        this.serviceForVehicle = serviceForVehicle;
        this.partOfServiceHistory = partOfServiceHistory;
        this.dateOfService = dateOfService;
        this.serviceDescription = serviceDescription;
        this.serviceCost = serviceCost;
        this.serviceStatus = serviceStatus;
        this.partsReplaced = partsReplaced;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public Vehicle getServiceForVehicle() {
        return serviceForVehicle;
    }

    public ServiceHistory getPartOfServiceHistory() {
        return partOfServiceHistory;
    }

    public LocalDate getDateOfService() {
        return dateOfService;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }
    
    public String getPartsReplaced() {
        return partsReplaced;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public void setServiceForVehicle(Vehicle serviceForVehicle) {
        this.serviceForVehicle = serviceForVehicle;
    }

    public void setPartOfServiceHistory(ServiceHistory partOfServiceHistory) {
        this.partOfServiceHistory = partOfServiceHistory;
    }

    public void setDateOfService(LocalDate dateOfService) {
        this.dateOfService = dateOfService;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public void setPartsReplaced(String partsReplaced) {
        this.partsReplaced = partsReplaced;
    }

    @Override
    public String toString() {
        return "\n" +
                "\nWorkshop: " + (workshop != null ? workshop.getName() : "None") +
                "\nVehicle: " + (serviceForVehicle != null ? serviceForVehicle.getName() : "None") +
                "\nDate of Service: " + dateOfService +
                "\nDescription: " + serviceDescription +
                "\nCost: " + String.format("%.2f", serviceCost) +
                "\nStatus: " + serviceStatus +
                "\nParts Replaced: " + partsReplaced;
    }

}

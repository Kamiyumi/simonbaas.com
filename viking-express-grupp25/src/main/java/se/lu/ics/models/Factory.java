package se.lu.ics.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Factory {

    public Vehicle createVehicle(int vin, String vehicleType, String name, String location,
            ArrayList<Cargo> cargo, double weightCapacity, int partsReplaced,
            ServiceHistory serviceHistory, MaintenanceSchedule vehicleMaintenanceSchedule,
            ArrayList<Workshop> visitedWorkshops) {
        return new Vehicle(vin, vehicleType, name, location, cargo, weightCapacity,
                partsReplaced, serviceHistory, vehicleMaintenanceSchedule, visitedWorkshops);
    }

    public Employee createEmployee(String name, String ssNo, String address, String phoneNumber, String employeeType) {
        return new Employee(name, ssNo, address, phoneNumber, employeeType);
    }

    public Cargo createCargo(String cargoName, double cargoWeight, int cargoAmount, int ean) {
        return new Cargo(cargoName, cargoWeight, cargoAmount, ean);
    }

    public MaintenanceSchedule createMaintenanceSchedule(Vehicle scheduleForVehicle, LocalDate scheduledMaintenanceDate,
            String maintenanceDescription) {
        return new MaintenanceSchedule(scheduleForVehicle, scheduledMaintenanceDate);
    }

    public ServiceActivity createServiceActivity(Workshop workshop, Vehicle serviceForVehicle,
            ServiceHistory partOfServiceHistory, LocalDate dateOfService, String serviceDescription, double serviceCost,
            String serviceStatus, String partsReplaced) {
        return new ServiceActivity(workshop, serviceForVehicle, partOfServiceHistory, dateOfService, serviceDescription,
                serviceCost, serviceStatus, partsReplaced);
    }

    public ServiceHistory createServiceHistory(ArrayList<ServiceActivity> serviceActivities,
            Vehicle serviceHistoryForVehicle) {
        return new ServiceHistory(serviceActivities, serviceHistoryForVehicle);
    }

    public Workshop createWorkshop(String name, String address, boolean isInternal) {
        return new Workshop(name, address, isInternal);
    }
    

}

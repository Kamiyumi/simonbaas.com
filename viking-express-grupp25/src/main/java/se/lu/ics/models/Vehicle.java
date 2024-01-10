package se.lu.ics.models;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Vehicle {
    private int vin;
    private String vehicleType;
    private String name;
    private String location;
    private ArrayList<Cargo> cargo = new ArrayList<Cargo>();
    private double totalServiceCost = 0;
    private double weightCapacity;
    private double cargoWeight;
    private int partsReplaced;
    private ArrayList<Employee> responsibleDrivers = new ArrayList<Employee>();
    private ServiceHistory serviceHistory;
    private MaintenanceSchedule vehicleMaintenanceSchedule;
    private ArrayList<Workshop> visitedWorkshops = new ArrayList<Workshop>();
    private ObservableList<Cargo> cargos = FXCollections.observableArrayList();

        
    public Vehicle() {
    }

    public Vehicle(int vin, String vehicleType, String name, String location, ArrayList<Cargo> cargo,
            double weightCapacity, int partsReplaced, ServiceHistory serviceHistory,
            MaintenanceSchedule vehicleMaintenanceSchedule, ArrayList<Workshop> visitedWorkshops) {
        this.vin = vin;
        this.vehicleType = vehicleType;
        this.name = name;
        this.location = location;
        this.cargo = cargo;
        this.weightCapacity = weightCapacity;
        this.partsReplaced = partsReplaced;
        this.serviceHistory = serviceHistory;
        this.vehicleMaintenanceSchedule = vehicleMaintenanceSchedule;
        this.visitedWorkshops = visitedWorkshops;
    }

    public ObservableList<Cargo> getCargos() {
        return cargos;
    }

    public double getTotalServiceCost() {
        return totalServiceCost;
    }

    public void setTotalServiceCost(double totalServiceCost) {
        this.totalServiceCost = totalServiceCost;
    }

    public void addTotalServiceCost(double totalServiceCost) {
        this.totalServiceCost += totalServiceCost;
    }

    public void addCargoWeight(double cargoWeight) {
        this.cargoWeight += cargoWeight;
    }

    public void removeCargoWeight(double cargoWeight) {
        this.cargoWeight -= cargoWeight;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public void assignDriver(Employee driver) {
        responsibleDrivers.add(driver);
    }

    public String displayServiceHistory() {
        return serviceHistory.toString();
    }

    public int numberOfPartsReplaced() {
        return partsReplaced;
    }

    public String displayVisitedWorkshops() {
        return visitedWorkshops.toString();
    }

    // Getters and setters
    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Cargo> getCargo() {
        return cargo;
    }

    public void setCargo(ArrayList<Cargo> cargo) {
        this.cargo = cargo;
    }

    public int getPartsReplaced() {
        return partsReplaced;
    }

    public void setPartsReplaced(int partsReplaced) {
        this.partsReplaced = partsReplaced;
    }

    public ArrayList<Employee> getResponsibleDrivers() {
        return responsibleDrivers;
    }

    public void setResponsibleDrivers(ArrayList<Employee> responsibleDrivers) {
        this.responsibleDrivers = responsibleDrivers;
    }

    public ServiceHistory getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public void setVehicleMaintenanceSchedule(MaintenanceSchedule vehicleMaintenanceSchedule) {
        this.vehicleMaintenanceSchedule = vehicleMaintenanceSchedule;
    }

    public ArrayList<Workshop> getVisitedWorkshops() {
        return visitedWorkshops;
    }

    public void setVisitedWorkshops(ArrayList<Workshop> visitedWorkshops) {
        this.visitedWorkshops = visitedWorkshops;
    }

    // add remove for all lists

    public void addResponsibleDriver(Employee driver) {
        this.responsibleDrivers.add(driver);
    }

    public void removeResponsibleDriver(Employee driver) {
        this.responsibleDrivers.remove(driver);
    }

    // add visited workshop

    public void addVisitedWorkshop(Workshop workshop) {
        this.visitedWorkshops.add(workshop);
    }

    public void removeVisitedWorkshop(Workshop workshop) {
        this.visitedWorkshops.remove(workshop);
    }

    // get weight capacity

    public double getWeightCapacity() {
        return weightCapacity;
    }

    // set weight capacity

    public void setWeightCapacity(double weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    public MaintenanceSchedule getMaintenanceSchedule() {
        return vehicleMaintenanceSchedule;
    }

    //recalculate total service cost

    //recalculate parts replaced


    public void recalculateTotalServiceCost() {
        double totalServiceCost = 0;
        for (ServiceActivity serviceActivity : this.serviceHistory.getServiceActivities()) {
            totalServiceCost += serviceActivity.getServiceCost();
        }
        this.totalServiceCost = totalServiceCost;
    }
    
    public void recalculateTotalCargoWeight() {
        double totalWeight = 0;
        for (Cargo cargo : this.cargos) { // Assuming 'cargos' is the list of cargo items
            double cargoTotalWeight = cargo.getCargoWeight() * cargo.getCargoAmount();
            totalWeight += cargoTotalWeight;
        }
        this.cargoWeight = totalWeight; // Assuming 'cargoWeight' is the field for total weight
    }

    public void addCargo(Cargo newCargo) {
        this.cargos.add(newCargo); // Add the cargo to the cargos list
        this.cargoWeight += newCargo.getCargoWeight() * newCargo.getCargoAmount();
    }

    public void removeCargo(Cargo oldCargo) {
        if (this.cargos.remove(oldCargo)) { // Remove the cargo from the cargos list
            this.cargoWeight -= oldCargo.getCargoWeight() * oldCargo.getCargoAmount();
        }
    }

    public String getCargoDetails() {
        if (cargos.isEmpty()) {
            return "None";
        } else {
            return cargos.stream()
                    .map(Cargo::toString)
                    .collect(Collectors.joining(", "));
        }
    }

    @Override
    public String toString() {
        return "Vehicle" +
                "\nVIN: " + vin +
                "\nType: " + vehicleType +
                "\nName: " + name +
                "\nLocation: " + location +
                "\nCargo: " + getCargoDetails() +
                "\nTotal Cargo Weight: " + String.format("%.2f", cargoWeight) +
                "\nWeight Capacity: " + weightCapacity +
                "\nParts Replaced: " + partsReplaced +
                "\nResponsible Drivers: " + getDriverNames() +
                "\nService History: " + getServiceHistoryDetails() +
                "\nMaintenance Schedule: " + getMaintenanceScheduleDetails() +
                "\nVisited Workshops: " + getVisitedWorkshopsDetails();
    }

    public String toStringTwo() {
        return "Added Vehicle:" +
                "\nVIN: " + this.vin +
                "\nType: " + this.vehicleType +
                "\nName: " + this.name +
                "\nLocation: " + this.location +
                "\nCargo: " + getCargoDetails() +
                "\nTotal Cargo Weight: " + String.format("%.2f", this.cargoWeight) +
                "\nWeight Capacity: " + this.weightCapacity +
                "\nParts Replaced: " + this.partsReplaced +
                "\nResponsible Drivers: " + getDriverNames();
    }

    public String getDriverNames() {
        if (responsibleDrivers.isEmpty()) {
            return "None";
        } else {
            return responsibleDrivers.stream()
                    .map(Employee::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    private String getServiceHistoryDetails() {
        if (serviceHistory == null || serviceHistory.isEmpty()) {
            return "None";
        } else {
            return serviceHistory.toString();
        }
    }

    private String getMaintenanceScheduleDetails() {
        if (vehicleMaintenanceSchedule == null || vehicleMaintenanceSchedule.isEmpty()) {
            return "None";
        } else {
            return vehicleMaintenanceSchedule.toString();
        }
    }

    private String getVisitedWorkshopsDetails() {
        if (visitedWorkshops.isEmpty()) {
            return "None";
        } else {
            return visitedWorkshops.stream()
                    .map(Workshop::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    // calculate cargoWeight and set it

    public void calculateCargoWeight() {
    double totalWeight = 0;
    for (Cargo cargo : this.cargo) {
        totalWeight += cargo.getCargoWeight() * cargo.getCargoAmount();
    }
    this.cargoWeight = totalWeight;
}


}
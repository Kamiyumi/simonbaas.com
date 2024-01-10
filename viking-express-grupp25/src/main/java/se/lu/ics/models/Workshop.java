package se.lu.ics.models;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Workshop {
    private String name;
    private String address;
    private boolean isInternal;
    private double totalCost = 0;
    private double averageCost = 0;
    private ArrayList<Vehicle> vehiclesServiced = new ArrayList<Vehicle>();
    private ArrayList<ServiceActivity> serviceActivitiesHandeled = new ArrayList<ServiceActivity>();

    public Workshop(String name, String address, boolean isInternal) {
        this.name = name;
        this.address = address;
        this.isInternal = isInternal;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

    public ArrayList<Vehicle> getVehiclesServiced() {
        return vehiclesServiced;
    }

    public void setVehiclesServiced(ArrayList<Vehicle> vehiclesServiced) {
        this.vehiclesServiced = vehiclesServiced;
    }

    public ArrayList<ServiceActivity> getServiceActivitiesHandeled() {
        return serviceActivitiesHandeled;
    }

    public void setServiceActivitiesHandeled(ArrayList<ServiceActivity> serviceActivitiesHandeled) {
        this.serviceActivitiesHandeled = serviceActivitiesHandeled;
    }

    public void addVehicle(Vehicle vehicle) {
        vehiclesServiced.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehiclesServiced.remove(vehicle);
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost += totalCost;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost += totalCost / serviceActivitiesHandeled.size();
    }

    public void calculateCosts() {
        totalCost = 0;
        for (ServiceActivity activity : serviceActivitiesHandeled) {
            totalCost += activity.getServiceCost();
        }
        averageCost = serviceActivitiesHandeled.isEmpty() ? 0 : totalCost / serviceActivitiesHandeled.size();
    }

    public void addServiceActivity(ServiceActivity serviceActivity) {
        serviceActivitiesHandeled.add(serviceActivity);
        calculateCosts();
    }

    public void removeServiceActivity(ServiceActivity serviceActivity) {
        serviceActivitiesHandeled.remove(serviceActivity);
        calculateCosts();
    }

    @Override
    public String toString() {
        return "Workshop" +
                "\nName: " + name +
                "\nAddress: " + address +
                "\nIs Internal: " + isInternal +
                "\nServiced Vehicles: " + (vehiclesServiced.isEmpty() ? "None"
                        : vehiclesServiced.stream().map(Vehicle::getName).collect(Collectors.joining(", ")));
    }

}

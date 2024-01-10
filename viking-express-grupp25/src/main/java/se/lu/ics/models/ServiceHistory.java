package se.lu.ics.models;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ServiceHistory {
    // list of service activities
    private ArrayList<ServiceActivity> serviceActivities = new ArrayList<ServiceActivity>();
    private Vehicle serviceHistoryForVehicle;

    public ServiceHistory(ArrayList<ServiceActivity> serviceActivities, Vehicle serviceHistoryForVehicle) {
        this.serviceActivities = serviceActivities;
        this.serviceHistoryForVehicle = serviceHistoryForVehicle;

    }

    public ServiceHistory() {
    }

    //create method to calculate total service cost

    public double calculateTotalServiceCost() {
        double totalServiceCost = 0;
        for (ServiceActivity serviceActivity : serviceActivities) {
            totalServiceCost += serviceActivity.getServiceCost();
        }
        return totalServiceCost;
    }
    
    public ArrayList<ServiceActivity> getServiceActivities() {
        return serviceActivities;
    }

    public Vehicle getServiceHistoryForVehicle() {
        return serviceHistoryForVehicle;
    }

    public void setServiceActivities(ArrayList<ServiceActivity> serviceActivities) {
        this.serviceActivities = serviceActivities;
    }

    public void setServiceHistoryForVehicle(Vehicle serviceHistoryForVehicle) {
        this.serviceHistoryForVehicle = serviceHistoryForVehicle;
    }

    // add service activity to service history

    public void addServiceActivity(ServiceActivity serviceActivity) {
        serviceActivities.add(serviceActivity);
    }

    // remove service activity from service history

    public void removeServiceActivity(ServiceActivity serviceActivity) {
        serviceActivities.remove(serviceActivity);

    }

    @Override
    public String toString() {
        String activitiesString = serviceActivities.isEmpty() ? "No service activities recorded."
                : serviceActivities.stream()
                        .map(ServiceActivity::toString)
                        .collect(Collectors.joining("\n\n")); // Adding an extra newline for better readability

        return "Service History:" +
                "\n====================" +
                "\nService Activities:\n" + activitiesString +
                "\n====================" +
                "\nVehicle: "
                + (serviceHistoryForVehicle != null ? serviceHistoryForVehicle.getName() : "No vehicle associated");
    }

    public boolean isEmpty() {
        return false;
    }

}

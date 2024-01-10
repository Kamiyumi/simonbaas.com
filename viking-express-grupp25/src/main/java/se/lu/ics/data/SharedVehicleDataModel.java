package se.lu.ics.data;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.lu.ics.models.Cargo;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;
import se.lu.ics.models.Employee;
import se.lu.ics.models.ServiceActivity;

public class SharedVehicleDataModel {
    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    private ObservableList<Cargo> vehiclesCargo = FXCollections.observableArrayList();
    private ObservableList<MaintenanceSchedule> maintenanceSchedules = FXCollections.observableArrayList();
    private ObservableList<Workshop> workshops = FXCollections.observableArrayList();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObservableList<ServiceActivity> serviceActivities = FXCollections.observableArrayList();

    public ObservableList<ServiceActivity> getServiceActivities() {
        return serviceActivities;
    }

    public void addServiceActivity(ServiceActivity serviceActivity) {
        if (serviceActivity != null) {
            serviceActivities.add(serviceActivity);
        }
    }

    // remove service activity

    public void removeServiceActivity(ServiceActivity serviceActivity) {
        if (serviceActivity != null) {
            serviceActivities.remove(serviceActivity);
        }
    }

    // add all service activities

    public void addAllServiceActivities(List<ServiceActivity> serviceActivityList) {
        if (serviceActivityList != null) {
            serviceActivities.addAll(serviceActivityList);
        }
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }
    // add all employees to list

    public void addAllEmployees(List<Employee> employeeList) {
        if (employeeList != null) {
            employees.addAll(employeeList);
        }
    }

    public ObservableList<Vehicle> getVehicles() {
        return vehicles;
    }

    public ObservableList<Cargo> getAllCargo() {
        return vehiclesCargo; // Return the list of cargo directly
    }

    public ObservableList<MaintenanceSchedule> getMaintenanceSchedules() {
        return maintenanceSchedules;
    }

    // get names from workshop list

    public void addCargoToVehicle(Cargo cargo, Vehicle vehicle) {
        if (cargo != null && vehicle != null) {
            // Set the vehicle to the cargo
            cargo.setVehicle(vehicle);

            // Add cargo to the specific vehicle's cargo list
            vehicle.addCargo(cargo);

            // Add to the general list of all cargos
            vehiclesCargo.add(cargo);
        }
    }

    public ObservableList<Workshop> getWorkshops() {
        return workshops;
    }

    public void addAllCargoFromVehicles() {
        // Clear the existing cargo list
        vehiclesCargo.clear();

        // Iterate through the vehicles and add their cargo to the cargo list
        for (Vehicle vehicle : vehicles) {
            vehiclesCargo.addAll(vehicle.getCargos());
        }
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }

    public void addAllVehicles(List<Vehicle> vehicleList) {
        if (vehicleList != null) {
            vehicles.addAll(vehicleList);
        }
    }

    public void addMaintenanceSchedule(MaintenanceSchedule schedule) {
        if (schedule != null) {
            maintenanceSchedules.add(schedule);
        }
    }

    public void removeMaintenanceSchedule(MaintenanceSchedule schedule) {
        if (schedule != null) {
            maintenanceSchedules.remove(schedule);
        }
    }

    // add all workshops

    public void addWorkshop(Workshop workshop) {
        if (workshop != null) {
            workshops.add(workshop);
        }

    }

    public void addAllWorkshops(List<Workshop> workshopList) {
        if (workshopList != null) {
            ObservableList<Workshop> newWorkshops = FXCollections.observableArrayList(workshopList);
            workshops.addAll(newWorkshops);
        }
    }

    public void removeWorkshop(Workshop workshop) {
        if (workshop != null) {
            workshops.remove(workshop);
        }
    }

    // return the most expensive service activity and the workshop that handled it
    // and the vehicle that was serviced

    public String getMostExpensiveServiceActivity() {
        List<ServiceActivity> mostExpensiveServiceActivities = new ArrayList<>();
        double highestCost = 0;

        // Find the highest cost
        for (ServiceActivity serviceActivity : serviceActivities) {
            if (serviceActivity.getServiceCost() > highestCost) {
                highestCost = serviceActivity.getServiceCost();
            }
        }

        // Collect all activities with the highest cost
        for (ServiceActivity serviceActivity : serviceActivities) {
            if (serviceActivity.getServiceCost() == highestCost) {
                mostExpensiveServiceActivities.add(serviceActivity);
            }
        }

        if (!mostExpensiveServiceActivities.isEmpty()) {
            StringBuilder result = new StringBuilder("Most Expensive Service Activities:\n");
            for (ServiceActivity serviceActivity : mostExpensiveServiceActivities) {
                result.append(String.format(
                        "Description: %s\nCost: %.2f\nHandled By: %s\nDate: %s\nPerformed On: %s\n\n",
                        serviceActivity.getServiceDescription(),
                        serviceActivity.getServiceCost(),
                        serviceActivity.getWorkshop().getName(),
                        serviceActivity.getDateOfService(),
                        serviceActivity.getServiceForVehicle().getName()));
            }
            return result.toString().trim(); // Trim to remove the last newline character
        } else {
            return "No service activities found.";
        }
    }

    // calculte the average cost of all service activities

    public double getAverageServiceCost() {
        if (serviceActivities.isEmpty()) {
            return 0.0; // or handle this case as you see fit
        }

        double totalServiceCost = 0;
        for (ServiceActivity serviceActivity : serviceActivities) {
            totalServiceCost += serviceActivity.getServiceCost();
        }
        return totalServiceCost / serviceActivities.size();
    }

    // display most expensive workshop

    public String getMostExpensiveWorkshopDetails() {
        Workshop mostExpensiveWorkshop = null;
        double highestCost = 0;
        double highestAverageCost = 0;

        for (Workshop workshop : workshops) {
            double workshopTotalCost = 0;
            int count = 0;

            for (ServiceActivity activity : serviceActivities) {
                if (activity.getWorkshop().equals(workshop)) {
                    workshopTotalCost += activity.getServiceCost();
                    count++;
                }
            }

            double averageCost = (count > 0) ? workshopTotalCost / count : 0;

            if (workshopTotalCost > highestCost) {
                highestCost = workshopTotalCost;
                highestAverageCost = averageCost;
                mostExpensiveWorkshop = workshop;
            }
        }

        if (mostExpensiveWorkshop != null) {
            return String.format("Most Expensive Workshop: %s\nTotal Cost: %.2f\nAverage Service Cost: %.2f",
                    mostExpensiveWorkshop.getName(),
                    highestCost,
                    highestAverageCost);
        } else {
            return "No workshops found.";
        }
    }

    public void updateCargoVehicle(Cargo cargo, Vehicle newVehicle) {
        if (cargo != null && newVehicle != null) {
            Vehicle oldVehicle = cargo.getVehicle();
            if (oldVehicle != null) {
                oldVehicle.removeCargo(cargo); // Remove cargo from the old vehicle
                oldVehicle.recalculateTotalCargoWeight(); // Recalculate total weight for old vehicle
            }
            newVehicle.addCargo(cargo); // Add cargo to the new vehicle
            cargo.setVehicle(newVehicle); // Update the cargo's vehicle reference
            newVehicle.recalculateTotalCargoWeight(); // Recalculate total weight for new vehicle
        }
    }

    // calculate the sum of all service activities

    public double getTotalServiceCost() {
        double totalServiceCost = 0;
        for (ServiceActivity serviceActivity : serviceActivities) {
            totalServiceCost += serviceActivity.getServiceCost();
        }
        return totalServiceCost;
    }
}

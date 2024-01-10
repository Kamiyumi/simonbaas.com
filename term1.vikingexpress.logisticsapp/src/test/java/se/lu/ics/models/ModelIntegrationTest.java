/* package se.lu.ics.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class ModelIntegrationTest {

    public static void main(String[] args) {
        Factory factory = new Factory();

        // Create instances of vehicles, employees, workshops, and cargo with more lifelike names
        Vehicle truckRed = factory.createVehicle(1, "Truck", "Red Baron", "Warehouse A", new ArrayList<>(), 5000.0, 0, null, null, new ArrayList<>());
        Vehicle vanBlue = factory.createVehicle(2, "Van", "Blue Whale", "Warehouse B", new ArrayList<>(), 3000.0, 0, null, null, new ArrayList<>());

        // Set up service history and maintenance schedules for the vehicles
        ServiceHistory truckServiceHistory = factory.createServiceHistory(new ArrayList<>(), truckRed);
        ServiceHistory vanServiceHistory = factory.createServiceHistory(new ArrayList<>(), vanBlue);
        MaintenanceSchedule truckMaintenance = factory.createMaintenanceSchedule(truckRed, LocalDate.now().plusDays(30), "Scheduled Engine Checkup");
        MaintenanceSchedule vanMaintenance = factory.createMaintenanceSchedule(vanBlue, LocalDate.now().plusDays(45), "Scheduled Brake System Review");

        // Assign the service history and maintenance schedules to vehicles
        truckRed.setServiceHistory(truckServiceHistory);
        vanBlue.setServiceHistory(vanServiceHistory);
        truckRed.setVehicleMaintenanceSchedule(truckMaintenance);
        vanBlue.setVehicleMaintenanceSchedule(vanMaintenance);

        Employee driverAlice = factory.createEmployee("Alice Johnson", 1001, "123 Elm Street", "123-456-7890", "Driver");
        Employee techBob = factory.createEmployee("Bob Smith", 1002, "456 Oak Avenue", "123-456-7891", "Technician");

        // Assign drivers to vehicles
        driverAlice.setDrives(truckRed);
        techBob.setDrives(vanBlue);
        truckRed.addResponsibleDriver(driverAlice);
        vanBlue.addResponsibleDriver(techBob);

        Workshop serviceCenterA = factory.createWorkshop("Service Center A", "200 Main Street", true);
        Workshop serviceCenterB = factory.createWorkshop("Service Center B", "201 Main Street", false);

        // Add vehicles to workshops
        serviceCenterA.addVehicle(truckRed);
        serviceCenterB.addVehicle(vanBlue);

        Cargo steelBeams = factory.createCargo("Steel Beams", 100.0, 10, 1111);
        Cargo lumberPlanks = factory.createCargo("Lumber Planks", 50.0, 20, 2222);
        Cargo bricks = factory.createCargo("Bricks", 25.0, 40, 3333);
        Cargo nails = factory.createCargo("Nails", 1.0, 100, 4444);

        // Load cargo onto vehicles
        truckRed.addCargo(steelBeams);
        truckRed.addCargo(bricks);
        vanBlue.addCargo(lumberPlanks);
        vanBlue.addCargo(nails);

        // Create service activities with more descriptive names
        ServiceActivity oilChange = factory.createServiceActivity(serviceCenterA, truckRed, truckServiceHistory, LocalDate.now(), "Complete Oil Change", 250.00, "Completed", "Oil Filter");
        ServiceActivity tireReplacement = factory.createServiceActivity(serviceCenterB, vanBlue, vanServiceHistory, LocalDate.now(), "Full Tire Replacement", 500.00, "Completed", "All Four Tires");

        // Add service activities to service histories
        truckServiceHistory.addServiceActivity(oilChange);
        vanServiceHistory.addServiceActivity(tireReplacement);

        // Vehicle register to track all vehicles
        VehicleRegister registry = new VehicleRegister();
        registry.addVehicle(truckRed);
        registry.addVehicle(vanBlue);

        // Printing all details
        printVehicleDetails(truckRed);
        printVehicleDetails(vanBlue);
        printSection(driverAlice);
        printSection(techBob);
        printSection(serviceCenterA);
        printSection(serviceCenterB);
        printSection(steelBeams);
        printSection(lumberPlanks);
        printSection(registry);
    }

    private static void printVehicleDetails(Vehicle vehicle) {
    // Print vehicle information
    System.out.println("========================================");
    System.out.println("Vehicle Information");
    System.out.println("========================================");
    System.out.println("VIN: " + vehicle.getVin());
    System.out.println("Type: " + vehicle.getVehicleType());
    System.out.println("Name: " + vehicle.getName());
    System.out.println("Location: " + vehicle.getLocation());
    System.out.print("Cargo: ");
    printCargos(vehicle.getCargo());
    System.out.println("Weight Capacity: " + vehicle.getWeightCapacity());
    System.out.println("Cargo Weight: " + vehicle.getCargoWeight()); // Add the cargoWeight attribute here
    System.out.println("Parts Replaced: " + vehicle.getPartsReplaced());
    System.out.println("Responsible Drivers: " + getDriverNames(vehicle.getResponsibleDrivers()));
    System.out.println("Maintenance Schedule: "
            + (vehicle.getVehicleMaintenanceSchedule() != null ? vehicle.getVehicleMaintenanceSchedule() : "None"));
    System.out.println("Visited Workshops: " + getVisitedWorkshops(vehicle.getVisitedWorkshops()));
    System.out.println("\n");

    // Print service history details
    printServiceHistory(vehicle.getServiceHistory());
}

private static void printCargos(ArrayList<Cargo> cargos) {
    if (cargos.isEmpty()) {
        System.out.println("None");
    } else {
        for (Cargo cargo : cargos) {
            System.out.println(cargo.getCargoName() + " - Weight: " + cargo.getCargoWeight() + " Amount: "
                    + cargo.getCargoAmount() + " Total Weight: " + cargo.getCargoTotalWeight());
        }
    }
}

private static String getDriverNames(ArrayList<Employee> drivers) {
    if (drivers.isEmpty()) {
        return "None";
    }
    return String.join(", ", drivers.stream().map(Employee::getName).toArray(String[]::new));
}

private static String getVisitedWorkshops(ArrayList<Workshop> workshops) {
    if (workshops.isEmpty()) {
        return "None";
    }
    return String.join(", ", workshops.stream().map(Workshop::getName).toArray(String[]::new));
}

private static void printServiceHistory(ServiceHistory serviceHistory) {
    System.out.println("========================================");
    System.out.println("Service History Details");
    System.out.println("========================================");
    if (serviceHistory != null && !serviceHistory.getServiceActivities().isEmpty()) {
        serviceHistory.getServiceActivities().forEach(activity -> {
            System.out.println(activity.toString());
            System.out.println("---");
        });
    } else {
        System.out.println("No service activities recorded.");
    }
    System.out.println("\n");
}

private static void printSection(Object object) {
    String className = object.getClass().getSimpleName();
    System.out.println(className + " Details");
    System.out.println("========================================");
    System.out.println(object.toString());
    System.out.println("\n");
}
} */
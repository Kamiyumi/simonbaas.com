package se.lu.ics.models.populator;

import se.lu.ics.data.SharedVehicleDataModel;
import se.lu.ics.models.Cargo;
import se.lu.ics.models.Employee;
import se.lu.ics.models.Factory;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.ServiceHistory;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;
import se.lu.ics.models.ServiceActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDataPopulatorSql {

        public static void populateTestDataSql(SharedVehicleDataModel sharedDataModel, Factory factory) {

                String dbURL = "jdbc:sqlserver://sqldatabasesimon.database.windows.net:1433;databaseName=SimonDB;user=kamiyumi;password=Doggeezusu1236!!";

              
                List<String> shuffledCarNames = new ArrayList<String>();
                List<String> shuffledAddresses = new ArrayList<String>();
                List<String> shuffledEmployeeNames = new ArrayList<String>();
                List<String> shuffledLocations = new ArrayList<String>();
                List<String> shuffledPhoneNumbers = new ArrayList<String>();
                List<String> shuffledRepairShopNames = new ArrayList<String>();
                List<String> shuffledCargoNames = new ArrayList<String>();
                List<String> shuffledFunnyTitles = new ArrayList<String>();


                // Establish a database connection and retrieve car names
                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT CAR_NAMES FROM dbo.VIKING_EXPRESS_CAR_NAMES");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledCarNames.add(rs.getString("CAR_NAMES"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }
                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT FICTIONAL_ADDRESSES FROM dbo.VIKING_EXPRESS_ADDRESSES");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledAddresses.add(rs.getString("FICTIONAL_ADDRESSES"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }

                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT EMPLOYEE_NAMES FROM dbo.VIKING_EXPRESS_EMPLOYEE_NAMES");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledEmployeeNames.add(rs.getString("EMPLOYEE_NAMES"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }

                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT LOCATIONS FROM dbo.VIKING_EXPRESS_LOCATIONS");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledLocations.add(rs.getString("LOCATIONS"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }

                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT PHONE_NUMBER FROM dbo.VIKING_EXPRESS_SWEDISH_PHONE_NUMBERS");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledPhoneNumbers.add(rs.getString("PHONE_NUMBER"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }
             

                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT REPAIR_SHOP_NAMES FROM dbo.VIKING_EXPRESS_REPAIR_SHOP_NAMES");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledRepairShopNames.add(rs.getString("REPAIR_SHOP_NAMES"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }

            
                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT CARGO_NAMES FROM dbo.VIKING_EXPRESS_CARGO_NAMES");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledCargoNames.add(rs.getString("CARGO_NAMES"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }

                try (Connection conn = DriverManager.getConnection(dbURL);
                                PreparedStatement stmt = conn
                                                .prepareStatement("SELECT FUNNY_TITLES FROM dbo.VIKING_EXPRESS_FUNNY_TITLES");
                                ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                shuffledFunnyTitles.add(rs.getString("FUNNY_TITLES"));
                        }
                } catch (SQLException e) {
                        e.printStackTrace(); // Handle exception
                }

                Random random = new Random();

               
                Collections.shuffle(shuffledFunnyTitles);
                Collections.shuffle(shuffledCargoNames);
                Collections.shuffle(shuffledRepairShopNames);
                Collections.shuffle(shuffledAddresses);
                Collections.shuffle(shuffledPhoneNumbers);
                Collections.shuffle(shuffledLocations);
                Collections.shuffle(shuffledEmployeeNames);
                Collections.shuffle(shuffledCarNames); 

                Cargo electronicsCargo = factory.createCargo(shuffledCargoNames.get(0), 100.0, 5, getRandomEAN());
                Cargo furnitureCargo = factory.createCargo(shuffledCargoNames.get(1), 150.0, 3, getRandomEAN());
                Cargo foodCargo = factory.createCargo(shuffledCargoNames.get(2), 300.0, 10, getRandomEAN());
                Cargo clothingCargo = factory.createCargo(shuffledCargoNames.get(3), 200.0, 8, getRandomEAN());
                Cargo techCargo = factory.createCargo(shuffledCargoNames.get(4), 500.0, 5, getRandomEAN());
                Cargo machineryCargo = factory.createCargo(shuffledCargoNames.get(5), 800.0, 2, getRandomEAN());
                Cargo pharmaceuticalCargo = factory.createCargo(shuffledCargoNames.get(6), 400.0, 15, getRandomEAN());
                Cargo booksCargo = factory.createCargo(shuffledCargoNames.get(7), 350.0, 20, getRandomEAN());

                // Create sample employees (drivers) with shuffled streets as addresses
                Employee driverJohn = factory.createEmployee(shuffledEmployeeNames.get(0), "101",
                                shuffledAddresses.get(0), shuffledPhoneNumbers.get(0), shuffledFunnyTitles.get(0));

                Employee driverJane = factory.createEmployee(shuffledEmployeeNames.get(1), "102",
                                shuffledAddresses.get(1), shuffledPhoneNumbers.get(1), shuffledFunnyTitles.get(1));

                Employee driverAlice = factory.createEmployee(shuffledEmployeeNames.get(2), "103",
                                shuffledAddresses.get(2), shuffledPhoneNumbers.get(2), shuffledFunnyTitles.get(2));

                Employee driverBob = factory.createEmployee(shuffledEmployeeNames.get(3), "104",
                                shuffledAddresses.get(3), shuffledPhoneNumbers.get(3), shuffledFunnyTitles.get(3));

                Employee driverEmma = factory.createEmployee(shuffledEmployeeNames.get(4), "105",
                                shuffledAddresses.get(4), shuffledPhoneNumbers.get(4), shuffledFunnyTitles.get(4));

                Employee driverLiam = factory.createEmployee(shuffledEmployeeNames.get(5), "106",
                                shuffledAddresses.get(5), shuffledPhoneNumbers.get(5), shuffledFunnyTitles.get(5));

                Workshop autoTechWorkshop = factory.createWorkshop(shuffledRepairShopNames.get(0),
                                shuffledAddresses.get(0), true);
                Workshop cityGarage = factory.createWorkshop(shuffledRepairShopNames.get(1), shuffledAddresses.get(1),
                                false);
                Workshop speedyRepairs = factory.createWorkshop(shuffledRepairShopNames.get(2), shuffledAddresses.get(2),
                                false);
                Workshop downtownGarage = factory.createWorkshop(shuffledRepairShopNames.get(3), shuffledAddresses.get(3),
                                false);
                Workshop centralWorkshop = factory.createWorkshop(shuffledRepairShopNames.get(4),
                                shuffledAddresses.get(4), false);
                Workshop eastSideGarage = factory.createWorkshop(shuffledRepairShopNames.get(5), shuffledAddresses.get(5),
                                false);

                sharedDataModel.addAllWorkshops(Arrays.asList(autoTechWorkshop, cityGarage, speedyRepairs,
                                downtownGarage, centralWorkshop, eastSideGarage));

                // Create sample service histories
                ServiceHistory history1 = new ServiceHistory(new ArrayList<>(), null);
                ServiceHistory history2 = new ServiceHistory(new ArrayList<>(), null);
                ServiceHistory history3 = new ServiceHistory(new ArrayList<>(), null);
                ServiceHistory history4 = new ServiceHistory(new ArrayList<>(), null);
                ServiceHistory history5 = new ServiceHistory(new ArrayList<>(), null);
                ServiceHistory history6 = new ServiceHistory(new ArrayList<>(), null);

                // Create sample maintenance schedules
                MaintenanceSchedule routineMaintenance = factory.createMaintenanceSchedule(null, LocalDate.now(),
                                "Routine Maintenance");
                MaintenanceSchedule majorInspection = factory.createMaintenanceSchedule(null,
                                LocalDate.now().plusDays(30),
                                "Major Inspection");
                MaintenanceSchedule safetyCheck = factory.createMaintenanceSchedule(null, LocalDate.now().plusDays(60),
                                "Safety Check");
                MaintenanceSchedule regularCheckup = factory.createMaintenanceSchedule(null,
                                LocalDate.now().plusDays(90),
                                "Regular Checkup");
                MaintenanceSchedule annualInspection = factory.createMaintenanceSchedule(null,
                                LocalDate.now().plusDays(120),
                                "Annual Inspection");
                MaintenanceSchedule serviceUpdate = factory.createMaintenanceSchedule(null,
                                LocalDate.now().plusDays(150),
                                "Service Update");

                // Create sample vehicles
                Vehicle truckCity1 = factory.createVehicle(
                                random.nextInt(100000), "Small Truck", shuffledCarNames.get(0),
                                shuffledLocations.get(0),
                                new ArrayList<>(Arrays.asList(electronicsCargo)), 2000.0, 10, history1,
                                routineMaintenance,
                                new ArrayList<>(Arrays.asList(autoTechWorkshop)));

                Vehicle vanCity2 = factory.createVehicle(
                                random.nextInt(100000), "Van", shuffledCarNames.get(1), shuffledLocations.get(1),
                                new ArrayList<>(Arrays.asList(furnitureCargo)), 1500.0, 5, history2, majorInspection,
                                new ArrayList<>(Arrays.asList(cityGarage)));

                Vehicle busCity3 = factory.createVehicle(
                                random.nextInt(100000), "Large Truck", shuffledCarNames.get(2),
                                shuffledLocations.get(2),
                                new ArrayList<>(Arrays.asList(foodCargo)), 3000.0, 20, history3, safetyCheck,
                                new ArrayList<>(Arrays.asList(speedyRepairs)));

                Vehicle carCity4 = factory.createVehicle(
                                random.nextInt(100000), "Small Truck", shuffledCarNames.get(3),
                                shuffledLocations.get(3),
                                new ArrayList<>(Arrays.asList(clothingCargo, techCargo)), 1000.0, 4, history4,
                                regularCheckup,
                                new ArrayList<>(Arrays.asList(downtownGarage)));

                Vehicle lorryCity5 = factory.createVehicle(
                                random.nextInt(100000), "Large Truck", shuffledCarNames.get(4),
                                shuffledLocations.get(4),
                                new ArrayList<>(Arrays.asList(machineryCargo)), 5000.0, 15, history5, annualInspection,
                                new ArrayList<>(Arrays.asList(centralWorkshop)));

                Vehicle pickupCity6 = factory.createVehicle(
                                random.nextInt(100000), "Small Truck", shuffledCarNames.get(5),
                                shuffledLocations.get(5),
                                new ArrayList<>(Arrays.asList(pharmaceuticalCargo, booksCargo)), 1200.0, 3, history6,
                                serviceUpdate,
                                new ArrayList<>(Arrays.asList(eastSideGarage)));

                ServiceActivity serviceActivity1 = factory.createServiceActivity(autoTechWorkshop, truckCity1, history1,
                                LocalDate.now(), "Oil change and tire rotation", 200.0, "Completed",
                                "Oil filter, Tires");
                ServiceActivity serviceActivity2 = factory.createServiceActivity(cityGarage, vanCity2, history2,
                                LocalDate.now().plusDays(1), "Brake pad replacement", 150.0, "Completed", "Brake pads");
                ServiceActivity serviceActivity3 = factory.createServiceActivity(speedyRepairs, busCity3, history3,
                                LocalDate.now().plusDays(2), "Engine diagnostics and repair", 500.0, "Completed",
                                "Spark plugs, Engine oil");
                ServiceActivity serviceActivity4 = factory.createServiceActivity(downtownGarage, carCity4, history4,
                                LocalDate.now().plusDays(3),
                                "Tire replacement", 100.0, "Completed", "Tires");

                ServiceActivity serviceActivity5 = factory.createServiceActivity(centralWorkshop, lorryCity5, history5,
                                LocalDate.now().plusDays(4),
                                "Engine diagnostics and repair", 500.0, "Completed", "Spark plugs, Engine oil");

                ServiceActivity serviceActivity6 = factory.createServiceActivity(eastSideGarage, pickupCity6, history6,
                                LocalDate.now().plusDays(5), "Tire replacement", 100.0, "Completed", "Tires");

                // Add service activities to workshops activity list
                autoTechWorkshop.addServiceActivity(serviceActivity1);
                cityGarage.addServiceActivity(serviceActivity2);
                speedyRepairs.addServiceActivity(serviceActivity3);
                downtownGarage.addServiceActivity(serviceActivity4);
                centralWorkshop.addServiceActivity(serviceActivity5);
                eastSideGarage.addServiceActivity(serviceActivity6);

                sharedDataModel.addServiceActivity(serviceActivity6);
                sharedDataModel.addServiceActivity(serviceActivity5);
                sharedDataModel.addServiceActivity(serviceActivity4);
                sharedDataModel.addServiceActivity(serviceActivity3);
                sharedDataModel.addServiceActivity(serviceActivity2);
                sharedDataModel.addServiceActivity(serviceActivity1);

                truckCity1.setVehicleMaintenanceSchedule(routineMaintenance);
                vanCity2.setVehicleMaintenanceSchedule(majorInspection);
                busCity3.setVehicleMaintenanceSchedule(safetyCheck);
                carCity4.setVehicleMaintenanceSchedule(regularCheckup);
                lorryCity5.setVehicleMaintenanceSchedule(annualInspection);
                pickupCity6.setVehicleMaintenanceSchedule(serviceUpdate);

                routineMaintenance.setVehicleForSchedule(pickupCity6);
                majorInspection.setVehicleForSchedule(vanCity2);
                safetyCheck.setVehicleForSchedule(busCity3);
                regularCheckup.setVehicleForSchedule(carCity4);
                annualInspection.setVehicleForSchedule(lorryCity5);
                serviceUpdate.setVehicleForSchedule(truckCity1);

                routineMaintenance.setWorkshop(eastSideGarage);
                majorInspection.setWorkshop(cityGarage);
                safetyCheck.setWorkshop(speedyRepairs);
                regularCheckup.setWorkshop(downtownGarage);
                annualInspection.setWorkshop(centralWorkshop);
                serviceUpdate.setWorkshop(autoTechWorkshop);

                // create serviceActivities

                // Associate cargos with vehicles
                sharedDataModel.addCargoToVehicle(electronicsCargo, truckCity1);
                sharedDataModel.addCargoToVehicle(furnitureCargo, vanCity2);
                sharedDataModel.addCargoToVehicle(foodCargo, busCity3);
                sharedDataModel.addCargoToVehicle(clothingCargo, carCity4);
                sharedDataModel.addCargoToVehicle(techCargo, carCity4);
                sharedDataModel.addCargoToVehicle(machineryCargo, lorryCity5);
                sharedDataModel.addCargoToVehicle(pharmaceuticalCargo, pickupCity6);
                sharedDataModel.addCargoToVehicle(booksCargo, pickupCity6);

                // Assign drivers to vehicles
                truckCity1.addResponsibleDriver(driverJohn);
                vanCity2.addResponsibleDriver(driverJane);
                busCity3.addResponsibleDriver(driverAlice);
                carCity4.addResponsibleDriver(driverBob);
                lorryCity5.addResponsibleDriver(driverEmma);
                pickupCity6.addResponsibleDriver(driverLiam);

                // Set vehicles for drivers
                driverJohn.setDrives(truckCity1);
                driverJane.setDrives(vanCity2);
                driverAlice.setDrives(busCity3);
                driverBob.setDrives(carCity4);
                driverEmma.setDrives(lorryCity5);
                driverLiam.setDrives(pickupCity6);

                // Calculate cargo weight for vehicles
                truckCity1.calculateCargoWeight();
                vanCity2.calculateCargoWeight();
                busCity3.calculateCargoWeight();
                carCity4.calculateCargoWeight();
                lorryCity5.calculateCargoWeight();
                pickupCity6.calculateCargoWeight();

                // add all employees to list
                sharedDataModel.addEmployee(driverLiam);
                sharedDataModel.addEmployee(driverEmma);
                sharedDataModel.addEmployee(driverBob);
                sharedDataModel.addEmployee(driverAlice);
                sharedDataModel.addEmployee(driverJane);
                sharedDataModel.addEmployee(driverJohn);

                sharedDataModel.addMaintenanceSchedule(routineMaintenance);
                sharedDataModel.addMaintenanceSchedule(majorInspection);
                sharedDataModel.addMaintenanceSchedule(safetyCheck);
                sharedDataModel.addMaintenanceSchedule(regularCheckup);
                sharedDataModel.addMaintenanceSchedule(annualInspection);
                sharedDataModel.addMaintenanceSchedule(serviceUpdate);

                history1.addServiceActivity(serviceActivity1);
                history2.addServiceActivity(serviceActivity2);
                history3.addServiceActivity(serviceActivity3);
                history4.addServiceActivity(serviceActivity4);
                history5.addServiceActivity(serviceActivity5);
                history6.addServiceActivity(serviceActivity6);

                // recaulculate total service cost
                truckCity1.recalculateTotalServiceCost();
                vanCity2.recalculateTotalServiceCost();
                busCity3.recalculateTotalServiceCost();
                carCity4.recalculateTotalServiceCost();
                lorryCity5.recalculateTotalServiceCost();
                pickupCity6.recalculateTotalServiceCost();

                sharedDataModel.getVehicles()
                                .addAll(Arrays.asList(truckCity1, vanCity2, busCity3, carCity4, lorryCity5,
                                                pickupCity6));
        }

        private static int getRandomEAN() {
                Random random = new Random();
                return random.nextInt(1000000000);
        }

}

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

public class TestDataPopulator {

        public static final String[] CAR_NAMES = {
                        "Achilles Speedster",
                        "Aphrodite Cruiser",
                        "Apollo Roadster",
                        "Artemis Runner",
                        "Centaur Cruiser",
                        "Cerberus Chariot",
                        "Chimera Voyager",
                        "Cyclops Sprinter",
                        "Druid Transporter",
                        "Fenrir Cruiser",
                        "GigaCar",
                        "Griffin Glide",
                        "Harpy Hatchback",
                        "Hydra Hybrid",
                        "Medusa Sedan",
                        "Minotaur Mustang",
                        "Nuts Car",
                        "Nymph Navigator",
                        "Pegasus Pickup",
                        "Phoenix Phaeton",
                        "Siren Sports Car",
                        "Sphinx SUV",
                        "Thor Thunderbolt",
                        "Titan Titan",
                        "Valkyrie Van",
                        "Vampire Vehicle",
                        "Werewolf Wheels",
                        "Wyvern Wagon",
                        "Zeus Zipper",
                        "Zombie Zoomer"
        };

        public static final String[] SWEDISH_PHONE_NUMBERS = {
                        "08-123 456 78",
                        "08-234 567 89",
                        "08-345 678 90",
                        "08-456 789 01",
                        "08-567 890 12",
                        "08-678 901 23",
                        "08-789 012 34",
                        "08-890 123 45",
                        "08-901 234 56",
                        "08-012 345 67",
                        "08-123 456 78",
                        "08-234 567 89",
                        "08-345 678 90",
                        "08-456 789 01",
                        "08-567 890 12",
                        "08-678 901 23",
                        "08-789 012 34",
                        "08-890 123 45",
                        "08-901 234 56",
                        "08-012 345 67",
                        "08-123 456 78",
                        "08-234 567 89",
                        "08-345 678 90",
                        "08-456 789 01",
                        "08-567 890 12",
                        "08-678 901 23",
                        "08-789 012 34",
                        "08-890 123 45",
                        "08-901 234 56",
                        "08-012 345 67",
                        "08-123 456 78",
                        "08-234 567 89",
                        "08-345 678 90",
                        "08-456 789 01",
                        "08-567 890 12",
                        "08-678 901 23",
                        "08-789 012 34",
                        "08-890 123 45",
                        "08-901 234 56",
                        "08-012 345 67",
                        "08-123 456 78",
                        "08-234 567 89",
                        "08-345 678 90",
                        "08-456 789 01",
                        "08-567 890 12",
                        "08-678 901 23",
                        "08-789 012 34",
                        "08-890 123 45",
                        "08-901 234 56",
        };

        public static final String[] FICTIONAL_STREETS = {
                        "Sesame Street", // Sesame Street
                        "Baker Street", // Sherlock Holmes
                        "Diagon Alley", // Harry Potter
                        "Privet Drive", // Harry Potter
                        "Abbey Road", // The Beatles
                        "Platform 9¾", // Harry Potter
                        "Elm Street", // A Nightmare on Elm Street
                        "Hogwarts Express", // Harry Potter
                        "Wisteria Lane", // Desperate Housewives
                        "Peachtree Street", // Gone with the Wind
                        "Kings Cross Station", // Harry Potter
                        "Wall Street", // Financial Districts
                        "Main Street", // Generic street name
                        "Elmwood Avenue", // Generic street name
                        "Maple Lane", // Generic street name
                        "Oakwood Drive", // Generic street name
                        "Cedar Street", // Generic street name
                        "Pine Avenue", // Generic street name
                        "Rosewood Lane", // Generic street name
                        "Willow Way", // Generic street name
                        "Cypress Court", // Generic street name
                        "Magnolia Street", // Generic street name
                        "Chestnut Avenue", // Generic street name
                        "Cottonwood Lane", // Generic street name
                        "Sycamore Road", // Generic street name
                        "Aspen Avenue", // Generic street name
                        "Redwood Drive", // Generic street name
                        "Birch Lane", // Generic street name
                        "Cherry Street", // Generic street name
                        "Mulberry Lane", // Generic street name
                        "Hickory Avenue", // Generic street name
        };

        public static final String[] EMPLOYEE_NAMES = {
                        "Eric Cartman", "Stan Marsh", "Kyle Broflovski", "Kenny McCormick",
                        "Rick Sanchez", "Morty Smith", "Summer Smith", "Beth Smith",
                        "Jerry Smith", "Mr. Meeseeks",
                        "Homer Simpson", "Marge Simpson", "Bart Simpson", "Lisa Simpson", "Maggie Simpson",
                        "Peter Griffin", "Lois Griffin", "Meg Griffin", "Chris Griffin", "Stewie Griffin",
                        "Brian Griffin", "Cleveland Brown",
                        "Bob Belcher", "Linda Belcher", "Tina Belcher", "Gene Belcher", "Louise Belcher",
                        "Archer Sterling", "Lana Kane", "Cyril Figgis", "Pam Poovey", "Cheryl Tunt",
                        "Michael Scott", "Dwight Schrute", "Jim Halpert", "Pam Beesly", "Ryan Howard",
                        "Leslie Knope", "Ron Swanson", "April Ludgate", "Andy Dwyer", "Ben Wyatt",
                        "Fry", "Leela", "Bender", "Professor Farnsworth", "Zoidberg",
                        "BoJack Horseman", "Diane Nguyen", "Todd Chavez", "Princess Carolyn", "Mr. Peanutbutter"
        };

        public static final String[] REPAIR_SHOP_NAMES = {
                        "Fix-It Fast Repair Shop",
                        "QuickFix Electronics Repair",
                        "TechPro Repair Center",
                        "Gadget Guru Repairs",
                        "Mobile Medic Repair Services",
                        "Device Doctor Repair Shop",
                        "Express Auto Repairs",
                        "Speedy Appliance Repair",
                        "Computer Clinic Tech Repairs",
                        "iRepair Solutions",
                        "Mr. Fix-It All Repairs",
                        "Precision Auto Body Repair",
                        "Handyman Heroes Repair Shop",
                        "ProTech Phone Repair",
                        "The Repair Shoppe",
                        "Reliable Roof Repair",
                        "Master Mechanics Auto Repair",
                        "Geek Squad Tech Repair",
                        "Gadget Wizards Repair Center",
                        "Fast & Furious Auto Repairs"
        };

        public static final String[] LOCATIONS = {
                        "Springfield", // The Simpsons
                        "Quahog", // Family Guy
                        "South Park", // South Park
                        "C-137", // Rick and Morty (Dimension)
                        "Pawnee", // Parks and Recreation
                        "Scranton", // The Office
                        "Dunder Mifflin", // The Office
                        "Hawkins", // Stranger Things
                        "Greendale", // Community
                        "Stars Hollow", // Gilmore Girls
                        "Sacred Heart Hospital", // Scrubs
                        "Eternia", // Masters of the Universe
                        "Bikini Bottom", // SpongeBob SquarePants
                        "Gotham City", // Batman
                        "Metropolis", // Superman
                        "Bedrock", // The Flintstones
                        "Riverdale", // Riverdale/Archie Comics
                        "Hill Valley", // Back to the Future
                        "Sunnydale", // Buffy the Vampire Slayer
                        "Twin Peaks", // Twin Peaks
                        "Pleasantville", // Pleasantville
                        "Shelbyville", // The Simpsons
                        "Vice City", // Grand Theft Auto
                        "Gilead", // The Handmaid's Tale
                        "Hogsmeade", // Harry Potter
                        "Gotham", // Batman
                        "Kings Landing", // Game of Thrones
                        "Winterfell", // Game of Thrones
                        "The Upside Down", // Stranger Things
                        "Cloud City", // Star Wars
                        "New New York", // Futurama
                        "Whoville", // Dr. Seuss
                        "Asgard", // Norse Mythology/Marvel Comics
                        "Wakanda", // Marvel Comics
                        "Atlantis", // Various Mythologies
                        "Neverland", // Peter Pan
                        "Oz", // The Wizard of Oz
                        "Narnia", // The Chronicles of Narnia
                        "Middle Earth", // Lord of the Rings
                        "Hogwarts", // Harry Potter
                        "Mordor", // Lord of the Rings
                        "The Shire", // Lord of the Rings
                        "Pandora", // Avatar
                        "Gallifrey", // Doctor Who
                        "Vulcan", // Star Trek
                        "Krypton", // Superman
        };

        public static final String[] FICTIONAL_CARGO_NAMES = {
                        "Excalibur Swords",
                        "Unobtainium Crystals",
                        "Dragon Eggs",
                        "Alien Artifacts",
                        "Mithril Ingots",
                        "Pirate Treasure Chests",
                        "Elixir of Immortality",
                        "Time-Traveling Devices",
                        "Magic Wands",
                        "Fairy Dust",
                        "Ancient Scrolls",
                        "Space Alien Technology",
                        "Enchanted Potions",
                        "Elven Jewelry",
                        "Mythical Creatures",
                        "Invisibility Cloaks",
                        "Golden Fleece",
                        "Cursed Relics",
                        "Stardust Samples",
                        "Haunted Artwork"
        };

        public static final String[] FUNNY_TITLES = {
                        "Chief Coffee Drinker",
                        "Office Comedian",
                        "Director of Office Shenanigans",
                        "Senior Procrastinator",
                        "VP of Snack Attacks",
                        "Chief Emoji Translator",
                        "Master of Puns",
                        "Social Media Ninja",
                        "Director of Napping Strategy",
                        "Office Jokester",
                        "Captain of the Watercooler Chat",
                        "Chief Meme Creator",
                        "Head of Sarcasm",
                        "Senior Sock Puppeteer",
                        "VP of Desk Toy Collection",
                        "Director of Random Facts",
                        "Office Magician",
                        "Chief Emoji Artist",
                        "Director of Office Pranks",
                        "Master of Office Supplies"
        };

        public static void populateTestData(SharedVehicleDataModel sharedDataModel, Factory factory) {


                Random random = new Random();

                List<String> shuffledEmployeeNames = new ArrayList<>(Arrays.asList(EMPLOYEE_NAMES));
                List<String> shuffledLocations = new ArrayList<>(Arrays.asList(LOCATIONS));
                List<String> shuffledPhoneNumbers = new ArrayList<>(Arrays.asList(SWEDISH_PHONE_NUMBERS));
                List<String> shuffledStreets = new ArrayList<>(Arrays.asList(FICTIONAL_STREETS));
                List<String> shuffledRepairShopNames = new ArrayList<>(Arrays.asList(REPAIR_SHOP_NAMES));
                List<String> shuffledCargoNames = new ArrayList<>(Arrays.asList(FICTIONAL_CARGO_NAMES));
                List<String> shuffledFunnyTitles = new ArrayList<>(Arrays.asList(FUNNY_TITLES));
                List<String> ShuffledCarNames = new ArrayList<>(Arrays.asList(CAR_NAMES));
                Collections.shuffle(shuffledFunnyTitles);
                Collections.shuffle(shuffledCargoNames);
                Collections.shuffle(shuffledRepairShopNames);
                Collections.shuffle(shuffledStreets);
                Collections.shuffle(shuffledPhoneNumbers);
                Collections.shuffle(shuffledLocations);
                Collections.shuffle(shuffledEmployeeNames);
                Collections.shuffle(ShuffledCarNames);

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
                                shuffledStreets.get(0), shuffledPhoneNumbers.get(0), shuffledFunnyTitles.get(0));

                Employee driverJane = factory.createEmployee(shuffledEmployeeNames.get(1), "102",
                                shuffledStreets.get(1), shuffledPhoneNumbers.get(1), shuffledFunnyTitles.get(1));

                Employee driverAlice = factory.createEmployee(shuffledEmployeeNames.get(2), "103",
                                shuffledStreets.get(2), shuffledPhoneNumbers.get(2), shuffledFunnyTitles.get(2));

                Employee driverBob = factory.createEmployee(shuffledEmployeeNames.get(3), "104",
                                shuffledStreets.get(3), shuffledPhoneNumbers.get(3), shuffledFunnyTitles.get(3));

                Employee driverEmma = factory.createEmployee(shuffledEmployeeNames.get(4), "105",
                                shuffledStreets.get(4), shuffledPhoneNumbers.get(4), shuffledFunnyTitles.get(4));

                Employee driverLiam = factory.createEmployee(shuffledEmployeeNames.get(5), "106",
                                shuffledStreets.get(5), shuffledPhoneNumbers.get(5), shuffledFunnyTitles.get(5));

                Workshop autoTechWorkshop = factory.createWorkshop(shuffledRepairShopNames.get(0),
                                shuffledStreets.get(0), true);
                Workshop cityGarage = factory.createWorkshop(shuffledRepairShopNames.get(1), shuffledStreets.get(1),
                                false);
                Workshop speedyRepairs = factory.createWorkshop(shuffledRepairShopNames.get(2), shuffledStreets.get(2),
                                false);
                Workshop downtownGarage = factory.createWorkshop(shuffledRepairShopNames.get(3), shuffledStreets.get(3),
                                false);
                Workshop centralWorkshop = factory.createWorkshop(shuffledRepairShopNames.get(4),
                                shuffledStreets.get(4), false);
                Workshop eastSideGarage = factory.createWorkshop(shuffledRepairShopNames.get(5), shuffledStreets.get(5),
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
                                random.nextInt(100000), "Small Truck", ShuffledCarNames.get(0),
                                shuffledLocations.get(0),
                                new ArrayList<>(Arrays.asList(electronicsCargo)), 2000.0, 10, history1,
                                routineMaintenance,
                                new ArrayList<>(Arrays.asList(autoTechWorkshop)));

                Vehicle vanCity2 = factory.createVehicle(
                                random.nextInt(100000), "Van", ShuffledCarNames.get(1), shuffledLocations.get(1),
                                new ArrayList<>(Arrays.asList(furnitureCargo)), 1500.0, 5, history2, majorInspection,
                                new ArrayList<>(Arrays.asList(cityGarage)));

                Vehicle busCity3 = factory.createVehicle(
                                random.nextInt(100000), "Large Truck", ShuffledCarNames.get(2),
                                shuffledLocations.get(2),
                                new ArrayList<>(Arrays.asList(foodCargo)), 3000.0, 20, history3, safetyCheck,
                                new ArrayList<>(Arrays.asList(speedyRepairs)));

                Vehicle carCity4 = factory.createVehicle(
                                random.nextInt(100000), "Small Truck", ShuffledCarNames.get(3),
                                shuffledLocations.get(3),
                                new ArrayList<>(Arrays.asList(clothingCargo, techCargo)), 1000.0, 4, history4,
                                regularCheckup,
                                new ArrayList<>(Arrays.asList(downtownGarage)));

                Vehicle lorryCity5 = factory.createVehicle(
                                random.nextInt(100000), "Large Truck", ShuffledCarNames.get(4),
                                shuffledLocations.get(4),
                                new ArrayList<>(Arrays.asList(machineryCargo)), 5000.0, 15, history5, annualInspection,
                                new ArrayList<>(Arrays.asList(centralWorkshop)));

                Vehicle pickupCity6 = factory.createVehicle(
                                random.nextInt(100000), "Small Truck", ShuffledCarNames.get(5),
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

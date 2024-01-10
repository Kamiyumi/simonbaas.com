package se.lu.ics.models;

public class Cargo {
    private String cargoName;
    private double cargoWeight;
    private double cargoTotalWeight;
    private int cargoAmount;
    private int ean;
    private Vehicle vehicle;

    public Cargo(String cargoName, double cargoWeight, int cargoAmount, int ean) {
        this.cargoName = cargoName;
        this.cargoWeight = cargoWeight;
        this.cargoAmount = cargoAmount;
        this.ean = ean;
        this.cargoTotalWeight = cargoWeight * cargoAmount;
    }


    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getVehicleName() {
        if (vehicle != null) {
            return vehicle.getName();
        } else {
            return "N/A"; 
        }
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        vehicle.addCargoWeight(cargoTotalWeight);
    }

    public String getCargoName() {
        return cargoName;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public int getCargoAmount() {
        return cargoAmount;
    }

    public int getEan() {
        return ean;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public void setCargoAmount(int cargoAmount) {
        this.cargoAmount = cargoAmount;
    }

    public void setEan(int ean) {
        this.ean = ean;
    }

    public double getCargoTotalWeight() {
        return cargoTotalWeight;
    }

    public void setCargoTotalWeight(double cargoTotalWeight) {
        this.cargoTotalWeight = cargoTotalWeight;
    }

    @Override
    public String toString() {
        return "Cargo Info:" +
                "\nCargo Type: '" + cargoName + '\'' +
                "\nWeight: " + String.format("%.2f", cargoWeight) + " KG" +
                "\nAmount: " + cargoAmount +
                "\nEAN: " + ean + "\n\n";

    }

}

package se.lu.ics.models;

public class Employee {
    private String name;
    private String ssNo;
    private String address;
    private String phoneNumber;
    private String employeeType;
    private Vehicle driverOf;

    public Employee(String name, String ssNo, String address, String phoneNumber, String employeeType) {
        this.name = name;
        this.ssNo = ssNo;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.employeeType = employeeType;
    }

    public Vehicle getDriverOf() {
        return driverOf;
    }

    public void setDriverOf(Vehicle driverOf) {
        this.driverOf = driverOf;
    }

    public void setDrives(Vehicle vehicle) {
        this.driverOf = vehicle;
    }

  

    public String getName() {
        return name;
    }

    public String getSsNo() {
        return ssNo;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String type) {
        this.employeeType = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSsNo(String ssNo) {
        this.ssNo = ssNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Employee" +
                "\nName: " + name +
                "\nSS No: " + ssNo +
                "\nAddress: " + address +
                "\nPhone Number: " + phoneNumber +
                "\nEmployee Type: " + employeeType +
                "\nDrives: " + (driverOf != null ? driverOf.getName() : "None");
    }

}
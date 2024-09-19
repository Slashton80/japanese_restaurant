package ca.hccis.bus.pass.entity;

import java.util.Scanner;

public class BusPass {

    private String name; // String Person’s name
    private String address; // String Person’s address
    private String city; // String Person’s city
    private String preferredRoute; // String Preferred bus route
    private int passType; // int Type of pass (k12, student, senior, or regular)
    private boolean validForRuralRoute; // boolean If valid for rural routes
    private int lengthOfPass; // int Number of days for the pass
    private String startDate; // String Date pass starts being valid
    private double cost;

    public static final double COST_BASE_DAY = 1;
    public static final double COST_OVER_20_DAYS = 0.5;
    public static final double COST_RURAL = 10;

    public void getInformation(){
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        name = input.nextLine();
        System.out.println("Address: ");
        address = input.nextLine();
        System.out.println("Number of Days: ");
        lengthOfPass = input.nextInt();
        input.nextLine();
        System.out.println("City: ");
        city = input.nextLine();
        System.out.println("Preferred Route: ");
        preferredRoute = input.nextLine();
        System.out.println("Pass Type: ");
        passType = input.nextInt();
        input.nextLine();
        System.out.println("Valid For Rural Route (y/n): ");
        validForRuralRoute = input.nextLine().equalsIgnoreCase("y");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPreferredRoute() {
        return preferredRoute;
    }

    public void setPreferredRoute(String preferredRoute) {
        this.preferredRoute = preferredRoute;
    }

    public int getPassType() {
        return passType;
    }

    public void setPassType(int passType) {
        this.passType = passType;
    }

    public boolean isValidForRuralRoute() {
        return validForRuralRoute;
    }

    public void setValidForRuralRoute(boolean validForRuralRoute) {
        this.validForRuralRoute = validForRuralRoute;
    }

    public int getLengthOfPass() {
        return lengthOfPass;
    }

    public void setLengthOfPass(int lengthOfPass) {
        this.lengthOfPass = lengthOfPass;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Calculate cost of this bus pass
     * @return cost
     * @since 20240916
     * @author BJM
     */
    public double calculateCost(){

        double cost;

        //Update based on length of pass
        if(lengthOfPass <= 20) {
             cost = lengthOfPass * COST_BASE_DAY;
        }else{
            cost = (20*COST_BASE_DAY)+(lengthOfPass-20)*COST_OVER_20_DAYS;
        }

        //Add premium for rural routes
        if(validForRuralRoute){
            cost += COST_RURAL;
        }

        return cost;
    }

    @Override
    public String toString() {
        return "BusPass" + System.lineSeparator() +
                "Name: " + name + System.lineSeparator() +
                "Address: " + address + System.lineSeparator() +
                "City: " + city + System.lineSeparator() +
                "Preferred Route: " + preferredRoute + System.lineSeparator() +
                "Pass Type: " + passType + System.lineSeparator() +
                "Valid For Rural Route: " + validForRuralRoute + System.lineSeparator() +
                "Length of pass: " + lengthOfPass + System.lineSeparator() +
                "Start Date: " + startDate + System.lineSeparator() +
                "Cost: " + cost + System.lineSeparator();

    }

    public String toCSV() {
        return  name + "," + address + "," + lengthOfPass;
    }

}

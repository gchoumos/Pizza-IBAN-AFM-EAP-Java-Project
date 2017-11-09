/**
  TODOs:
    1. Input validation is always a nice-to-have. This program can easily
       be expanded in order to include those sanity checks and foolproof
       functionality.

    2. Having to use a comma instead of a dot for the doubles is really
       annoying. This can be easily changed by importing the Locale and
       using the ENGLISH one through the useLocale method on the scanner
       object.

    3. The total price and area privates are most probably an overkill. This
       is true only in the context of this exercise. Otherwise, and with regards
       to expandability, they are not an overkill at all and I would actually
       encourage their existence.

    4. Instead of using the ternary operator twice for the actual number of family
       pizzas that need to be ordered, it can just be stored as a private variable too.
       That's really an unimportant beautification detail though.

    5. The output of compare method is verbose. It can be reduced, but for the purpose of
       this exercise it seems like it is nice to have all the info that led to the decision
       there instead of having the program to work as a completely black box.

    6. I could add the case where both the regular and the family price/area ratio are equal.
       For now, this extreme case will output the family proposal.

    7. The printInputs method was created at first for debugging purposes and can be removed.
       It's commented out in the main, thus never called. Doesn't hurt that it's there though.
**/

import java.io.*;
import java.util.Scanner;

public class Pizza {

    // Private members - To be given by the user
    private double diameterRegular, diameterFamily;
    private double priceRegular, priceFamily;
    private int numPeople;

    // Private members - To be calculated
    private double totalPriceRegular, totalPriceFamily;
    private double totalAreaRegular, totalAreaFamily;

    private double regularPrSurfRate, familyPrSurfRate;

    // Mutators
    public void setDiameterRegular(double val) {
        this.diameterRegular = val;
    }
    public void setDiameterFamily(double val) {
        this.diameterFamily = val;
    }
    public void setPriceRegular(double val) {
        this.priceRegular = val;
    }
    public void setPriceFamily(double val) {
        this.priceFamily = val;
    }
    public void setNumPeople(int val) {
        this.numPeople = val;
    }
    public void setTotalPriceRegular(double val) {
        this.totalPriceRegular = val;
    }
    public void setTotalPriceFamily(double val) {
        this.totalPriceFamily = val;
    }
    public void setTotalAreaRegular(double val) {
        this.totalAreaRegular = val;
    }
    public void setTotalAreaFamily(double val) {
        this.totalAreaFamily = val;
    }
    public void setRegularPrSurfRate(double val) {
        this.regularPrSurfRate = val;
    }
    public void setFamilyPrSurfRate(double val) {
        this.familyPrSurfRate = val;
    }

    // Accessors
    public double getDiameterRegular() {
        return this.diameterRegular;
    }
    public double getDiameterFamily() {
        return this.diameterFamily;
    }
    public double getPriceRegular() {
        return this.priceRegular;
    }
    public double getPriceFamily() {
        return this.priceFamily;
    }
    public int getNumPeople() {
        return this.numPeople;
    }
    public double getTotalPriceRegular() {
        return this.totalPriceRegular;
    }
    public double getTotalPriceFamily() {
        return this.totalPriceFamily;
    }
    public double getTotalAreaRegular() {
        return this.totalAreaRegular;
    }
    public double getTotalAreaFamily() {
        return this.totalAreaFamily;
    }
    public double getRegularPrSurfRate() {
        return this.regularPrSurfRate;
    }
    public double getFamilyPrSurfRate() {
        return this.familyPrSurfRate;
    }

	// Method responsible for the user inputs
    public void readUserInput() {

        Scanner sc = new Scanner(System.in);

        // Time to read the required input and set the private members accordingly
        System.out.print("Please enter the number of participants:");
        this.setNumPeople(sc.nextInt());

        System.out.print("Enter the diameter of regular pizza size:");
        this.setDiameterRegular(sc.nextDouble());
        System.out.print("Enter the diameter of family pizza size:");
        this.setDiameterFamily(sc.nextDouble());
        System.out.print("Enter the price for the regular pizza:");
        this.setPriceRegular(sc.nextDouble());
        System.out.print("Enter the price for the family pizza:");
        this.setPriceFamily(sc.nextDouble());
    }

    // Just a method to dump the private variable values - For debugging purposes
    public void printInputs() {
        System.out.println("Participants: " + this.getNumPeople());
        System.out.println("Regular diameter: " + this.getDiameterRegular() + " cm");
        System.out.println("Family diameter: " + this.getDiameterFamily() + " cm");
        System.out.println("Regular price: " + this.getPriceRegular() + " euros");
        System.out.println("Family price: " + this.getPriceFamily() + " euros");
    }

    public void calculateRegular() {
        this.setTotalPriceRegular(this.getPriceRegular() * this.getNumPeople());
        this.setTotalAreaRegular(Math.PI * Math.pow(this.getDiameterRegular(),2) * this.getNumPeople());
        this.setRegularPrSurfRate(this.getTotalPriceRegular()/this.getTotalAreaRegular());
    }

    public void calculateFamily() {
        // The number of family pizzas to be ordered depends on the number of people.
        int numOfFamilyPizzas = (this.getNumPeople()%2 == 0) ? this.getNumPeople()/2 : this.getNumPeople()/2 + 1;
        this.setTotalPriceFamily(numOfFamilyPizzas * this.getPriceFamily());
        this.setTotalAreaFamily(Math.PI * Math.pow(this.getDiameterFamily(),2) * 1/2 * this.getNumPeople());
        this.setFamilyPrSurfRate(this.getTotalPriceFamily()/this.getTotalAreaFamily());
    }

    public void compare() {
        int numOfFamilyPizzas = (this.getNumPeople()%2 == 0) ? this.getNumPeople()/2 : this.getNumPeople()/2 + 1;

        System.out.println("There are " + this.getNumPeople() + " that will participate in the order\n");

        System.out.println("If they choose to go with the regular size:");
        System.out.println("\tTotal number of pizzas: " + this.getNumPeople());
        System.out.println("\tTotal price: " + this.getTotalPriceRegular() + " euros");
        System.out.println("\tTotal area eaten: " + this.getTotalAreaRegular() + " cm^2");
        System.out.println("\tPrice/Area rate: " + this.getRegularPrSurfRate() + "\n");

        System.out.println("If they choose to go with the family size:");
        System.out.println("\tTotal number of pizzas: " + numOfFamilyPizzas);
        System.out.println("\tTotal price: " + this.getTotalPriceFamily() + " euros");
        System.out.println("\tTotal area eaten: " + this.getTotalAreaFamily() + " cm^2");
        System.out.println("\tPrice/Area rate: " + this.getFamilyPrSurfRate() + "\n");

        if (this.getNumPeople()%2 != 0) {
            System.out.println("\nNOTE:");
            System.out.println("In the family case, since there is an odd number of participants, half a family pizza will go to waste!");
            System.out.println("This half wasted family pizza area has NOT been taken into account in the calculation of the ratio!");
            System.out.println("However, the price has been calculated. This is the expected behaviour.");
        }

        System.out.println("\nResult:\n*******");
        if (this.getRegularPrSurfRate() < this.getFamilyPrSurfRate()) {
            System.out.println("Order " + this.getNumPeople() + " regular pizza(s).");
            System.out.println("The price/surface rate is " + this.getRegularPrSurfRate()); 
        } else {
            System.out.println("Order " + numOfFamilyPizzas + " family pizza(s).");
            System.out.println("The price/surface rate is " + this.getFamilyPrSurfRate()); 
        }
    }

    public static void main (String[] args) {
        // A regular placeholder and super SWAG print
        //System.out.println("Hello World");

        // Create an instance of our Pizza class
        Pizza pizza = new Pizza();

        // Read inputs from user
        pizza.readUserInput();

        // Dump values (Debug) - To be commented out
        //pizza.printInputs();

        // Calculate regular and family pizza order cases
        pizza.calculateRegular();
        pizza.calculateFamily();

        // Compare those 2 and decide which is the best
        pizza.compare();
    }
}



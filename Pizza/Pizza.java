/**
  Notes:
    1. Input validation is always a nice-to-have. This program can easily
       be expanded in order to include those sanity checks and foolproof
       functionality.

    2. Having to use a comma instead of a dot for the doubles is really
       annoying. This can be easily changed by importing the Locale and
       using the ENGLISH one through the useLocale method on the scanner
       object.

    3. Accessors and mutators seemed like an overkill with regards to this
       particular exercise. So, although they were first created, now they
       are removed.

    4. The output of compare method is verbose. It can be reduced, but for the purpose of
       this exercise it seems like it is nice to have all the info that led to the decision
       there instead of having the program to work as a completely black box.

    5. I could add the case where both the regular and the family price/area ratio are equal.
       For now, this extreme case will output the family proposal.

    6. The printInputs method was created at first for debugging purposes and can be removed.
       It's commented out in the main, thus never called. Doesn't hurt that it's there though.

    7. Convert the multiple printlines to single calls.
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

    // All the private members are being accessed by the object itself so
    // the accessors and mutators would be an overkill for the purpose of
    // this exercise

    // Method responsible for the user inputs
    public void readUserInput() {

        Scanner sc = new Scanner(System.in);

        // Time to read the required input and set the private members accordingly
        System.out.print("Please enter the number of participants:");
        this.numPeople = sc.nextInt();

        System.out.print("Enter the diameter of regular pizza size:");
        this.diameterRegular = sc.nextDouble();
        System.out.print("Enter the diameter of family pizza size:");
        this.diameterFamily = sc.nextDouble();
        System.out.print("Enter the price for the regular pizza:");
        this.priceRegular = sc.nextDouble();
        System.out.print("Enter the price for the family pizza:");
        this.priceFamily = sc.nextDouble();
    }

    // Just a method to dump the private variable values - For debugging purposes
    public void printInputs() {
        System.out.println("Participants: " + this.numPeople);
        System.out.println("Regular diameter: " + this.diameterRegular + " cm");
        System.out.println("Family diameter: " + this.diameterFamily + " cm");
        System.out.println("Regular price: " + this.priceRegular + " euros");
        System.out.println("Family price: " + this.priceFamily + " euros");
    }

    public void calculateRegular() {
        this.totalPriceRegular = this.priceRegular * this.numPeople;
        this.totalAreaRegular = Math.PI * Math.pow(this.diameterRegular/2,2) * this.numPeople;
        this.regularPrSurfRate = this.totalPriceRegular/this.totalAreaRegular;
    }

    public void calculateFamily() {
        // The number of family pizzas to be ordered depends on the number of people.
        int numOfFamilyPizzas;
        if (this.numPeople % 2 == 0) {
            numOfFamilyPizzas = this.numPeople/2;
        } else {
            numOfFamilyPizzas = this.numPeople/2 + 1;
        }
        this.totalPriceFamily = numOfFamilyPizzas * this.priceFamily;
        this.totalAreaFamily = Math.PI * Math.pow(this.diameterFamily/2,2) * 1/2 * this.numPeople;
        this.familyPrSurfRate = this.totalPriceFamily/this.totalAreaFamily;
    }

    public void compare() {
        int numOfFamilyPizzas = (this.numPeople%2 == 0) ? this.numPeople/2 : this.numPeople/2 + 1;

        System.out.println("There are " + this.numPeople + " that will participate in the order\n");

        System.out.println("If they choose to go with the regular size:");
        System.out.println("\tTotal number of pizzas: " + this.numPeople);
        System.out.println("\tTotal price: " + this.totalPriceRegular + " euros");
        System.out.println("\tTotal area eaten: " + this.totalAreaRegular + " cm^2");
        System.out.println("\tPrice/Area rate: " + this.regularPrSurfRate + "\n");

        System.out.println("If they choose to go with the family size:");
        System.out.println("\tTotal number of pizzas: " + numOfFamilyPizzas);
        System.out.println("\tTotal price: " + this.totalPriceFamily + " euros");
        System.out.println("\tTotal area eaten: " + this.totalAreaFamily + " cm^2");
        System.out.println("\tPrice/Area rate: " + this.familyPrSurfRate + "\n");

        if (this.numPeople%2 != 0) {
            System.out.println("\nNOTE:");
            System.out.println("In the family case, since there is an odd number of participants, half a family pizza will go to waste!");
            System.out.println("This half wasted family pizza area has NOT been taken into account as eaten for the calculation of the ratio!");
            System.out.println("However, the price has been calculated. This is the expected behaviour.");
        }

        System.out.println("\nResult:\n*******");
        if (this.regularPrSurfRate < this.familyPrSurfRate) {
            System.out.println("Order " + this.numPeople + " regular pizza(s).");
            System.out.println("The price/surface rate is " + this.regularPrSurfRate); 
        } else {
            System.out.println("Order " + numOfFamilyPizzas + " family pizza(s).");
            System.out.println("The price/surface rate is " + this.familyPrSurfRate); 
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



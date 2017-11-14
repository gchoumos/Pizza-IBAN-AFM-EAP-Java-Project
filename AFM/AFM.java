/**
    TODOs:
        1.  The termination check is annoying and painful the way it looks like. I can just
            convert the check into a regular expression - what I do now is embarrasing.

        2.  The while(true) is really ugly and dodgy. Please refactor that at some point.

        3.  In the checkValidity method, the 2 if and else if parts could be actually merged
            into one. This is only a detail with no functional impact.

        4.  It is not really clear if the exercise wants to ask whether the user wants to continue
            or not for all cases. It seems that for the case of invalid length and not digit only
            inputs, the program has to re-ask without asking the user about it.
            This should probably be clarified with the professor.
**/

import java.io.*;
import java.lang.*;
import java.util.Scanner;

public class AFM {

    // private members
    private String afmValue;
    private int sum;

    // mutators
    public void setAfmValue(String val) {
        this.afmValue = val;
    }
    public void initSum() {
        this.sum = 0;
    }
    public void updateSum(int val) {
        this.sum += val;
    }

    // accessors
    public String getAfmValue() {
        return this.afmValue;
    }

    // Method to check the validity of the given AFM length
    public boolean checkLength () {
        return this.afmValue.length() == 9;
    }

    // Method to check the afm consists only of digits. Sum is also calculated.
    public boolean checkDigitsAndCalculate() {
        // Iterate through the given afm
        for(int i=0;i<9;i++) {
            if (Character.isDigit(this.afmValue.charAt(i))) {
                // It is a digit indeed - So update the sum accordingly - Cast the Math.pow result to int
                this.updateSum(Character.getNumericValue(this.afmValue.charAt(i)) * (int) Math.pow(2,8-i));
            } else {
                // We found a non-digit!
                System.out.println("AFM should consist of digits only!");
                return false;
            }
        }
        // If we are here, we calculated the sum successfuly
        return true;
    }

        // Method to check the afm validity by computing the modulo 11 of sum and/or
        // checking the last digit remainder.
    public boolean checkValidity() {
        if (this.sum % 11 == 10 && Character.getNumericValue(this.afmValue.charAt(8)) == 0) {
            return true;
        } else if (this.sum % 11 == Character.getNumericValue(this.afmValue.charAt(8))) {
            return true;
        } else {
            return false;
        }
    }

        // A debug method - Forces the program to elaborate on the outcome for the valid or not AFM
    public void debugOutput() {
        System.out.println("Given AFM: " + this.afmValue);
        System.out.println("Calculated Sum: " + this.sum);
        System.out.println("Sum % 11: " + this.sum % 11);

        return;
    }

    public static void main (String[] args) {

        AFM afm = new AFM();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.print("Give AFM to check: ");
            afm.setAfmValue(sc.nextLine());

            System.out.println("Just read " + afm.getAfmValue());

            // Check length
            if (afm.checkLength()) {
                System.out.println("Valid length ...");
            } else {
                System.out.println("Invalid length! The afm should be 9 digits long!");
                // The exercise states that in this case we should ask again - so we continue.
                continue;
            }

            // Initialise sum
            afm.initSum();

            // Check the digits and calculate sum - If it goes well, this if will not be executed.
            if (!afm.checkDigitsAndCalculate()) {
                // The exercise states that in this case too we should ask again - so we continue
                continue;
            }

            // Now make the final AFM validity checks
            if (afm.checkValidity()){
                System.out.println("AFM entered is valid!");
            } else {
                System.out.println("AFM entered is NOT valid!");
            }

            // Comment this out as it is only here for debugging purposes
            afm.debugOutput();

            // Termination check
            System.out.print("Would you like to check for another [y,n]?: ");
            String answer = sc.nextLine();
            if (answer.equals("n") || answer.equals("N") || answer.equals("no") || answer.equals("NO")) {
                break;
            }
        }
    }
}

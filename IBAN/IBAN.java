/**
  TODOs:
    1. 
**/

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class IBAN {

	private char[] grIBAN;
	private char[] ibanInCheck,ibanInCheck2; // helper
	private char[] grToNum = {'1','6','2','7'}; // helper for the GR conversion

	// We need an accessor for the IBAN
	public String getIBAN() {
		return new String(this.grIBAN);
	}

	// Move GRDD to the end
	private void move4FirstToEnd() {
		for(int i=0;i<this.grIBAN.length;i++) {
			this.ibanInCheck[i] = this.grIBAN[(i + 4)%this.grIBAN.length];
		}
	}

	// Convert GR to 1627
	private void convertGRToDigits() {
		char[] lastDigits = Arrays.copyOfRange(this.ibanInCheck,this.ibanInCheck.length-2,this.ibanInCheck.length);

		// We now need an array that will be 27 + 2 = 29 chars long. Use helper
		this.ibanInCheck2 = new char[this.ibanInCheck.length + 2];

		for(int i=0;i<this.grIBAN.length+2;i++) {
			if (i<this.grIBAN.length-4) {
				this.ibanInCheck2[i] = this.ibanInCheck[i];
			} else if (i<this.grIBAN.length) {
				this.ibanInCheck2[i] = this.grToNum[i-this.grIBAN.length+4];
			} else {
				this.ibanInCheck2[i] = lastDigits[i-this.grIBAN.length];
			}
		}
	}

	// This method will perform the calculation steps of mod97 and the replacements.
	private void calculateModReplaceRepeat() {
		// will be performed 3 times
		for (int i=0;i<3;i++) {
			String ibanPart;

			// for the first 2 steps we get 9 digits, for the third one, it depends.
			ibanPart = new String(this.ibanInCheck2,0,9);

			// We calculate the mod 97 and convert it to a char array
			char[] digits = ("" + (Integer.parseInt(ibanPart) % 97)).toCharArray();

			this.ibanInCheck = new char[this.ibanInCheck2.length-7];

			this.ibanInCheck[0] = digits[0];
			if(digits.length==2) {
				this.ibanInCheck[1] = digits[1];
			}
			for(int j=digits.length;j<this.ibanInCheck.length;j++) {
				this.ibanInCheck[j] = this.ibanInCheck2[j+7];
			}

			// Last but not least, swap the variables so that the same names
			// will be reused in the next iteration.
			char[] temp = new char[this.ibanInCheck2.length];
			System.arraycopy(this.ibanInCheck2,0,temp,0,this.ibanInCheck2.length);
			this.ibanInCheck2 = new char[this.ibanInCheck.length];
			System.arraycopy(this.ibanInCheck,0,this.ibanInCheck2,0,this.ibanInCheck.length);
			this.ibanInCheck = new char[temp.length];
			System.arraycopy(temp,0,this.ibanInCheck,0,temp.length);
		}
	}

	// Final check of given IBAN - Returns true or false
	private boolean finalCheckIBAN() {
		// This is actually step 9 where we calculate the final mod
		if(Integer.parseInt(new String (this.ibanInCheck2)) % 97 == 1) {
			return true;
		} else {
			return false;
		}
	}

	// We know how to fix the iban and that's what we actually do here
	private void finalFixIBAN() {
		char[] fixed = ("" + (Integer.parseInt(new String (this.ibanInCheck2))%97)).toCharArray();
		if (Integer.parseInt(new String(fixed)) < 10) {
			this.grIBAN[2] = 0;
			this.grIBAN[3] = fixed[0];
		} else {
			this.grIBAN[2] = fixed[0];
			this.grIBAN[3] = fixed[1];
		}
	}

	// IBAN has already been checked if it's starting with GR. This function will
	// check whether the check digits are ok.
	public boolean checkIBAN() {

		// The initial (27 chars long)
		this.ibanInCheck = new char[this.grIBAN.length];// = this.grIBAN;

		// STEP 1: Move the first 4 chars to the end
		move4FirstToEnd();	
		// STEP 2: Convert G and R to 16 and 27 respectively
		convertGRToDigits();
		// STEPS 3 to 8: Calculate mod, replace and repeat.
		calculateModReplaceRepeat();

		// STEP 9: Return whether the given IBAN was good or not
		return finalCheckIBAN();
	}

	// fix and invalid iban by correcting its check digits
	public void makeIBAN() {

		// The initial (27 chars long)
		this.ibanInCheck = new char[this.grIBAN.length];// = this.grIBAN;

		// STEP 1: Convert the 2 check digits to 00
		this.grIBAN[2] = '0';
		this.grIBAN[3] = '0';

		// STEPS 2 to 8: We are re-using methods! That's a huge win!
		move4FirstToEnd();
		convertGRToDigits();
		calculateModReplaceRepeat();

		// STEP 9: Find the correct digits and return the fixed IBAN
		finalFixIBAN();
	}

	IBAN(String incomingIBAN) {
		this.grIBAN = incomingIBAN.toCharArray();
	}
	
}
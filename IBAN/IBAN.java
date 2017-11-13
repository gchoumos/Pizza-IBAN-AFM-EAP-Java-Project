/**
  Clarification needed:
    There is no information on whether we should expect all the mod results (apart
	from the last one) to be 2-digits long. The document shared doesn't include
	such indication.

  TODOs:
    1. 
**/

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class IBAN {

    // A Greek IBAN should be 27 characters long
	private char[] grIBAN;

	// Mutators
	

	// IBAN has already been checked if it's starting with GR. This funcrion will
	// check whether the check digits are ok.
	public boolean checkIBAN() {

		System.out.println("Checking IBAN ...");

		// The initial (27 chars long)
		char[] ibanInCheck = new char[this.grIBAN.length];// = this.grIBAN;

		/****
			STEP 1: Move the first 4 chars to the end
		****/
		// move the first four characters to the end (GRDD)
		for(int i=0;i<this.grIBAN.length;i++) {
			ibanInCheck[i] = this.grIBAN[(i + 4)%this.grIBAN.length];
			//System.out.println("Iban in iteration " + i + ": " + new String(ibanInCheck));
		}

		System.out.println("ibanInCheck after initial shift: " + new String(ibanInCheck));

		/****
			STEP 2: Convert G and R to 16 and 27 respectively
		****/
		char[] grToNum = {'1','6','2','7'};
		System.out.println("Greek indication is converted to: " + new String(grToNum));

		// In order to do this, we'll convert the last 4 characters GRXX to 1627XX
		// Get the last 2 digits
		char[] lastDigits = Arrays.copyOfRange(ibanInCheck,ibanInCheck.length-2,ibanInCheck.length);
		System.out.println("Last Digits of IBAN after initial shift: " + new String(lastDigits));

		// We now need an array that will be 27 + 2 = 29 chars long
		char[] ibanInCheck2 = new char[this.grIBAN.length + 2];

		// Now make the actual modification to the iban in check
		for(int i=0;i<this.grIBAN.length+2;i++) {
			if (i<this.grIBAN.length-4) {
				ibanInCheck2[i] = ibanInCheck[i];
			} else if (i<this.grIBAN.length) {
				ibanInCheck2[i] = grToNum[i-this.grIBAN.length+4];
			} else {
				ibanInCheck2[i] = lastDigits[i-this.grIBAN.length];
			}
		}
		System.out.println("IBAN as occured after shift and GR translation: " + new String(ibanInCheck2));

		/****
			STEP 3: Get the first 9 digits and calculate mod97
		****/
		String ibanPart = new String(ibanInCheck2,0,9);
		// We calculate the mod 97 and convert it to char array
		char[] digits = ("" + (Integer.parseInt(ibanPart) % 97)).toCharArray();

		/****
			STEP 4: Replace those first 9 digits with the mod97
		****/
		char [] ibanInCheck3 = new char[ibanInCheck2.length-7];

		ibanInCheck3[0] = digits[0];
		if(digits.length==2) {
			ibanInCheck3[1] = digits[1];
		}
		for(int i=digits.length;i<ibanInCheck3.length;i++) {
			ibanInCheck3[i] = ibanInCheck2[i+7];
		}
		System.out.println("IBAN after step 4: " + new String(ibanInCheck3));

		/****
			STEP 5: Repeat step 3
		****/
		String ibanPart2 = new String(ibanInCheck3,0,9);
		char[] digits2 = ("" + (Integer.parseInt(ibanPart2) % 97)).toCharArray();

		/****
			STEP 6: Repeat step 4
		****/
		char [] ibanInCheck4 = new char[ibanInCheck3.length-7];

		ibanInCheck4[0] = digits2[0];
		if(digits2.length==2) {
			ibanInCheck4[1] = digits2[1];
		}
		for(int i=digits2.length;i<ibanInCheck4.length;i++) {
			ibanInCheck4[i] = ibanInCheck3[i+7];
		}
		System.out.println("IBAN after step 6: " + new String(ibanInCheck4));

		/****
			STEP 7: Repeat step 5
		****/
		String ibanPart3 = new String(ibanInCheck4,0,9);
		char[] digits3 = ("" + (Integer.parseInt(ibanPart3) % 97)).toCharArray();

		/****
			STEP 8: Repeat step 6
		****/
		char [] ibanInCheck5 = new char[ibanInCheck4.length-7];

		ibanInCheck5[0] = digits3[0];
		if(digits3.length==2) {
			ibanInCheck4[1] = digits3[1];
		}
		ibanInCheck5[1] = digits3[1];
		for(int i=digits3.length;i<ibanInCheck5.length;i++) {
			ibanInCheck5[i] = ibanInCheck4[i+7];
		}
		System.out.println("IBAN after step 8: " + new String(ibanInCheck5));

		/****
			STEP 9: Check the final mod 97 - Should be 1 for the IBAN to be valid
		****/
		if(Integer.parseInt(new String (ibanInCheck5)) % 97 == 1) {
			return true;
		} else {
			return false;
		}
	}
/*
Q W E R T
0 3
Q W E Q T
1 4
Q W E Q W
2 0
E W E Q W
3 1
E R E Q W
4 2
E R T Q W 
*/
	IBAN(String incomingIBAN) {
		this.grIBAN = incomingIBAN.toCharArray();
		System.out.println("Initial IBAN: " + new String(this.grIBAN));
	}
	
}
/**
  TODOs:
    1. We could catch and print both format errors as well using a flag. For example
	   an IBAN candidate could be 26 characters long AND not starting with GR. In
	   that case we could print both errors but maybe that's an overkill.
**/

import java.io.*;
import java.util.Scanner;

public class IBANApp {

	private boolean checkFormat(String ibanToCheck) {
		if (ibanToCheck.length() != 27) {
			System.out.println("IBAN given not 27 characters long!");
			return false;
		} else if (ibanToCheck.charAt(0) != 'G' || ibanToCheck.charAt(1) != 'R') {
			System.out.println("IBAN given does not begin with 'GR'!");
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {

		// To be able to use the non-static method checkFormat
		IBANApp app = new IBANApp();
		
		// This should be converted to be reading from a file
		String ibanString = new String("GR3902604290000780200174634");

		// We have to check first if the format is good. If not we skip the
		// rest of the checks completely and move on to the next one
		if (app.checkFormat(ibanString)) {
			
			IBAN ib = new IBAN(ibanString);

			System.out.println("Checking IBAN: " + ib.getIBAN());
			if (ib.checkIBAN()) {
				System.out.println("IBAN is good!");
			} else {
				System.out.println("IBAN is not valid. We are fixing it ...");
				ib.makeIBAN();
				System.out.println("IBAN fixed --> " + ib.getIBAN());
			}
		} else {
			System.out.println("Format is not good. Moving on to the next one ...");
			// once the for loop gets in, continue here (not really necessary)
		}
	}
}
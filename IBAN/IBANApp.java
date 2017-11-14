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
			System.out.println("\tIBAN given not 27 characters long!");
			return false;
		} else if (ibanToCheck.charAt(0) != 'G' || ibanToCheck.charAt(1) != 'R') {
			System.out.println("\tIBAN given does not begin with 'GR'!");
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {

		// To be able to use the non-static method checkFormat
		IBANApp app = new IBANApp();

		// To be able to read user input
		Scanner sc = new Scanner(System.in);

		// We are going to read from a file
		try {
			//for the input
			File ibanFile = new File("inIBAN.txt");
			FileReader ibanFileReader = new FileReader(ibanFile);
			BufferedReader buffer = new BufferedReader(ibanFileReader);

			// for the output
			BufferedWriter ibanOut = new BufferedWriter(new FileWriter("outIBAN.txt"));

			String ibanString;

			int i = 0; // for unnecessary printing
			while ((ibanString = buffer.readLine()) != null ) {

				System.out.println(++i + ".");

				// We have to check first if the format is good. If not we skip the
				// rest of the checks completely and move on to the next one
				if (app.checkFormat(ibanString)) {

					IBAN ib = new IBAN(ibanString);

					System.out.println("\tChecking IBAN: " + ib.getIBAN());
					if (ib.checkIBAN()) {
						System.out.println("\tIBAN is good!");
					} else {
						System.out.println("\tIBAN is not valid. We are fixing it ...");
						ib.makeIBAN();
						System.out.println("\tIBAN fixed --> " + ib.getIBAN());
					}

					// Time to check if the user wants to store it in the output file
					boolean flag = false;
					while(!flag) {
						System.out.print("Would you like to store it in the output file? [y,n]:");
						String reply = sc.nextLine();

						switch(reply) {
							case "yes":
							case "y":
							case "YES":
							case "Y":
							case "Yes":
								flag = true;
								// Store to output file
								ibanOut.write(ib.getIBAN());
								ibanOut.newLine();
								break;
							case "no":
							case "n":
							case "NO":
							case "N":
							case "No":
								flag = true;
								break;
							default:
								System.out.println("Not a valid response!");
						}
					}
				} else {
					System.out.println("\tFormat is not good. Moving on to the next one ...");
					continue; // would happen anyway :p
				}
			}
			ibanOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
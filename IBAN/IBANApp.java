/**
  TODOs:
    1. 
**/

import java.io.*;
import java.util.Scanner;

public class IBANApp {

	public static void main(String[] args) {
		// This should be converted to be reading from a file
		IBAN ib = new IBAN("GR3902604290000780200174634");

		if (ib.checkIBAN()) {
			System.out.println("IBAN is good!");
		} else {
			System.out.println("IBAN is not valid. We are fixing it ...");
		}
	}
}
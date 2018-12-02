package problem;

import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class PatientReport {
	
	// Declare Class Variables
	private static String[][] patients = new String[1000][11];
	private static String[] prompts = {"Patient Last Name: ","Patient First Name: ", "Address Line 1: ",
			"Address Line 2: ", "City: ", "State: ", "Zip: ", "Zip + 4: ", "Amount Owed: $",
			"Payment Amount: $", "Payment Date (MM/dd/yyyy)"};
	private static Scanner input = new Scanner(System.in);
	
	// Declare Class Constants
	final static String DATE_FORMAT = "MM/dd/yyyy";
	final static int WIDTH = 180;
	
	// Main Method
	public static void main(String[] args) {
				
		// Get Patient Information
		getAllInfo();
		
		// Display Report
		displayAllInfo();
		
		
	}
	
	// Method that gets information from user on all patients
	private static void getAllInfo() {
		
		String keepGoing = "y";
		
		// Loop to get information for each patient
		// Up to 1000 patients, or user enters sentinel
				for(int i = 0; i < 1000; i++) {
					
					// Prompt user to enter new patient information
					System.out.println("Enter Patient " + (i + 1) + " Information:");
					
					// Get Information for patient i + 1
					patients[i] = getPatientInfo();
					
					// Prompt user to enter another patient, or finish input 
					System.out.print("Would you like to enter information for another patient? (y/n): ");
					keepGoing = input.nextLine();
					if(keepGoing.equals("n")) {
						if(keepGoing.equals("n")) {
							break;
						}
					}
					// If KeepGoing not equal to n or y prompt user to enter input again
					while(!keepGoing.equals("y")) {
						System.out.print("Exception: Input must be (y/n)\n" +
								"Would you like to enter information for another patient? (y/n): ");
						keepGoing = input.nextLine();
					}		
				}
		
	}
	
	// Method that gets information for one patient
	private static String[] getPatientInfo() {
		
		// Declare Variable
		double amountOwed = 0;
		
		// Create new patient info Array
		String[] patientInfo = new String[11];
		
		// Loop to get all fields of information for patient i + 1
		for(int j = 0; j<11; j++) {
			
			// Display Prompt And get Input
			System.out.print(prompts[j]);
			patientInfo[j] = input.nextLine();
			
			// Validate that input is not blank for all fields except
			// Address Line 2 & Zip + 4
			while(patientInfo[j].isEmpty()) {
				if(j == 3 || j == 7) {
					break;
				}
				else {
					System.out.print("Exception: Input cannot be blank.\n" +
							prompts[j]);
					patientInfo[j] = input.nextLine();
				}
			}
			
			// Switch with cases to validate each input
			switch(j) {
			// First name and Last Name
			case 0:
			case 1:
				// Validate that Name only contains letters
				while(patientInfo[j].matches(".*[^a-zA-Z]+.*")) {
					System.out.print("Exception: Input cannot contain numbers or symbols.\n" +
							prompts[j]);
					patientInfo[j] = input.nextLine();
				}
				break;
			// Address Line 1 and Address Line 2
			case 2:
			case 3:
				// Validate that Address fields only contain
				// numbers, letters, and allowed symbols
				while(patientInfo[j].matches(".*[^a-zA-Z0-9 #.,;:']+.*")) {
					System.out.print("Exception: Input may only contain letters, numbers, "
								+ "and the symbols \"# . , ; : '\"\n" + prompts[j]);
					patientInfo[j] = input.nextLine();
				}
				break;
			// City
			case 4:
				// Validate that City field only contains
				// letters, and allowed symbols
				while(patientInfo[j].matches(".*[^a-zA-Z -,.;'&/.()]+.*")) {
					System.out.print("Exception: Input may only contain letters, numbers, "
								+ "and the symbols \"-,.;'&/.()\"\n" + prompts[j]);
					patientInfo[j] = input.nextLine();
				}
				break;
			// State
			case 5:
				// Validate that State only contains 2 uppercase letters
				while(patientInfo[j].length() != 2 || patientInfo[j].matches(".*[^A-Z]+.*")) {
					System.out.print("Exception: Input may only contain two upper case letters: (IN)\n"
							+ prompts[j]);
					patientInfo[j] = input.nextLine();
				}
				break;
			// Zipcode
			case 6:
				// Validate that Zipcode only contains 5 numbers
				while(patientInfo[j].length() != 5 || patientInfo[j].matches(".*[^0-9]+.*")){
					System.out.print("Exception: Input may only contain five numbers: (46563)\n"
							+ prompts[j]);
					patientInfo[j] = input.nextLine();
				}
				break;
			// Zipcode + 4
			case 7:
				// Validate that Zipcode + 4 is either empty
				// or only contains four numbers
				while(patientInfo[j].length() !=4 || patientInfo[j].matches(".*[^0-9]+.*")){
					if(patientInfo[j].isEmpty()) {
						break;
					}
					System.out.print("Exception: Input may only contain four numbers: (4425)\n"
							+ prompts[j]);
					patientInfo[j] = input.nextLine();
				}
				break;
			// Amount Owed
			case 8:
				// Validate that Amount Owed is only a number
				// and only symbol is a '.'
				while(patientInfo[j].matches(".*[^0-9.]+.*")) {
					System.out.print("Exception: Input may only contain numbers: (44 or 108.21)\n"
							+ prompts[j]);
					patientInfo[j] = input.nextLine();
				}
				// Format Amount
				amountOwed = Double.parseDouble(patientInfo[j]);
				patientInfo[j] = String.format("$%4.2f", amountOwed);
				break;
			// Payment Amount
			case 9:
				// Validate that Payment amount is only a number
				// and only symbol is a '.' and that it is not greater
				// than Amount Owed.
				while(Double.parseDouble(patientInfo[j]) > amountOwed
						|| patientInfo[j].matches(".*[^0-9.]+.*")){
					if(patientInfo[j].matches(".*[^0-9.]+.*")){
						System.out.print("Exception: Input may only contain numbers: (44 or 108.21)\n"
								+ prompts[j]);
						patientInfo[j] = input.nextLine();
					}
					else {
						System.out.print("Exception: Input must be less than amount owed $" 
								+ patientInfo[j-1] + "\n" + prompts[j]);
						patientInfo[j] = input.nextLine();
					}
				}
				// Format Amount
				patientInfo[j] = String.format("$%4.2f", Double.parseDouble(patientInfo[j]));
				break;
			// Payment Date
			case 10:
				// Validate that Date is in correct format
				do {
					try {
						DateFormat df = new SimpleDateFormat(DATE_FORMAT);
						df.setLenient(false);
						df.parse(patientInfo[j]);
						break;
					}
					catch(ParseException ex) {
						System.out.print("Exception: Input must be in format (MM/dd/yyyy)\n" +
								prompts[j]);
						patientInfo[j] = input.nextLine();
					}
				} while(true);
				break;
			}
		}
		return patientInfo;
	}
	
	// Method that displays Table of patient information
	private static void displayAllInfo() {
		
		// Print Table Header
		setupHeader();
		
		// Print All Patients
		for(String[] patient : patients) {
			if(patient[0] != null) {
				printColumns(patient);
			}
		}
	}
	
	// Method that Displays Header
	private static void setupHeader() {
		
		String[] columnNames = {"Last","First", "Address Line 1","Address Line 2", "City",
				"State", "Zip", "Zip+4", "Amount Owed","Payment Amount", "Payment Date"};
		String divider = "=";
		String title = "XYZ Community Hospital";
		
		// Print Centered Title
		System.out.println("\n");
		for(int i = 0; i< (WIDTH - title.length())/2; i++){
			System.out.print(" ");
		}
		System.out.print(title + "\n\n");
		
		// Print First Divider
		for(int i = 0; i < WIDTH; i++) {
			System.out.print(divider);
		}
		System.out.println("\n");
		
		// Print Column Names
		printColumns(columnNames);

		// Print Final Divider
		System.out.println("\n");
		for(int i = 0; i < WIDTH; i++) {
			System.out.print(divider);
		}
		System.out.println("\n");
	}
	
	// Method that Formats column widths and displays each column
	private static void printColumns(String[] fields) {
		
		// Format each column based on how wide they need to be
		for(int i = 0; i < 11; i++) {
			switch(i) {
				//Last Name, First Name, and City
				case 0:
				case 1:
				case 4:
					System.out.format("%-15s", fields[i]);
					break;
				//Address Line 1 and Address Line 2
				case 2:
				case 3:
					System.out.format("%-25s", fields[i]);
					break;
				//State, Zip, Zip+4, 
				case 5:
				case 6:
				case 7:
					System.out.format("%-10s", fields[i]);
					break;
				//Amount Owed, Payment Amount, and Payment Date
				case 8:
				case 9:
				case 10:
					System.out.format("%-20s", fields[i]);
					break;
			}
		}
	}
}

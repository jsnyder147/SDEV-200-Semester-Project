package problem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Patient {
	
	private String id;
	private String firstName;
	private String lastName;
	private String addressOne;
	private String addressTwo = "";
	private String city;
	private String state;
	private String zip;
	private String zip4 = "";
	private String paymentDate;
	private String paymentAmount;
	private String amountOwed;
	private double doubleAmountOwed;
	private static ObservableList<Patient> patients = FXCollections.observableArrayList();
	final static String DATE_FORMAT = "MM/dd/yyyy";
	private String delimiter = ",";
	
	public Patient() {
		
	}
	
	public String getId() {

		return id;
	}

	public void setId(String id)  throws InputErrorException {
		if(id.isEmpty()) {
			throw new InputErrorException("Id is required.");
		} else {
			this.id = id;
		}
		
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws InputErrorException {
		if(lastName.isEmpty()) {
			throw new InputErrorException("Last name is required.");
		} else if(lastName.matches(".*[^a-zA-Z]+.*")) {
			throw new InputErrorException("Last name cannot contain numbers or symbols.");

		} else {
			this.lastName = lastName;
		}
		
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws InputErrorException {
		if(firstName.isEmpty()) {
			throw new InputErrorException("First name is required.");
		} else if(firstName.matches(".*[^a-zA-Z]+.*")) {
			throw new InputErrorException("First name cannot contain numbers or symbols.");

		} else {
			this.firstName = firstName;
		}

	}

	public String getAddressOne() {

		return addressOne;
	}

	public void setAddressOne(String addressOne) throws InputErrorException {
		if(addressOne.isEmpty()) {
			throw new InputErrorException("Address Line 1 is required.");
		}
		else if(addressOne.matches(".*[^a-zA-Z0-9 #.,;:']+.*")){
			throw new InputErrorException("Address Line 1 may only contain letters, numbers, and the symbols \"# . , ; : '\"");
		} else {
			this.addressOne = addressOne;
		}

	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) throws InputErrorException {
		if(addressTwo.matches(".*[^a-zA-Z0-9 #.,;:']+.*")){
			throw new InputErrorException("Address Line 2 may only contain letters, numbers, and the symbols \"# . , ; : '\"");
		} else {
			this.addressTwo = addressTwo;
		}
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) throws InputErrorException{
		if(city.isEmpty()) {
			throw new InputErrorException("City is required.");
		}
		else if(city.matches(".*[^a-zA-Z -,.;'&/.()]+.*")){
			throw new InputErrorException("City may only contain letters, numbers, and the symbols \"-,.;'&/.()\"");
		} else {
			this.city = city;
		}
	}

	public String getState() {
		return state;
	}

	public void setState(String state) throws InputErrorException {
		if(state.isEmpty()) {
			throw new InputErrorException("State is required.");
		}
		else if(state.length() != 2 || state.matches(".*[^A-Z]+.*")){
			throw new InputErrorException("State may only contain two upper case letters: (IN)");
		} else {
			this.state = state;
		}
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) throws InputErrorException {
		if(zip.isEmpty()) {
			throw new InputErrorException("Zip is required.");
		}
		else if(zip.length() != 5 || zip.matches(".*[^0-9]+.*")){
			throw new InputErrorException("Zip may only contain five numbers: (46563)");
		} else {
			this.zip = zip;
		}

	}

	public String getZip4() {
		return zip4;
	}

	public void setZip4(String zip4) throws InputErrorException {
		if(zip4.length() !=4 || zip4.matches(".*[^0-9]+.*")){
			if(zip4.isEmpty()) {
				this.zip4 = zip4;
			} else {
				throw new InputErrorException("Zip+4 may only contain four numbers: (4425)");
			}

		} else {
			this.zip4= zip4;
		}
	}
	
	public String getPaymentDate() {
		return paymentDate;
	}
	
	public void setPaymentDate(String paymentDate) throws InputErrorException {
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(paymentDate);
			this.paymentDate = paymentDate;
		} catch(ParseException ex) {
			throw new InputErrorException("Payment Date must be in format (MM/dd/yyyy)");
		}
	}
	
	public String getPaymentAmount() {
		return paymentAmount;
	}
	
	public void setPaymentAmount(String paymentAmount) throws InputErrorException {

		if(paymentAmount.isEmpty()) {
			throw new InputErrorException("Payment Amount is required.");
		}
		/*else if(Double.parseDouble(paymentAmount) > doubleAmountOwed) {
			throw new InputErrorException("Payment Amount must be less than amount owed.");
		} */
		else if(paymentAmount.matches(".*[^0-9.]+.*")){
			throw new InputErrorException("Payment Amount may only contain numbers: (44 or 108.21)");
		} else if(Double.parseDouble(paymentAmount) <0){
			throw new InputErrorException("Payment Amount cannot be negative.");
		} else {
			this.paymentAmount = String.format("$%4.2f", Double.parseDouble(paymentAmount));
		}
	}
	
	public String getAmountOwed() {
		return amountOwed;
	}
	
	public void setAmountOwed(String amountOwed) throws InputErrorException {
		/*if(amountOwed.isEmpty()) {
			throw new InputErrorException("Amount Owed is required.");
		}
		else if(amountOwed.matches(".*[^0-9. ]+.*")){
			throw new InputErrorException("Amount Owed may only contain numbers: (44 or 108.21)");
		} else { */
		if(amountOwed.isEmpty()) {
			throw new InputErrorException("Amount Owed is required.");
		} else {
			this.amountOwed = String.format("$%4.2f", Double.parseDouble(amountOwed));
		}
	}
	
	public static ObservableList<Patient> getPatients() {
		return patients;
	}
	
	public static void addPatient(Patient patient) {
		patients.add(patient);
	}
	
	public static void removePatient(Patient patient) {
		patients.remove(patient);
	}
	
	public static int getNumOfPatients() {
		return patients.size();
	}
	
	@Override
	public String toString() {
		return String.format("%-7s",id) + " " + String.format("%-15s",lastName)+ " " + String.format("%-10s",firstName) + " " +
				String.format("%-25s",addressOne) + " " + String.format("%-15s",addressTwo) + " " + 
				String.format("%-15s",city) + " " + String.format("%-5s",state) + " " + String.format("%-8s",zip) + " " +
				String.format("%-6s",zip4) + " " + String.format("%-15s",paymentDate) + " " + 
				String.format("%-15s",paymentAmount) + " " + String.format("%-15s",amountOwed) + "\n";
		
	}
	
	
	public String toString1() {
		return "Patient " + id + " " + lastName + ", " + firstName;
	}
	
	public String toStringAgain() {
		String strippedPayment = paymentAmount.replaceAll("[^0-9.]","");
		String strippedOwed = amountOwed.replaceAll("[^0-9.]","");
		return id + delimiter + lastName + delimiter + firstName + delimiter + addressOne + delimiter +
				addressTwo + delimiter + city + delimiter + state + delimiter + zip + delimiter +
				zip4 + delimiter + paymentDate + delimiter + strippedPayment + delimiter + strippedOwed;
	}
	
}

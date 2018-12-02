package problem;

public class TestPatient {
	
	private static Patient patientOne;
	private static Patient patientTwo;
	private static Patient patientThree;
	
	public static void main(String[] args) {
		patientOne= new Patient();
		patientTwo = new Patient();
		patientThree = new Patient();
		// Test setters
		testOne(patientOne, "12345", true);
		// Test Getters
		testTwo();
		// Test Empty Exceptions
		testThree();
		// Test Invalid input exceptions
		testFour();
		
		// Test List
		testOne(patientTwo, "54321", false);
		testOne(patientThree, "77777", false);
		testFive();
	}
	
	public static void testOne(Patient patient, String id, boolean showTest) {
		if(showTest) {System.out.println("\tTESTING SETTERS\n");}
		
		try {
		patient.setId(id);
		patient.setLastName("Snyder");
		patient.setFirstName("Jason");
		patient.setAddressOne("1234 Test St.");
		patient.setAddressTwo("Apt. 1");
		patient.setCity("Indianapolis");
		patient.setState("IN");
		patient.setZip("46264");
		patient.setZip4("1234");
		patient.setPaymentDate("10/08/2018");
		patient.setAmountOwed("1234.87");
		patient.setPaymentAmount("400");
		
		if(showTest) {System.out.println("\n\tTEST PASSED\n");}
	
		} catch(InputErrorException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void testTwo() {
		System.out.println("\n\n\tTESTING GETTERS\n");
		System.out.print("ID: " + patientOne.getId() + " ");
		System.out.print("Last Name: " + patientOne.getLastName() + " ");
		System.out.print("First Name: " + patientOne.getFirstName() + " ");
		System.out.print("Address Line 1: " + patientOne.getAddressOne() + " ");
		System.out.print("Address Line 2: " + patientOne.getAddressTwo() + " ");
		System.out.print("City: " + patientOne.getCity() + " ");
		System.out.print("State: " + patientOne.getState() + " ");
		System.out.print("Zip: " + patientOne.getZip() + " ");
		System.out.print("Zip+4: " + patientOne.getZip4() + " ");
		System.out.print("Payment Date: " + patientOne.getPaymentDate() + " ");
		System.out.print("Amount Owed: " + patientOne.getAmountOwed() + " ");
		System.out.print("Payment Amount: " + patientOne.getPaymentAmount() + " ");
		
		// Test toString
		System.out.println("\n" + patientOne);
	}
	
	public static void testThree() {
		System.out.println("\n\n\tTESTING EMPTY EXCEPTIONS\n");
		try {patientOne.setId("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setLastName("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setFirstName("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setAddressOne("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {
			patientOne.setAddressTwo("");
			System.out.println("Address Line Two doesn't have exception, it can be blank");
		} catch(InputErrorException ex) {
			System.out.println(ex.getMessage());
		}
		
		try {patientOne.setCity("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setState("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setZip("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {
			patientOne.setZip4("");
			System.out.println("Zip 4 doesn't have exception, it can be blank");
		} catch(InputErrorException ex) {
			System.out.println(ex.getMessage());
		}
		
		try {patientOne.setPaymentDate("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setAmountOwed("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setPaymentAmount("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}

		
	}
	
	public static void testFour() {
		System.out.println("\n\nTESTING INPUT VALIDATION EXCEPTIONS\n");
		
		try {patientOne.setId("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setLastName("J1son");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setFirstName("Sny#der");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setAddressOne("12345&*+} St.");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setAddressTwo("apt%$ 1");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setCity("Ind%apol8");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setState("indian");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setZip("%111111");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setZip4("1");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setPaymentDate("0000/01/00");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setAmountOwed("");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
		
		try {patientOne.setPaymentAmount("-500");} catch(InputErrorException ex) {System.out.println(ex.getMessage());}
	}
	
	public static void testFive() {
		System.out.println("\n\nTESTING ADDING PATIENTS\n");
		Patient.addPatient(patientOne);
		System.out.println("Patient Added!");
		Patient.addPatient(patientTwo);
		System.out.println("Patient Added!");
		Patient.addPatient(patientThree);
		System.out.println("Patient Added!");
		
		
		System.out.println("Num of Patients: " + Patient.getNumOfPatients());
		for(Patient aPatient : Patient.getPatients()) {
			System.out.println(aPatient.toString1());
		}
		System.out.println("\n\nTESTING Removing PATIENTS\n");
		Patient.removePatient(patientThree);
		System.out.println("Patient Removed!");
		System.out.println("Num of Patients: " + Patient.getNumOfPatients());
		for(Patient aPatient : Patient.getPatients()) {
			System.out.println(aPatient.toString1());
		}
		
	}

	
}

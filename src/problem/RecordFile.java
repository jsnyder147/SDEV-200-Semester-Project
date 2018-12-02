package problem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public abstract class RecordFile {
	private static boolean saved = false;
	private static boolean reportImported = false;
	
	private static Stage stage = MainMenu.getStage();
	//private static FileChooser fileChooser = new FileChooser();
	
	
	private static void importAnotherReport(FileChooser fileChooser) throws Exception{
		try {
    		ObservableList<Patient> patients = Patient.getPatients();
    		patients.removeAll(Patient.getPatients());
    		fileChooser.setTitle("Open Report File");
			getFile(fileChooser);
			reportImported = true;
			DisplayReport report = new DisplayReport();

    	}
    	catch(Exception ex) {
    		
    	}
	}
	
	public static void saveFile(File file) throws Exception {
		
		ObservableList<Patient> patients = Patient.getPatients();
		
		try(
				PrintWriter output = new PrintWriter(file);
			){
				for(Patient patient : patients) {
					output.println(patient.toStringAgain());
				}
				saved = true;

			} 
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				saved = false;
			} 
		
	}
	
	public static void getFile(FileChooser fileChooser) throws IOException{
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
		File file = fileChooser.showOpenDialog(stage); // new File("Untitled.txt");
		
		if(file != null) {
			System.out.println(file.getCanonicalPath());
			
			Scanner s = new Scanner(file).useDelimiter("[\\^\\n]");
			
			int count = 0;
			Patient patient = new Patient();
			// Read through file while it has next area to input
			while(s.hasNext()) {
				String line = s.next();
				if(count == 12) {
					count = 0;
					System.out.println(patient);
					Patient.addPatient(patient);
					patient = new Patient();
				} 
				if(line.equals("")) {
					count++;
					continue;
				}
				
				
				try {
					switch(count) {
						case 0:
							patient.setId(line);
							break;
						case 1:
							patient.setLastName(line);
							break;
						case 2:
							patient.setFirstName(line);
							break;
						case 3:
							patient.setAddressOne(line);
							break;
						case 4:
							patient.setAddressTwo(line);
							break;
						case 5:
							patient.setCity(line);
							break;
						case 6:
							patient.setState(line);
							break;
						case 7:
							patient.setZip(line);
							break;
						case 8:
							patient.setZip4(line);
							break;
						case 9:
							patient.setPaymentDate(line);
							break;
						case 10:
							patient.setPaymentAmount(line);
							break;
						case 11:
							System.out.println(line);
							patient.setAmountOwed(line);
							break;
					}
					
				} catch (InputErrorException ex) {
					System.out.println(ex);
				}
	
				count ++;
				
				if(count == 12 && !s.hasNext()) {
					System.out.println(patient);
					Patient.addPatient(patient);
				}
			}
			
			reportImported = true;
		
		} 
	}
	
	
	public static void confirmImport() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Import Report");
		alert.setHeaderText("A report is already imported.");
		alert.setContentText("Save current report before importing another report, or changes will be lost.");
		
		ButtonType btpSave = new ButtonType("Save");
		ButtonType btpImport = new ButtonType("Import");
		ButtonType btpCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(btpSave, btpImport, btpCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		FileChooser fileChooser = new FileChooser();
		// If Save option
		if(result.get() == btpSave) {
			
            fileChooser.setTitle("Save Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
            	try {
            		saveFile(file);
            		importAnotherReport(fileChooser);

            	}
            	catch(Exception ex) {
            		
            	} 
            }
		
		}
		// If Import option
		else if(result.get() == btpImport) {
			try {
				importAnotherReport(fileChooser);
			} catch(Exception e) {
				
			}
		}
		// If Cancel option
		else {
			
		}
		
		
	}
	
	public static boolean isSaved() {
		return saved;
	}
	
	public static boolean isReportImported() {
		return reportImported;
	}
	
}

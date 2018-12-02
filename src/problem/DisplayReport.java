 package problem;


import problem.Patient;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class DisplayReport {
	
	private Stage stage = AddPatient.getStage();
	private Scene addPatientScene = AddPatient.getScene();
	private Scene mainMenuScene = MainMenu.getScene();
	private Scene reportScene;
	private TableView table = new TableView();
	private ArrayList<TableColumn> columns = new ArrayList<>();
	private boolean saved = false;
	private ObservableList<Patient> patients;
	
	public DisplayReport() {
		BorderPane pane = new BorderPane();
		FlowPane buttonPane = new FlowPane();
		
		// Add Buttons
		Button addPatient = new Button("Add Patient");
		Button saveReport = new Button("Save Report");
		Button mainMenu = new Button("Main Menu");
		Button importReport = new Button("Import Report");
		
		// Create Separators
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.getStyleClass().add("seper");
		Separator sep2 = new Separator();
		sep2.setOrientation(Orientation.VERTICAL);
		sep2.getStyleClass().add("seper");
		Separator sep3 = new Separator();
		sep3.setOrientation(Orientation.VERTICAL);
		sep3.getStyleClass().add("seper");
		
		// Set Buttons
		buttonPane.getChildren().addAll(mainMenu, sep, importReport, sep2, addPatient, sep3, saveReport);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setHgap(20);
		buttonPane.setPadding(new Insets(15,0,10,0));
		buttonPane.getStyleClass().add("mainButtons");
		
		// Add ButtonPane to borderPane
		pane.setTop(buttonPane);
		Label error = new Label("");
		
		// Add Columns to table
		setColumns();
		table.getColumns().addAll(columns);
		table.setEditable(false);
		placeData();
		table.setItems(Patient.getPatients());
		pane.setCenter(table);
		
		
		// Add BorderPane to scene and style
		reportScene = new Scene(pane, 1100, 400);
		reportScene.getStylesheets().add("/problem/patientStyle.css");
		pane.getStyleClass().add("infoPane1");
		BorderPane.setAlignment(table, Pos.CENTER);
		
		// Event Listeners
		addPatient.setOnMouseClicked(e -> {
			if(addPatientScene == null) {
				AddPatient add = new AddPatient();
			} else {
				stage.setScene(addPatientScene);
				stage.centerOnScreen();
			}
		});
		
		mainMenu.setOnMouseClicked(ev -> {
			stage.setScene(mainMenuScene);
			stage.centerOnScreen();
		});
		
		importReport.setOnMouseClicked(eve ->{
			if(MainMenu.getIsReportImported()) {
				String toastMessage = "Report Already Imported";
				int toastMessageTime = 2000;
				int fadeInTime = 500;
				int fadeOutTime = 500;
				Toast.makeText(stage, toastMessage, toastMessageTime, fadeInTime, fadeOutTime);
				
			} else {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Report File");

				try {
					MainMenu.getFile(fileChooser);
					MainMenu.setIsReportImported(true);
				} catch (IOException e1) {
					
				}
			}

		});
		
		saveReport.setOnMouseClicked(event -> {
			FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
            	try {
            		saveFile(file);
            	}
            	catch(Exception ex) {
            		
            	}
			/*try {
			showSaveDialog();
			} catch(Exception ex) {
				
			}*/
            }
		});
		
		stage.setScene(reportScene);
		stage.centerOnScreen();
	}
	
	private void setColumns() {
		columns.add(new TableColumn("Id"));
		columns.add(new TableColumn("Last Name"));
		columns.add(new TableColumn("First Name"));
		columns.add(new TableColumn("Address Line One"));
		columns.add(new TableColumn("Address Line Two"));
		columns.add(new TableColumn("City")); 
		columns.add(new TableColumn("State"));
		columns.add(new TableColumn("Zip"));
		columns.add(new TableColumn("Zip+4"));
		columns.add(new TableColumn("Payment Date"));
		columns.add(new TableColumn("Payment Amount"));
		columns.add(new TableColumn("Amount Owed"));
	}
	
	private void placeData() {
		columns.get(0).setCellValueFactory(new PropertyValueFactory<Patient, String>("id"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<Patient, String>("lastName"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<Patient, String>("addressOne"));
		columns.get(4).setCellValueFactory(new PropertyValueFactory<Patient, String>("addressTwo"));
		columns.get(5).setCellValueFactory(new PropertyValueFactory<Patient, String>("city"));
		columns.get(6).setCellValueFactory(new PropertyValueFactory<Patient, String>("state"));
		columns.get(7).setCellValueFactory(new PropertyValueFactory<Patient, String>("zip"));
		columns.get(8).setCellValueFactory(new PropertyValueFactory<Patient, String>("zip4"));
		columns.get(9).setCellValueFactory(new PropertyValueFactory<Patient, String>("paymentDate"));
		columns.get(10).setCellValueFactory(new PropertyValueFactory<Patient, String>("paymentAmount"));
		columns.get(11).setCellValueFactory(new PropertyValueFactory<Patient, String>("amountOwed")); 
	}
	
	private void saveFile(File file) throws Exception {
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
			} 
		
	}
	

	
	private void showSaveDialog() throws Exception{
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Save Report");
		dialog.setHeaderText("Please input location to save file (including path)");
		dialog.setContentText("");
		while(!saved) {
			Optional<String> result = dialog.showAndWait();
			
	
			result.ifPresent(name ->{
			
					String path = result.get();
					File file = new File(path);
					try(
						PrintWriter output = new PrintWriter(file);
					){
						output.print(createHeader());
						for(Patient patient : Patient.getPatients()) {
							output.print(patient);
						}
						saved = true;
	
					} 
					catch (FileNotFoundException e) {
					} 
						
			});
			if(saved) {
				break;
			} else {
				dialog.setHeaderText("Error saving file. Please try a different file name/path.");
				
			}
		
		}
	} 

	
	
	private String createHeader() {
		return String.format("%-7s", "Id") + " " + String.format("%-15s","Last Name")+ " " + String.format("%-10s","First Name") + " " +
		String.format("%-25s","Address Line 1") + " " + String.format("%-15s","Address Line 2") + " " + 
		String.format("%-15s","City") + " " + String.format("%-5s","State") + " " + String.format("%-8s","Zip") + " " +
		String.format("%-6s","Zip+4") + " " + String.format("%-15s","Payment Date") + " " + 
		String.format("%-15s","Payment Amount") + " " + String.format("%-15s","Amount Owed") + "\n\n";
	}
	
}

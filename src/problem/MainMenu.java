 package problem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class MainMenu extends Application{

	private static Stage stage;
	private static Scene mainScene;
	private String fileName = "/problem/hospital.txt";
	private static Boolean isReportImported = false;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		stage.setResizable(false);
		
		FlowPane pane = new FlowPane();
		BorderPane border = new BorderPane();
		border.setPadding(new Insets(0,150,0,150));
		
		
		// Create Title
		Label title = new Label("Welcome to Patient Reports");
		title.getStyleClass().add("tileDisplay");
		
		border.setTop(title);
		BorderPane.setAlignment(title,Pos.CENTER);
		
		// Create Buttons
		Button addPatient = new Button("Add Patient");
		addPatient.getStyleClass().add("mainButtons");
		Button importReport = new Button("Import Report");
		importReport.getStyleClass().add("mainButtons");
		Button viewReport = new Button("View Report");
		viewReport.getStyleClass().add("mainButtons");
		
		// Style Pane
		pane.getChildren().addAll(addPatient,importReport, viewReport);
		pane.setOrientation(Orientation.VERTICAL);
		pane.setPadding(new Insets(40,0,0,0));
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setVgap(20);
		border.setCenter(pane);
		BorderPane.setAlignment(pane, Pos.CENTER);
		
		// Event Handlers
		addPatient.setOnMouseClicked(e ->{
			AddPatient report = new AddPatient();
		});
		
		importReport.setOnMouseClicked(ev ->{
			if(isReportImported) {
				DisplayReport report = new DisplayReport();
			} else {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				try { 
					getFile(fileChooser);
					isReportImported = true;
					DisplayReport report = new DisplayReport();
				} catch (IOException ex) {
					System.out.println(ex);
				}
			}
			
		});
		
		viewReport.setOnMouseClicked(eve -> {
			DisplayReport report = new DisplayReport();
		});
		
		// Set Scene and Stage
		mainScene = new Scene(border, 700, 400);
		mainScene.getStylesheets().add("/problem/patientStyle.css");
		border.getStyleClass().add("infoPane1");
		primaryStage.setTitle("Patient Report");
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
	}
	
	public static void getFile(FileChooser fileChooser) throws IOException{
		 fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
		File file = fileChooser.showOpenDialog(stage); // new File("Untitled.txt");
		
		if(file != null) {
			System.out.println(file.getCanonicalPath());
			//^
		Scanner s = new Scanner(file).useDelimiter("[\\,\\n]");
		
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
		}
		
		System.out.println(Patient.getNumOfPatients());
		for(Patient patient1 : Patient.getPatients()) {
			System.out.println(patient1);
		}
		
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public static Boolean getIsReportImported() {
		return isReportImported;
	}
	
	public static void setIsReportImported(boolean imported) {
		isReportImported = imported;
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static Scene getScene() {
		return mainScene;
	}
	
}

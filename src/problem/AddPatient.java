package problem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;




public class AddPatient {
	
	private static Stage primaryStage = MainMenu.getStage();
	protected static Scene mainScene = MainMenu.getScene();
	protected static Scene patientScene;
	private TextField tfId = new TextField();
	private TextField tfLastName = new TextField();
	private TextField tfFirstName = new TextField();
	private TextField tfAddressOne = new TextField();
	private TextField tfAddressTwo = new TextField();
	private TextField tfCity = new TextField();
	private TextField tfState = new TextField();
	private TextField tfZip = new TextField();
	private TextField tfZip4 = new TextField();
	private TextField tfPaymentDate = new TextField();
	private TextField tfPaymentAmount = new TextField();
	private TextField tfAmountOwed = new TextField();
	private Label error = new Label("");
	
	
	public AddPatient() {
		setUpGUI();
		clearText();
	}
	
	private void setUpGUI() {
		// Create Panes
		BorderPane pane = new BorderPane();
		GridPane infoPaneLeft = new GridPane();
		GridPane infoPaneRight = new GridPane();
		FlowPane infoPane = new FlowPane();
		FlowPane buttonPane = new FlowPane();
		
		// Set Pane Attributes
		infoPane.setHgap(20);
		infoPane.setPadding(new Insets(30,40,15,40));
		infoPaneLeft.setAlignment(Pos.CENTER);
		infoPaneLeft.setHgap(10);
		infoPaneLeft.setVgap(10);
		infoPaneRight.setAlignment(Pos.CENTER);
		infoPaneRight.setHgap(10);
		infoPaneRight.setVgap(10);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setHgap(20);
		buttonPane.setPadding(new Insets(15,0,10,0));
		
		// Create Buttons
		Button viewReport = new Button("View Report");
		Button savePatient = new Button("Save Patient");
		Button mainMenu = new Button("Main Menu");
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.getStyleClass().add("seper");
		Separator sep2 = new Separator();
		sep2.setOrientation(Orientation.VERTICAL);
		sep2.getStyleClass().add("seper");
		// Add Buttons and buttonPane to borderPane
		buttonPane.getChildren().addAll(mainMenu, sep, viewReport,sep2, savePatient);
		buttonPane.getStyleClass().add("mainButtons");
		pane.setTop(buttonPane);
		pane.setAlignment(buttonPane, Pos.CENTER);
		
		error.setPadding(new Insets(10,0,20,0));

		// Set Placeholder for payment date
		tfPaymentDate.setPromptText("MM/DD/YYYY");
		
		// Add Text Fields To panes
		infoPaneLeft.addRow(0, new Label("ID:"), tfId);
		infoPaneLeft.addRow(1, new Label("Last Name:"), tfLastName);
		infoPaneLeft.addRow(2, new Label("First Name:"), tfFirstName);
		infoPaneLeft.addRow(3, new Label("Payment Date:"), tfPaymentDate);
		infoPaneLeft.addRow(4, new Label("Payment Amount:"), tfPaymentAmount);
		infoPaneLeft.addRow(5, new Label("Amount Owed:"), tfAmountOwed);
		infoPaneRight.addRow(0, new Label("Address Line 1:"), tfAddressOne);
		infoPaneRight.addRow(1, new Label("Address Line 2:"), tfAddressTwo);
		infoPaneRight.addRow(2, new Label("City:"), tfCity);
		infoPaneRight.addRow(3, new Label("State:"), tfState);
		infoPaneRight.addRow(4, new Label("Zip:"), tfZip);
		infoPaneRight.addRow(5, new Label("Zip + 4:"), tfZip4);

		
		// Add Columns to infoPane and infoPane to center of BorderPane
		infoPane.getChildren().addAll(infoPaneLeft, infoPaneRight);
		pane.setCenter(infoPane);
		pane.setAlignment(infoPane, Pos.CENTER);
		pane.setBottom(error);
		pane.setAlignment(error, Pos.CENTER);
		
		patientScene = new Scene(pane, 700, 400);
		patientScene.getStylesheets().add("/problem/patientStyle.css");
		
		pane.getStyleClass().add("infoPane1");
		error.getStyleClass().add("errorMessage");
		
		// Button event handlers
		viewReport.setOnMouseClicked(ent -> {
			System.out.println("View Report Clicked");
			DisplayReport report = new DisplayReport();
		});
		
		savePatient.setOnMouseClicked(ev ->{
			addPatient();
		});
		
		mainMenu.setOnMouseClicked(event -> {
			primaryStage.setScene(mainScene);
		});
		
		primaryStage.setScene(patientScene);
		primaryStage.centerOnScreen();

				
	}

	
	public static Stage getStage() {
		return primaryStage;
	}
	
	public static Scene getScene() {
		return patientScene;
	}
	
	private void addPatient() {
		Patient patient = new Patient();
		error.setText("");
		try {
			// Set Patient info
			patient.setId(tfId.getText());
			patient.setLastName(tfLastName.getText());
			patient.setFirstName(tfFirstName.getText());
			patient.setAddressOne(tfAddressOne.getText());
			patient.setAddressTwo(tfAddressTwo.getText());
			patient.setCity(tfCity.getText());
			patient.setState(tfState.getText());
			patient.setZip(tfZip.getText());
			patient.setZip4(tfZip4.getText());
			patient.setPaymentDate(tfPaymentDate.getText());
			patient.setAmountOwed(tfAmountOwed.getText());
			patient.setPaymentAmount(tfPaymentAmount.getText());
			
			// Add patient to patients list
			Patient.addPatient(patient);
			
			// Display message that patient is saved
			String toastMessage = "Patient Record Saved.";
			int toastMessageTime = 2000;
			int fadeInTime = 500;
			int fadeOutTime = 500;
			Toast.makeText(primaryStage, toastMessage, toastMessageTime, fadeInTime, fadeOutTime);
			clearText();
			
			for(Patient patient1 : Patient.getPatients()) {
				System.out.println(patient);
			}
			
			System.out.println(Patient.getNumOfPatients());
		} catch(InputErrorException ex) {
			error.setText(ex.getMessage());
		}
	}
	
	private void clearText() {
		tfId.setText("");
		tfLastName.setText("");
		tfFirstName.setText("");
		tfAddressOne.setText("");
		tfAddressTwo.setText("");
		tfCity.setText("");
		tfState.setText("");
		tfZip.setText("");
		tfZip4.setText("");
		tfPaymentDate.setText("");
		tfAmountOwed.setText("");
		tfPaymentAmount.setText("");
	}

	
}

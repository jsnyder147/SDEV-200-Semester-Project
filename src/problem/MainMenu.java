 package problem;

import java.io.IOException;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class MainMenu extends Application{

	private static Stage stage;
	private static Scene mainScene;

	
	
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
			if(RecordFile.isReportImported()) {
				RecordFile.confirmImport();
			} else {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Report File");
				try { 
					RecordFile.getFile(fileChooser);
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
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static Scene getScene() {
		return mainScene;
	}
	
}

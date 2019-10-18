package view;

import controller.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ImportData {
	
	
	public void start(Stage primaryStage) {
		try {
			FlexRent flexrent;
		    flexrent = new FlexRent();
			MenuBar menuBar = new MenuBar();
			Menu fileMenu = new Menu("File");
			MenuItem importMenuItem = new MenuItem("Import data");
			importMenuItem.setOnAction(e -> {	
				ImportData impdata= new ImportData();
				 impdata.start(primaryStage);


			});
			MenuItem exportMenuItem = new MenuItem("Export data");
			exportMenuItem.setOnAction(e -> {
				ExportData expdata= new ExportData();
				expdata.start(primaryStage);
			});
			MenuItem exitMenuItem = new MenuItem("Exit");
			exitMenuItem.setOnAction(e -> {
				primaryStage.close();
			});
			fileMenu.getItems().addAll(importMenuItem, exportMenuItem, exitMenuItem);
			Menu propertyMenu = new Menu("Property");
			MenuItem addMenuItem = new MenuItem("Add Property");
			addMenuItem.setOnAction(e -> {
				addProperty addproperty = new addProperty();
				addproperty.start(primaryStage);

			});
			MenuItem rentMenuItem = new MenuItem("Rent Property");
			rentMenuItem.setOnAction(e -> {
				new Rent(flexrent);
				primaryStage.close();
				
			});
			MenuItem returnMenuItem = new MenuItem("Return Property");
			returnMenuItem.setOnAction(e -> {
				new Return(flexrent);
				primaryStage.close();

			});
			propertyMenu.getItems().addAll(addMenuItem, rentMenuItem, returnMenuItem);
			Menu maintenanceMenu = new Menu("Maintenance");
			MenuItem pmMenuItem = new MenuItem("Property Maintenance");
			pmMenuItem.setOnAction(e -> {
				new Maintenance(flexrent);
				primaryStage.close();

			});
			MenuItem cmMenuItem = new MenuItem("Complete Maintenance");
			cmMenuItem.setOnAction(e -> {
				new CompleteMain(flexrent);
				primaryStage.close();

			});
			maintenanceMenu.getItems().addAll(pmMenuItem, cmMenuItem);
			Menu homeMenu = new Menu("Home");
			
			MenuItem backMenuItem = new MenuItem("Back to home");
			backMenuItem.setOnAction(e -> {
				Stage newstage = new Stage();			
				MainWindow home = new MainWindow();
				home.start(newstage);
				primaryStage.close();

			});
			homeMenu.getItems().add(backMenuItem);
			menuBar.getMenus().addAll(fileMenu, propertyMenu, maintenanceMenu,homeMenu);

			
			/*
			 * 
			 * 
			 * */
			VBox vbox=new VBox();
		
		BorderPane root = new BorderPane();
		
		Button button = new Button("Choose file to Import Data");
	
		
		button.setOnAction(new ImportDataListener(primaryStage));
		
		root.setCenter(button);
		
		vbox.getChildren().addAll(menuBar,root);
		Scene scene = new Scene(vbox);
		primaryStage.setTitle("Import Data"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		}
		catch(DBException DBE) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText(null);
			alert.setContentText(DBE.getMessage());
			alert.showAndWait();
		}
	}
}

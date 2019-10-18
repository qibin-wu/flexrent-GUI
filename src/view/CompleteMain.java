package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import controller.*;

public class CompleteMain {
	
	public CompleteMain(FlexRent flexrent) {
		Stage primaryStage = new Stage();
		
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
		VBox root=new VBox();
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		Label cDate = new Label("Complete Maintenance (dd/mm/yyyy)");
		TextField cDateText = new TextField();
		
		
		Label PID = new Label("Property ID: ");
		TextField PIDText = new TextField();		
		ComboBox<String> typeCbo = new ComboBox<>();
		typeCbo.getItems().addAll("Apartment", "Premium Suite");
		typeCbo.setValue(" ");
		typeCbo.setEditable(false);
		typeCbo.setOnAction(e -> {
			if (typeCbo.getValue() == "Premium Suite") {
				
				cDate.setVisible(true);
				cDateText.setVisible(true);
			} else {
				
				cDate.setVisible(false);
				cDateText.setVisible(false);
				
			}

		});	
		pane.add(PID, 0, 0);
		pane.add(PIDText, 1, 0);
		pane.add(cDate, 0, 1);
		pane.add(cDateText, 1, 1);
		Button btnSummit = new Button("Summit");
		btnSummit.setOnAction(e -> {
			
			Property property;
			
			try {
			
			property=getData.getPropertyByID(flexrent, PIDText.getText().trim());
			
			
			if (property instanceof PremiumSuite)
			{
				flexrent.completeMaintenance(property,  cDate.getText());
			}
			else
			{
				flexrent.completeMaintenance(property);
			}
			
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Perform Maintenance successfully");
			alert.setHeaderText(null);
			alert.setContentText(property.getProType() + " " + property.getPid() + " has all maintenance completed and ready for rent ");
			alert.showAndWait();		
			
			Stage newstage = new Stage();
			MainWindow home = new MainWindow();
			home.start(newstage);
			primaryStage.close();
			}catch (InvalidIdException idE) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(null);
				alert.setContentText(idE.getErrMsg());
				alert.showAndWait();
			} 	
			catch (completeMaintenanceException pmE) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(null);
				alert.setContentText(pmE.getErrMsg());
				alert.showAndWait();
			 }
			catch (DBException DBE) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(null);
				alert.setContentText(DBE.getErrMsg());
				alert.showAndWait();
			} catch (Exception ee)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(null);
				alert.setContentText(ee.getMessage());
				alert.showAndWait();
			}
				
		

		});

		pane.add(btnSummit, 1, 2);		
		root.getChildren().addAll(menuBar,pane);
		Scene scene = new Scene(root, 1280, 720);
		primaryStage.setTitle("Complete Maintenance");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public CompleteMain(FlexRent flexrent,Property property) {
		Stage primaryStage = new Stage();
		
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
		VBox root=new VBox();
		
		GridPane pane = new GridPane();
		
		if (property instanceof PremiumSuite) {
			
			
			pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane.setHgap(5.5);
			pane.setVgap(5.5);

			Label PID, cDate;
			TextField PIDText, cDateText;
			PID = new Label("Property ID:");
			cDate = new Label("Complete Maintenance (dd/mm/yyyy)");

			PIDText = new TextField();
			cDateText = new TextField();

			PIDText.setText(property.getPid());
			PIDText.setDisable(true);

			pane.add(PID, 0, 0);
			pane.add(PIDText, 1, 0);
			pane.add(cDate, 0, 1);
			pane.add(cDateText, 1, 1);

			Button btnSummit = new Button("Summit");
			btnSummit.setOnAction(e -> {
				
				try {			
						flexrent.completeMaintenance(property,  cDateText.getText());					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Perform Maintenance successfully");
					alert.setHeaderText(null);
					alert.setContentText(property.getProType() + " " + property.getPid() + " has all maintenance completed and ready for rent ");
					alert.showAndWait();
					
					Stage newstage = new Stage();
					MainWindow home = new MainWindow();
					home.start(newstage);
					primaryStage.close();
					}
					catch (completeMaintenanceException pmE) {

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText(null);
						alert.setContentText(pmE.getErrMsg());
						alert.showAndWait();
					 }
					catch (DBException DBE) {

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText(null);
						alert.setContentText(DBE.getErrMsg());
						alert.showAndWait();
					} catch (Exception ee)
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText(null);
						alert.setContentText(ee.getMessage());
						alert.showAndWait();
					}
						

			});

			pane.add(btnSummit, 1, 2);
			
		}
		else
		{
			
			pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane.setHgap(5.5);
			pane.setVgap(5.5);

			Label PID;
			TextField PIDText;
			PID = new Label("Property ID:");
			PIDText = new TextField();
			
			PIDText.setText(property.getPid());
			PIDText.setDisable(true);

			pane.add(PID, 0, 0);
			pane.add(PIDText, 1, 0);		
			

			Button btnSummit = new Button("Summit");
			btnSummit.setOnAction(e -> {
				
				try {							
					flexrent.completeMaintenance(property);	
					
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Perform Maintenance successfully");
					alert.setHeaderText(null);
					alert.setContentText(property.getProType() + " " + property.getPid() + " has all maintenance completed and ready for rent ");
					alert.showAndWait();			
					}					
					catch (DBException DBE) {

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText(null);
						alert.setContentText(DBE.getErrMsg());
						alert.showAndWait();
					} catch (Exception ee)
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText(null);
						alert.setContentText(ee.getMessage());
						alert.showAndWait();
					}
						

			});

			pane.add(btnSummit, 1, 1);
		}
		
		
		
root.getChildren().addAll(menuBar,pane);
		Scene scene = new Scene(root, 1280, 720);
		primaryStage.setTitle("Complete Maintenance");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}

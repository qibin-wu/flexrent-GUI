package view;

import controller.DBException;
import controller.FlexRent;
import controller.addPropertyException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class addProperty extends Application {

	String type, streetName, suburb, latestMainDay,desciption;
	int streetNum, bedNum;

	@Override
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
		VBox root=new VBox();
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Label streetNumLabel = new Label("Street number ");
		TextField streetNumTF = new TextField();
		Label streetNameLabel = new Label("Street name ");
		TextField streetNameTF = new TextField();
		Label suburbLabel = new Label("Suburb ");
		TextField suburbTF = new TextField();
		Label bedNumLabel = new Label("Bedroom numbers ");
		ComboBox<String> bedNumCbo = new ComboBox<>();
		bedNumCbo.getItems().addAll("1", "2", "3");
		bedNumCbo.setValue(" ");
		bedNumCbo.setEditable(false);
		Label latestMainDayLabel = new Label("last maintenance day (dd/mm/yyyy):");
		TextField latestMainDayTF = new TextField();
		latestMainDayLabel.setVisible(false);
		latestMainDayTF.setVisible(false);
		Label typeLabel = new Label("Property type ");
		ComboBox<String> typeCbo = new ComboBox<>();
		typeCbo.getItems().addAll("Apartment", "Premium Suite");
		typeCbo.setValue(" ");
		typeCbo.setEditable(false);
		typeCbo.setOnAction(e -> {
			if (typeCbo.getValue() == "Premium Suite") {
				bedNumCbo.setValue("3");
				bedNumCbo.setDisable(true);
				latestMainDayLabel.setVisible(true);
				latestMainDayTF.setVisible(true);
			} else {
				bedNumCbo.setDisable(false);
				bedNumCbo.setValue(" ");
				bedNumCbo.setEditable(true);
				latestMainDayLabel.setVisible(false);
				latestMainDayTF.setVisible(false);
				latestMainDayTF.setText("none");
			}

		});
		Label descipLabel=new Label("description");
		TextArea descipTextArea = new TextArea();
		descipTextArea.setPrefWidth(300);
		descipTextArea.setWrapText(true);


		// Place nodes in the pane
		pane.add(typeLabel, 0, 0);
		pane.add(typeCbo, 1, 0);
		pane.add(streetNumLabel, 0, 1);
		pane.add(streetNumTF, 1, 1);
		pane.add(streetNameLabel, 0, 2);
		pane.add(streetNameTF, 1, 2);
		pane.add(suburbLabel, 0, 3);
		pane.add(suburbTF, 1, 3);
		pane.add(bedNumLabel, 0, 4);
		pane.add(bedNumCbo, 1, 4);
		pane.add(latestMainDayLabel, 0, 5);
		pane.add(latestMainDayTF, 1, 5);
		pane.add(descipLabel, 0, 6);
		pane.add(descipTextArea, 1, 6);

		Button btSummit = new Button("Summit");
		btSummit.setOnAction(e -> {

			type = typeCbo.getValue();
			streetName = streetNameTF.getText();
			suburb = suburbTF.getText();
			latestMainDay = latestMainDayTF.getText();
			desciption= descipTextArea.getText();

			streetNum = Integer.parseInt(streetNumTF.getText());
			bedNum = Integer.parseInt(bedNumCbo.getValue());
			
			try {
			this.add(type,streetName,suburb,latestMainDay, streetNum, bedNum,desciption);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Add property successfully!");
			alert.showAndWait();
			
			Stage newstage = new Stage();
			MainWindow home = new MainWindow();
			home.start(newstage);
			primaryStage.close();
			
			}
			catch(addPropertyException aPE) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(null);
				alert.setContentText(aPE.getMessage());
				alert.showAndWait();
			}
			catch(DBException DBE) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(null);
				alert.setContentText(DBE.getMessage());
				alert.showAndWait();
			}
			
		
			
			

		});
		pane.add(btSummit, 1, 7);

		root.getChildren().addAll(menuBar,pane);
		// Create a scene and place it in the stage
		Scene scene = new Scene(root,1280,720);
		primaryStage.setTitle("Add Property");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		} catch( DBException DBE) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText(null);
			alert.setContentText(DBE.getMessage());
			alert.showAndWait();
			
		}
	}

	private  void add(String type,String streetName,String suburb,String latestMainDay,int streetNum,int bedNum,String description) throws addPropertyException, DBException {

		FlexRent flexrent = new FlexRent();
		flexrent.addProperty(type,streetName,suburb,latestMainDay, streetNum, bedNum,description);
	}



}

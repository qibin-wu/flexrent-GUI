package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.LinkedList;
import model.*;
import controller.*;

public class MainWindow extends Application {

	private LinkedList<Property> tempProlist;
	private String typeVa, bednumVa, statusVa;
	private boolean firstRun = true;

	@Override
	public void start(Stage primaryStage) {
		try {
		
		FlexRent flexrent = initFlexRent();
		tempProlist = flexrent.getProperty();
		VBox root = new VBox();
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
		 * */
		HBox filter = new HBox();
		filter.setPadding(new Insets(15, 12, 15, 12));
		filter.setSpacing(10);
		Text typeText = new Text("Type: ");
		typeText.setFont(Font.font("Helvetica", FontWeight.NORMAL, 16));
		Text numOfBedText = new Text("Number of bedrooms: ");
		numOfBedText.setFont(Font.font("Helvetica", FontWeight.NORMAL, 16));
		Text statusText = new Text("Status: ");
		statusText.setFont(Font.font("Helvetica", FontWeight.NORMAL, 16));
		ComboBox<String> typeCbo = new ComboBox<>();
		typeCbo.getItems().addAll("ALL", "Apartment", "Premium Suite");
		ComboBox<String> numOfBedCbo = new ComboBox<>();
		numOfBedCbo.getItems().addAll("ALL", "1", "2", "3");
		ComboBox<String> statusCbo = new ComboBox<>();
		statusCbo.getItems().addAll("ALL", "Available", "Rented", "Maintenance");
		if (firstRun) {
			typeCbo.setValue("ALL");
			numOfBedCbo.setValue("ALL");
			statusCbo.setValue("ALL");
		} else {
			typeCbo.setValue(typeVa);
			numOfBedCbo.setValue(bednumVa);
			statusCbo.setValue(statusVa);
		}
		Button searchBtn = new Button("Search");
		searchBtn.setPrefSize(100, 20);
		searchBtn.setOnAction(e -> {
			typeVa = typeCbo.getValue();
			bednumVa = numOfBedCbo.getValue();
			statusVa = statusCbo.getValue();
			firstRun = false;
			this.start(primaryStage);
		});
		filter.getChildren().addAll(typeText, typeCbo, numOfBedText, numOfBedCbo, statusText, statusCbo, searchBtn);
		
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * */
		
		
		HBox proList = new HBox();
		proList.setPadding(new Insets(15, 12, 15, 12));
		proList.setSpacing(30);
		VBox leftlist = new VBox();
		VBox rightlist = new VBox();
		leftlist.setSpacing(30);
		rightlist.setSpacing(30);		
		int i;
		for (i = 0; i < tempProlist.size(); i++) {
			if (i % 2 == 0) {
				Property tempProperty = tempProlist.get(i);
				VBox item = new VBox();
				String detail, sname;			
				Image image;				
				image = new Image("file:" + System.getProperty("user.dir") + "/images/"+ tempProperty.getImg() +"");				
				if(image.getHeight()==0)
				{
					image = new Image("file:" + System.getProperty("user.dir") + "/images/noimg.jpg");
				}				
				ImageView imageView = new ImageView();				
				imageView.setImage(image);		
				imageView.setFitWidth(592);
				imageView.setFitHeight(240);
				GridPane pane = new GridPane();
				pane.setPadding(new Insets(15, 12, 15, 12));
				pane.setHgap(400);
				pane.setPrefHeight(50);				
				String bednum;
				bednum = Integer.toString(tempProperty.getBedNum()).trim();
				if (typeCbo.getValue().compareTo(tempProperty.getProType()) != 0
						&& typeCbo.getValue().compareTo("ALL") != 0)
					continue;
				if (numOfBedCbo.getValue().compareTo(bednum) != 0 && numOfBedCbo.getValue().compareTo("ALL") != 0)
					continue;
				if (statusCbo.getValue().compareTo(tempProperty.getProstatus()) != 0
						&& statusCbo.getValue().compareTo("ALL") != 0)
					continue;
				sname = tempProperty.getSname();
				detail =tempProperty.getSnum() +" " + sname + " " + tempProperty.getSuburb() +". " + tempProperty.getDescription();
				Label snmae = new Label(sname);
				Button btDetail = new Button("Details");
				btDetail.setPrefHeight(60);
				btDetail.setPrefWidth(60);
				btDetail.setOnAction(e -> {
					
					new detail(flexrent,tempProperty);
					primaryStage.close();

				});
				pane.add(snmae, 0, 0);
				pane.add(btDetail, 1, 0);
				Label detailLabel = new Label(detail);
				detailLabel.setPrefWidth(565);
				detailLabel.setWrapText(true);
				HBox detailPane = new HBox();
				detailPane.setPadding(new Insets(15, 12, 15, 12));
				detailPane.setPrefHeight(70);
				detailPane.getChildren().add(detailLabel);
				item.setStyle("-fx-background-color: #D3D3D3;");
				item.setPrefHeight(360);
				item.getChildren().addAll(imageView, pane, detailPane);
				leftlist.getChildren().add(item);
			} else {
				Property tempProperty = tempProlist.get(i);
				VBox item = new VBox();
				String detail, sname;			
				Image image;				
				image = new Image("file:" + System.getProperty("user.dir") + "/images/"+ tempProperty.getImg() +"");				
				if(image.getHeight()==0)
				{
					image = new Image("file:" + System.getProperty("user.dir") + "/images/noimg.jpg");
				}				
				ImageView imageView = new ImageView();				
				imageView.setImage(image);		
				imageView.setFitWidth(592);
				imageView.setFitHeight(240);
				GridPane pane = new GridPane();
				pane.setPadding(new Insets(15, 12, 15, 12));
				pane.setHgap(400);
				pane.setPrefHeight(50);				
				String bednum;
				bednum = Integer.toString(tempProperty.getBedNum()).trim();
				if (typeCbo.getValue().compareTo(tempProperty.getProType()) != 0
						&& typeCbo.getValue().compareTo("ALL") != 0)
					continue;
				if (numOfBedCbo.getValue().compareTo(bednum) != 0 && numOfBedCbo.getValue().compareTo("ALL") != 0)
					continue;
				if (statusCbo.getValue().compareTo(tempProperty.getProstatus()) != 0
						&& statusCbo.getValue().compareTo("ALL") != 0)
					continue;
				sname = tempProperty.getSname();
				detail =tempProperty.getSnum() +" " + sname + " " + tempProperty.getSuburb() +". " + tempProperty.getDescription();
				Label snmae = new Label(sname);
				Button btDetail = new Button("Details");
				btDetail.setPrefHeight(60);
				btDetail.setPrefWidth(60);
				btDetail.setOnAction(e -> {
					
					new detail(flexrent,tempProperty);
					primaryStage.close();

				});
				pane.add(snmae, 0, 0);
				pane.add(btDetail, 1, 0);
				Label detailLabel = new Label(detail);
				detailLabel.setPrefWidth(565);
				detailLabel.setWrapText(true);
				HBox detailPane = new HBox();
				detailPane.setPadding(new Insets(15, 12, 15, 12));
				detailPane.setPrefHeight(70);
				detailPane.getChildren().add(detailLabel);
				item.setStyle("-fx-background-color: #D3D3D3;");
				item.setPrefHeight(360);
				item.getChildren().addAll(imageView, pane, detailPane);
				rightlist.getChildren().add(item);
			}
		}
		proList.getChildren().addAll(leftlist, rightlist);
		root.getChildren().addAll(filter, proList);
		ScrollPane sp = new ScrollPane(root);
		sp.getStyleClass().add("edge-to-edge");
		HBox.setHgrow(sp, Priority.ALWAYS);
		HBox hbox = new HBox();
		VBox fp = new VBox();
		hbox.getChildren().addAll(root, sp);
		fp.getChildren().addAll(menuBar, hbox);		
		Scene scene = new Scene(fp);
		primaryStage.setWidth(1280);
		primaryStage.setHeight(720);
		primaryStage.setTitle("FlexRent"); 
		primaryStage.setScene(scene); 
		primaryStage.show();
		}
		catch( DBException DBE) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText(null);
			alert.setContentText(DBE.getMessage());
			alert.showAndWait();
			
		}
	} 

	public FlexRent initFlexRent() throws DBException  {
		FlexRent flexrent;
	    flexrent = new FlexRent();
	
		return flexrent;
	}

	public static void main(String[] args) {
		launch(args);
	}

}

package view;


import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import controller.*;

public class detail {
	
	public detail(FlexRent flexrent,Property property){  
		
		Stage primaryStage  =new Stage();
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
		
		
		
		VBox pane = new VBox();
		pane.setSpacing(20);
		Image image;
		
			image = new Image("file:" + System.getProperty("user.dir") + "/images/"+ property.getImg() +"");
			
			if(image.getHeight()==0)
			{
				image = new Image("file:" + System.getProperty("user.dir") + "/images/noimg.jpg");
			}
			
		 		
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitHeight(450);
		imageView.setFitWidth(650);
		
		Label address=new Label();
		Label bedNum=new Label();
		Label proType=new Label();
		Label prostatus=new Label();
		Label description=new Label();
		Label lastMainDay= new Label();
		lastMainDay.setVisible(false);
		Button btRent = new Button("Rent");
		btRent.setOnAction(e -> {
			new Rent(flexrent,property);			
			primaryStage.close();
		});
		Button btReturn = new Button("Return");
		btReturn.setOnAction(e -> {
			new Return(flexrent,property);
			primaryStage.close();
			
		});
		Button btmaintenance = new Button("Maintenance");
		btmaintenance.setOnAction(e -> {
			new Maintenance(flexrent,property);
			primaryStage.close();
		});
		Button btCmaintenance = new Button("Complete Maintenance");
		btCmaintenance.setOnAction(e -> {
			new CompleteMain(flexrent,property);
			primaryStage.close();
		});
		
		HBox btnHbox=new HBox();
		
		btnHbox.setSpacing(10);
		btnHbox.getChildren().addAll(btRent,btReturn,btmaintenance,btCmaintenance);
		
		address.setText( Integer.toString(property.getSnum()) + " " + property.getSname());
		bedNum.setText("Number of bedrooms :" + property.getBedNum());
		proType.setText("Property Type :" + property.getProType());
		prostatus.setText("Status :"+property.getProstatus());
		description.setText(property.getDescription());	
		
		if(property instanceof PremiumSuite)
		{
			lastMainDay.setText("Last maintenance Date: " + ((PremiumSuite)property).getLatestMainDay());
			lastMainDay.setVisible(true);
		}
		pane.getChildren().addAll(imageView,address,bedNum,proType,prostatus,description,lastMainDay,btnHbox);
		HBox hbox = new HBox();
		hbox.setSpacing(50);
		VBox fp = new VBox();		
		
		Label recordLabel=new Label("Rental Record");		
		TextArea recordText=new TextArea(property.getRecordDetails());		
		recordText.setPrefWidth(500);
		recordText.setPrefHeight(430);
		recordText.setWrapText(true);		
		ScrollPane scrollPane = new ScrollPane(recordText);
		scrollPane.setFitToWidth(true);
		
		VBox recordvBox =new VBox(recordLabel,scrollPane);
		recordvBox.setPrefHeight(465);
		recordvBox.setSpacing(20);
		
		
		hbox.getChildren().addAll(pane, recordvBox);
		fp.getChildren().addAll(menuBar, hbox);		
		
		Scene scene = new Scene(fp,1280,768); 
		scene.setFill(Color.WHITE);
		     
		primaryStage.setTitle(Integer.toString(property.getSnum()) + " " + property.getSname());
		primaryStage.setScene(scene);     
		primaryStage.show();
	}


	
	

}

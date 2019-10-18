package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.*;

public class ExportDataListener implements EventHandler<ActionEvent> {

	private Stage primaryStage;
	private FlexRent flexrent;
	
	public ExportDataListener(Stage stage) throws DBException {
		primaryStage = stage;
		
		flexrent=new FlexRent();
	}
	
	@Override
	public void handle(ActionEvent event) {
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Select a folder...");
		File selectedDir = dirChooser.showDialog(primaryStage);
//		String selectedDirPath = dirChooser.showDialog(primaryStage).getAbsolutePath();
//		System.out.println(selectedDir.getAbsolutePath());
		writeToFile(selectedDir.getAbsolutePath(), "export_data.txt");
		
	
	}
	
	private void writeToFile(String absolutePath, String fileName) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(absolutePath + "/" + fileName, "UTF-8");
			LinkedList<Property> propertylist=flexrent.getProperty();
			for(int i=0;i<propertylist.size();i++)
			{
				writer.println(propertylist.get(i).toString());
				if(propertylist.get(i).getRecord().size()>0)
				{
					for(int k=0;k<propertylist.get(i).getRecord().size();k++)
					{
						writer.println(propertylist.get(i).getRecord().get(k).toString());
					}
				}
				
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Export successfully");
			alert.setHeaderText(null);
			alert.setContentText("File " + fileName + " has been saved to " + absolutePath);
			alert.showAndWait();
			
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
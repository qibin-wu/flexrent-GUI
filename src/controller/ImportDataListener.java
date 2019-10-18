package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import utilities.DateTime;

public class ImportDataListener implements EventHandler<ActionEvent> {

	private Stage primaryStage;

	public ImportDataListener(Stage stage) {
		primaryStage = stage;

	}

	@Override
	public void handle(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a file...");
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
//		String selectedDirPath = dirChooser.showDialog(primaryStage).getAbsolutePath();
//		System.out.println(selectedDir.getAbsolutePath());

		importFile(selectedFile.getAbsolutePath());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Import successfully");
		alert.setHeaderText(null);
		alert.setContentText("File " + selectedFile.getName() + " has been import ");
		alert.showAndWait();
	}

	public void importFile(String absolutePath) {// record 5£¬ apartment 9 PS 10
		Property tempProperty = null;
		Record tempRecord;
		File file1 = new File(absolutePath);

		try (Scanner input = new Scanner(file1)) {

			LinkedList<String> longString = new LinkedList<>();

			while (input.hasNextLine()) {
				longString.addLast(input.nextLine());
			}

			for (int i = 0; i < longString.size(); i++) {
				String substring[] = longString.get(i).split(":");



				if (substring.length == 9) {
					tempProperty = new Apartment(Integer.parseInt(substring[1]), substring[2], substring[3],
							Integer.parseInt(substring[5]), substring[4], substring[6], substring[7], substring[8]);
					Insert.insertProperty(tempProperty);
				} else if (substring.length == 10) {
					int day, month, year;
					day = Integer.parseInt(substring[7].substring(0, 2));
					month = Integer.parseInt(substring[7].substring(3, 5));
					year = Integer.parseInt(substring[7].substring(6));
					DateTime lmDate = new DateTime(day, month, year);

					tempProperty = new PremiumSuite(Integer.parseInt(substring[1]), substring[2], substring[3],
							substring[4], substring[6], lmDate, substring[8], substring[9]);
					Insert.insertProperty(tempProperty);
				} else {
					int day, month, year;
					day = Integer.parseInt(substring[1].substring(0, 2));
					month = Integer.parseInt(substring[1].substring(3, 5));
					year = Integer.parseInt(substring[1].substring(6));
					DateTime rentDate = new DateTime(day, month, year);

					day = Integer.parseInt(substring[2].substring(0, 2));
					month = Integer.parseInt(substring[2].substring(3, 5));
					year = Integer.parseInt(substring[2].substring(6));
					DateTime esReturnDate = new DateTime(day, month, year);

					day = Integer.parseInt(substring[3].substring(0, 2));
					month = Integer.parseInt(substring[3].substring(3, 5));
					year = Integer.parseInt(substring[3].substring(6));
					DateTime acReturnDate = new DateTime(day, month, year);

					String cusid = new StringBuffer(substring[0]).reverse().toString();

					cusid=cusid.substring(9, 16);

					String id = new StringBuffer(cusid).reverse().toString();

					tempRecord = new Record(rentDate, esReturnDate, acReturnDate, Double.parseDouble(substring[4]),
							Double.parseDouble(substring[5]), tempProperty, id);
					Insert.insertRecord(tempRecord);

				}

			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (DBException DBE) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText(null);
			alert.setContentText(DBE.getMessage());
			alert.showAndWait();
		}
	}

}

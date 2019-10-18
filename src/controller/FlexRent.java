package controller;

import utilities.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import model.*;

public class FlexRent {
	private LinkedList<Property> property;	

	public FlexRent() throws DBException {
		this.property = getData.getProperty();
		
	}


	

	public void completeMaintenance(Property property) throws DBException {		
		Apartment apartment = (Apartment) (property);		
		apartment.completeMaintenance();	
	}

	public void completeMaintenance(Property property, String cDate) throws completeMaintenanceException, DBException { // Maintenance done

		
		
		int day, month, year;
		
		if (this.checkDate(cDate) == false) {				
				throw new completeMaintenanceException("not a valid date");
			}
			day = Integer.parseInt(cDate.substring(0, 2));
			month = Integer.parseInt(cDate.substring(3, 5));
			year = Integer.parseInt(cDate.substring(6));
			DateTime completionDate = new DateTime(day, month, year);
			PremiumSuite suite = (PremiumSuite) (property);
		
			suite.completeMaintenance(completionDate);

	}

	public void propertyMaintenance(Property property) throws performMaintenanceException, DBException { // property start Maintenance
		property.performMaintenance();
	}

	public void returnProperty(String rDate,Property property) throws returnPropertyException,DBException { // Return the property
		
		String PID;
		int day, month, year;		
		PID = property.getPid();

		if (property.getProstatus().compareTo("maintenance") == 0) {			
			
			throw new returnPropertyException(property.getProType() + " " + PID + " could not be returned");
		}
		
		if (this.checkDate(rDate) == false) {			
			throw new returnPropertyException("not a valid date");
		}
		day = Integer.parseInt(rDate.substring(0, 2));
		month = Integer.parseInt(rDate.substring(3, 5));
		year = Integer.parseInt(rDate.substring(6));
		DateTime returnDate = new DateTime(day, month, year);		
		property.returnProperty(returnDate);	
		
	}

	public void rentProperty(String customerID, String rDate, int numOfRentDay,Property property) throws rentPropertyException, DBException {// rent a property
	
		int day, month, year;	
		if (this.checkDate(rDate) == false) {
					
			throw new rentPropertyException("not a valid date");		
		}
		day = Integer.parseInt(rDate.substring(0, 2));
		month = Integer.parseInt(rDate.substring(3, 5));
		year = Integer.parseInt(rDate.substring(6));
		DateTime rentDate = new DateTime(day, month, year);			
		property.rent(customerID, rentDate, numOfRentDay);
		
		
	}

	private boolean checkDate(String rDate) {// check the input Date is valid or not

		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			format.setLenient(false);
			format.parse(rDate);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public void addProperty(String type, String streetName, String suburb, String lmDay, int streetNum, int bedNum,
			String description) throws addPropertyException, DBException{
		
		if (type.compareTo("Premium Suite") == 0) {
			
			if (this.checkDate(lmDay) == false) {				
				throw new addPropertyException("not a valid date");
			}
			DateTime latestMainDay = new DateTime(Integer.parseInt(lmDay.substring(0, 2)),
					Integer.parseInt(lmDay.substring(3, 5)), Integer.parseInt(lmDay.substring(6)));
			Property addsuite = new PremiumSuite(streetNum, streetName, suburb, type, "available", latestMainDay,"noimg.jpg",
					description);
			if (this.checkPid(addsuite) == false) {				
				throw new addPropertyException("Property already exist");
			}
			this.property.addFirst(addsuite);
			Insert.insertProperty(addsuite);
		} else {
			
			Property addapartment = new Apartment(streetNum, streetName, suburb, bedNum, type, "available","noimg.jpg",
					description);
			if (this.checkPid(addapartment) == false) {
				throw new addPropertyException("Property already exist");
			}
			this.property.addFirst(addapartment);
			Insert.insertProperty(addapartment);
		}
		
	}

	private boolean checkPid(Property property) { // check property id repetition
		int k;
		for (k = 0; k < this.property.size(); k++) {
			if (this.property.get(k).getPid().compareTo(property.getPid()) == 0)
				return false;
		}
		return true;
	}

	public LinkedList<Property> getProperty() {
		return property;
	}



}

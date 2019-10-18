package model;
import java.text.DecimalFormat;

import controller.*;
import utilities.DateTime;

public class Apartment extends Property implements ApartmentMaintenance {
	private double rentRates;

	
	public Apartment(int snum, String sname, String suburb, int bedNum, String proType, String prostatus,String img,String description) {// new

		super(("A_" + snum + sname.toUpperCase() + suburb.toUpperCase()).replace(" ", ""), snum, sname, suburb, bedNum,
				proType, prostatus,img,description);

		if (bedNum == 1)
			this.rentRates = 143;
		else if (bedNum == 2)
			this.rentRates = 210;
		else if (bedNum == 3)
			this.rentRates = 319;
		else {
			System.out.println("Apartment only can have 1, 2 or 3 bedrooms");
			return;
		}

	}

	public void rent(String customerID, DateTime rentDate, int numOfRentDay) throws rentPropertyException, DBException {// add a rent record to Apartment
		if (super.getProstatus().toLowerCase().compareTo("available") !=0)			
		throw new rentPropertyException("This property is currently unavailable");
		else {
			if (numOfRentDay > 28 || numOfRentDay < 2) {
				throw new rentPropertyException("Number of days stayed is invalid(should be between 2 and 28) ");
			}
			if (numOfRentDay == 2) {
				if ((rentDate.getWeek() != 5 && rentDate.getWeek() != 6 && rentDate.getWeek() != 7))
					throw new rentPropertyException("A minimum of 2 days if the rental day is between Sunday and Thursday inclusively ");
			}
			if (numOfRentDay == 3) {
				if ((rentDate.getWeek() != 6 && rentDate.getWeek() != 7))
					throw new rentPropertyException("A minimum of 3 days if the rental day is Friday or Saturday");
			}

			DateTime esReturnDate = new DateTime(rentDate, numOfRentDay);

			super.setProstatus("rented");
			Record newRent = new Record(rentDate, esReturnDate, null, 0, 0, this, customerID);
			super.rent(newRent);
			
		}
	}

	public void returnProperty(DateTime returnDate) throws returnPropertyException, DBException{ // Return Apartment and Calculate rent fee and late fee
		Record newestrecord;
		newestrecord = super.getRecord().get(0);
		if (DateTime.diffDays(returnDate, newestrecord.getRentDate()) <= 0) // return day before rent day
		{
			throw new returnPropertyException("Return day before rent day");
		} else {
			super.setProstatus("available");
			double rentfee, latefee;
			if (DateTime.diffDays(returnDate, newestrecord.getEsReturnDate()) <= 0)// returned in advanced or on time
			{
				rentfee = this.rentRates * (DateTime.diffDays(returnDate, newestrecord.getRentDate()));
				latefee = 0;
			} else {// return late
				rentfee = this.rentRates* (DateTime.diffDays(newestrecord.getEsReturnDate(), newestrecord.getRentDate()));
				latefee = this.rentRates * 1.15 * (DateTime.diffDays(returnDate, newestrecord.getEsReturnDate()));
			}
			super.addReturnDetails(returnDate, rentfee, latefee);
			
		}
	}

	public boolean completeMaintenance() throws DBException { // Complete the property maintenance

		if (this.getProstatus() != "maintenance") {
			return false;
		} else {
			this.setProstatus("available");			
			return true;
		}
	}

	public String getDetails() {// Generate Apartment Details
		String details;

		if (super.getRecordNum() == 0) {
			details = "Property ID:" + "\t" + super.getPid() + "\n" + "Address:" + super.getSnum() + " "
					+ super.getSname() + " " + super.getSuburb() + "\n" + "Bedroom:" + "\t" + super.getBedNum() + "\n"
					+ "Status:" + "\t" + super.getProstatus() + "\n" + "RENTAL RECORD: empty"+"\n";
		} else {
			String propertyAttritube, record = "";
			propertyAttritube = "Property ID:" + "\t" + super.getPid() + "\n" + "Address:" + super.getSnum() + " "
					+ super.getSname() + " " + super.getSuburb() + "\n" + "Bedroom:" + "\t" + super.getBedNum() + "\n"
					+ "Status:" + "\t" + super.getProstatus() + "\n" + "RENTAL RECORD: " + "\n";
			int i;
			for (i = 0; i < super.getRecordNum(); i++) {

				Record temp = super.getRecord().get(i);
				DecimalFormat dFormat = new DecimalFormat("#.00");
				String rentfee = dFormat.format(temp.getRentFee());
				String latefee = dFormat.format(temp.getLateFee());

				if (temp.getRentFee()==0) {
					record = "Record ID:" + "\t" + temp.getRID() + "\n" + "Rent Date:" + "\t"
							+ temp.getRentDate().getFormattedDate() + "\n" + "Estimated Return Date:" + "\t"
							+ temp.getEsReturnDate().getFormattedDate() + "\n" + "------------------------------------" + "\n";
				} else {
					record += "Record ID:" + "\t" + temp.getRID() + "\n" + "Rent Date:" + "\t"
							+ temp.getRentDate().getFormattedDate() + "\n" + "Estimated Return Date:" + "\t"
							+ temp.getEsReturnDate().getFormattedDate() + "\n" + "Actual Rturn Date:" + "\t"
							+ temp.getAcReturnDate().getFormattedDate() + "\n" + "Rental Fee:" + "\t" + rentfee + "\n"
							+ "Late Fee:" + "\t" + latefee + "\n";

					if (i != super.getRecordNum() - 1) {
						record += "------------------------------------"+"\n";
					}
				}
			}
			details = propertyAttritube + record;
		}

		return details;

	}

	public String getReturnRecord() {// Generate Apartment return details
		String result;
		String propertyAttritube, record = "";
		propertyAttritube = "Property ID:" + "\t" + super.getPid() + "\n" + "Address:" + super.getSnum() + " "
				+ super.getSname() + " " + super.getSuburb() + "\n" + "Bedroom:" + "\t" + super.getBedNum() + "\n"
				+ "Status:" + "\t" + super.getProstatus() + "\n" + "RENTAL RECORD: " + "\n";
		Record temp = super.getRecord().get(0);
		record +=temp.getDetails();		
		record += "------------------------------------"+"\n";
		result = propertyAttritube + record;
		return result;

	}
	
	

	public double getRentRates() {
		return rentRates;
	}

	
	public String getRecordDetails() {
		String record="";
		int i;
		for (i = 0; i < super.getRecordNum(); i++) {

			Record temp = super.getRecord().get(i);
			DecimalFormat dFormat = new DecimalFormat("#.00");
			String rentfee = dFormat.format(temp.getRentFee());
			String latefee = dFormat.format(temp.getLateFee());
			
			if(temp.getLateFee()==0)
			{
				latefee="0";
			}

			if (i == 0 && super.getProstatus().compareTo("rented") == 0) {
				record = "Record ID:" + "\t" + temp.getRID() + "\n" + "Rent Date:" + "\t"
						+ temp.getRentDate().getFormattedDate() + "\n" + "Estimated Return Date:" + "\t"
						+ temp.getEsReturnDate().getFormattedDate() + "\n" + "------------------------------------" +"\n";
			} else {
				record += "Record ID:" + "\t" + temp.getRID() + "\n" + "Rent Date:" + "\t"
						+ temp.getRentDate().getFormattedDate() + "\n" + "Estimated Return Date:" + "\t"
						+ temp.getEsReturnDate().getFormattedDate() + "\n" + "Actual Rturn Date:" + "\t"
						+ temp.getAcReturnDate().getFormattedDate() + "\n" + "Rental Fee:" + "\t" + rentfee + "\n"
						+ "Late Fee:" + "\t" + latefee + "\n";

				if (i != super.getRecordNum() - 1) {
					record += "------------------------------------"+"\n";
				}
			}
		}
		return record;
	}

	@Override
	public String toString() {
		String result;
		
		result = this.getPid()+ ":" + this.getSnum() + ":" + this.getSname()+ ":" + this.getSuburb() + ":" + this.getProType()+ ":" + this.getBedNum() +":"+ this.getProstatus()+":"+this.getImg()+":"+this.getDescription();
		return result;
	}

}

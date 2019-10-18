package model;
import java.text.DecimalFormat;

import controller.*;
import utilities.DateTime;

public class PremiumSuite extends Property implements SuiteMaintenance {

	private final int rentRate = 554;
	private final int lateRate = 662;
	private final int interval = 10;
	private DateTime latestMainDay;

	
	public PremiumSuite(int snum, String sname, String suburb, String proType, String prostatus,
			DateTime latestMainDay,String img,String description) {//from DB
		super(("S_" + snum + sname.toUpperCase() + suburb.toUpperCase()).replace(" ", ""), snum, sname, suburb, 3,
				proType, prostatus,img,description);
		this.latestMainDay = latestMainDay;
	}

	public void rent(String customerID, DateTime rentDate, int numOfRentDay) throws rentPropertyException, DBException{// add a rent record to Premium suite
		if (super.getProstatus().toLowerCase().compareTo("available") !=0)
			throw new rentPropertyException("This property is currently unavailable");
		else {
			DateTime esReturnDate = new DateTime(rentDate, numOfRentDay);
			DateTime nextMainDay = new DateTime(latestMainDay, this.interval);
			if (numOfRentDay < 1 || numOfRentDay > 10) {
				throw new rentPropertyException("Number of days stayed is invalid(should be between 1 and 10) ");
			}
			if (DateTime.diffDays(esReturnDate, nextMainDay) > 0) // exceeds maintenance date
			{
				throw new rentPropertyException("Exceeds maintenance date!!!");
			}
			super.setProstatus("rented");
			Record newRent = new Record(rentDate, esReturnDate, null, 0, 0, this, customerID);
			super.rent(newRent);
			
		}
	}

	public String toString() {
		String result;
		
		result = this.getPid()+ ":" + this.getSnum() + ":" + this.getSname()+ ":" + this.getSuburb() + ":" + this.getProType()+ ":" + this.getBedNum() +":"+ this.getProstatus()+":"+ this.getLatestMainDay().getFormattedDate()+":"+this.getImg()+":"+this.getDescription();
		return result;
	}

	public void returnProperty(DateTime returnDate) throws returnPropertyException, DBException {// Return Premium suite and Calculate rent fee and late fee
		Record newestrecord;
		newestrecord = super.getRecord().get(0);
		if (DateTime.diffDays(returnDate, newestrecord.getRentDate()) <= 0) // return day before rent day
		{
			throw new returnPropertyException("Return day before rent day");
		} else {
			super.setProstatus("available");
			double rentfee, latefee;
			if (DateTime.diffDays(returnDate, newestrecord.getEsReturnDate()) <= 0) {// returned in advanced or on time
				rentfee = this.rentRate * (DateTime.diffDays(returnDate, newestrecord.getRentDate()));
				latefee = 0;
			} else {// return late
				rentfee = this.rentRate
						* (DateTime.diffDays(newestrecord.getEsReturnDate(), newestrecord.getRentDate()));
				latefee = this.lateRate * (DateTime.diffDays(returnDate, newestrecord.getEsReturnDate()));
			}

			super.addReturnDetails(returnDate, rentfee, latefee);
			
		}
	}

	public void completeMaintenance(DateTime completionDate) throws completeMaintenanceException, DBException {// Complete the property maintenance

		if (DateTime.diffDays(completionDate, this.getLatestMainDay()) <= 0)
			throw new completeMaintenanceException("Complete date cannot early than latest maintenance day");
		
		if (this.getProstatus().compareTo("maintenance") !=0) {
			throw new completeMaintenanceException("This property currently not in maintenance");
		} else {
			this.latestMainDay = completionDate;
			Update.updatecompletionDate(this,completionDate );
			this.setProstatus("available");
			
		}
	}

	public String getDetails() {// Generate Premium suite Details
		String details;

		if (super.getRecordNum() == 0) {
			details = "Property ID:" + "\t" + super.getPid() + "\n" + "Address:" + super.getSnum() + " "
					+ super.getSname() + " " + super.getSuburb() + "\n" + "Bedroom:" + "\t" + super.getBedNum() + "\n"
					+ "Status:" + "\t" + super.getProstatus() + "\n" + "Last Maintenance:" + "\t"
					+ this.getLatestMainDay().getFormattedDate() + "\n" + "RENTAL RECORD: empty" + "\n";
		} else {
			String propertyAttritube, record = "";
			propertyAttritube = "Property ID:" + "\t" + super.getPid() + "\n" + "Address:" + super.getSnum() + " "
					+ super.getSname() + " " + super.getSuburb() + "\n" + "Bedroom:" + "\t" + super.getBedNum() + "\n"
					+ "Status:" + "\t" + super.getProstatus() + "\n" + "Last Maintenance:" + "\t"
					+ this.getLatestMainDay().getFormattedDate() + "\n" + "RENTAL RECORD: " + "\n";
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

	public String getReturnRecord() {// Generate Premium suite return details
		String result;
		String propertyAttritube, record = "";
		propertyAttritube = "Property ID:" + "\t" + super.getPid() + "\n" + "Address:" + super.getSnum() + " "
				+ super.getSname() + " " + super.getSuburb() + "\n" + "Bedroom:" + "\t" + super.getBedNum() + "\n"
				+ "Status:" + "\t" + super.getProstatus() + "\n" + "Last Maintenance:" + "\t"
				+ this.getLatestMainDay().getFormattedDate() + "\n" + "RENTAL RECORD: " + "\n";
		Record temp = super.getRecord().get(0);
		record += temp.getDetails();
		record += "------------------------------------"+"/n";
		result = propertyAttritube + record;
		return result;

	}

	public int getRentRate() {
		return rentRate;
	}

	public int getLateRate() {
		return lateRate;
	}

	public int getInterval() {
		return interval;
	}

	public DateTime getLatestMainDay() {
		return latestMainDay;
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
			
			
			if (temp.getRentFee() == 0) {
				record = "Record ID:" + "\t" + temp.getRID() + "\n" + "Rent Date:" + "\t"
						+ temp.getRentDate().getFormattedDate() + "\n" + "Estimated Return Date:" + "\t"
						+ temp.getEsReturnDate().getFormattedDate() + "\n" + "------------------------------------"+"\n";
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

}

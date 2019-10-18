package model;
import java.util.LinkedList;

import controller.*;
import utilities.DateTime;

public abstract class Property {
	private String pid;
	private int snum;//street number
	private String sname;
	private String suburb;
	private int bedNum;
	private String proType;
	private String prostatus;
	private String description;
	private String img;
	private LinkedList<Record> record;
	
	public Property(String pid, int snum, String sname, String suburb, int bedNum, String proType, String prostatus, String img,String description) {// from DB
		this.pid = pid;
		this.snum = snum;
		this.sname = sname;
		this.suburb = suburb;
		this.bedNum = bedNum;
		this.proType = proType;
		this.prostatus = prostatus;
		this.description=description;
		this.record = new LinkedList<>();
		this.img=img;
	}
	public void rent(Record record) throws rentPropertyException, DBException { //add a new record to this property when new rent happen
		int k;
		
		for (k=0;k<this.getRecordNum();k++)
		{
			if(this.record.get(k).getRID().compareTo(record.getRID())==0)
			{							
				throw new rentPropertyException("Record already exist");
			}
		}		

		this.record.addFirst(record);
		Insert.insertRecord(record);
	}

	protected void addReturnDetails(DateTime returnDate, double rentfee, double latefee) throws DBException { //Set the return Details into the record
		this.record.get(0).setAcReturnDate(returnDate);
		this.record.get(0).setRentFee(rentfee);
		this.record.get(0).setLateFee(latefee);
		this.record.get(0).setPropertyStatus("available");
		Update.updateReturnDetail(this, returnDate, rentfee, latefee);
	
	}

	public void performMaintenance() throws performMaintenanceException, DBException { //start maintenance

		if (this.getProstatus().toLowerCase().compareTo("available") !=0 ) {
			throw new performMaintenanceException("The property is currently being rented or maintained ");
		} else {
			this.setProstatus("maintenance");
			
		}
	}

	public void addRecord(Record record) {// add record by database
		this.record.addFirst(record);
	}
	
	

	public abstract void rent(String customerID, DateTime rentDate, int numOfRentDay) throws rentPropertyException,DBException;
	public abstract void returnProperty(DateTime returnDate) throws returnPropertyException,DBException;
	public abstract String getReturnRecord();
	public abstract String getDetails();
	public abstract String getRecordDetails();
	public abstract String toString();
	public int getRecordNum() {// Get the numbers of record in the property 
		int k;		
		k=this.record.size();
		return k;
	}
	public String getPid() {
		return pid;
	}

	public int getSnum() {
		return snum;
	}

	public String getSname() {
		return sname;
	}

	public String getSuburb() {
		return suburb;
	}

	public int getBedNum() {
		return bedNum;
	}

	public String getProType() {
		return proType;
	}

	public String getProstatus() {
		return prostatus;
	}

	public LinkedList<Record> getRecord() {
		return record;
	}

	public String getDescription() {
		return description;
	}
	protected void setProstatus(String prostatus) throws DBException {
		this.prostatus = prostatus;
		Update.updateProStatus(this, prostatus);
	}

	public String getImg() {
		return img;
	}
	

}

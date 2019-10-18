package model;
import java.text.DecimalFormat;

import controller.DBException;
import utilities.DateTime;
public class Record {

	private String RID;
	private DateTime rentDate;
	private DateTime esReturnDate;
	private DateTime acReturnDate;
	private double rentFee;
	private double lateFee;	
	private String CID;
	private Property property;

	public Record(DateTime rentDate, DateTime esReturnDate, DateTime acReturnDate, double rentFee, double lateFee,
			Property property,String CID) {		
		this.rentDate = rentDate;
		this.esReturnDate = esReturnDate;
		this.acReturnDate = acReturnDate;
		this.rentFee = rentFee;
		this.lateFee = lateFee;
		this.property = property;
		this.CID=CID;			
		this.RID = property.getPid() + "_" + CID +"_"+ rentDate.getEightDigitDate();
	}	
	
	public String getRID() {
		return this.RID;
	}

	public DateTime getRentDate() {
		return this.rentDate;
	}

	public double getRentFee() {
		return this.rentFee;
	}

	public double getLateFee() {
		return this.lateFee;
	}

	public DateTime getEsReturnDate() {
		return esReturnDate;
	}

	public DateTime getAcReturnDate() {
		return acReturnDate;
	}

	public Property getProperty() {
		return property;
	}
	

	public String getCID() {
		return CID;
	}
	

	public void setAcReturnDate(DateTime acReturnDate) {
		this.acReturnDate = acReturnDate;
	}

	public void setRentFee(double rentFee) {
		this.rentFee = rentFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	public void setPropertyStatus(String status) throws DBException
	{
		this.property.setProstatus(status);
	}

	public String toString() {// Generate record string
		if (this.rentFee !=0 )
			return (this.RID + ":" + this.rentDate + ":" + this.esReturnDate + ":" + this.acReturnDate + ":"
					+ this.rentFee + ":" + this.lateFee);
		else
			return (this.RID + ":" + this.rentDate + ":" + this.esReturnDate + ":" + "none:none:none");
	}

	public String getDetails() {// Generate record details
		String details;
		if (this.rentFee ==0)
			details = "Record ID:" + "\t" + this.RID + "\n" + "Rent Date:" + "\t" + this.rentDate + "\n"
					+ "Estimated Return Date:" + "\t" + this.esReturnDate;
		else
			{
			 DecimalFormat dFormat=new DecimalFormat("#.00");
	         String rentfee=dFormat.format(this.rentFee);
	         String latefee=dFormat.format(this.lateFee);	
	         
	         if(this.lateFee==0)
	         {
	        	 latefee="0";
	         }
			
		    details = "Record ID:" + "\t" + this.RID + "\n" + "Rent Date:" + "\t" + this.rentDate + "\n"
					+ "Estimated Return Date:" + "\t" + this.esReturnDate + "\n" + "Actual Return Date:" + "\t"
					+ this.acReturnDate + "\n" + "Rental Fee:" + "\t" + rentfee + "\n" + "Late Fee:" + "\t"
					+ latefee+"\n";
		    }

		return (details);
	}

}

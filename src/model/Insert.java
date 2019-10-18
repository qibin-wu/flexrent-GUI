package model;

import java.sql.Connection;
import java.sql.Statement;

import controller.DBException;


public class Insert {

	public static void insertProperty(Property property) throws DBException {
		final String DB_NAME = "flexRentDB";
		final String TABLE_NAME = "RENTAL_PROPERTY";
		String query;

		// use try-with-resources Statement
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {

			if (property instanceof Apartment) 
			{
				 query = "INSERT INTO " + TABLE_NAME + " VALUES ('" + property.getPid() + "',"+property.getSnum()+",'"+property.getSname()+"', '"+property.getSuburb()+"',"+property.getBedNum()+",'"+property.getProType()+"','"+property.getProstatus().toLowerCase()+"','none','"+property.getImg()+"','"+property.getDescription()+"')";
				
				
			} 
			else 
			{
				query = "INSERT INTO " + TABLE_NAME + " VALUES ('" + property.getPid() + "',"+property.getSnum()+",'"+property.getSname()+"', '"+property.getSuburb()+"',"+property.getBedNum()+",'"+property.getProType()+"','"+property.getProstatus().toLowerCase()+"','"+ ((PremiumSuite)property).getLatestMainDay().getFormattedDate() +"','"+property.getImg() +"','"+property.getDescription()+"')";
				
			}
			

			stmt.executeUpdate(query);

			con.commit();
			con.close();


		} catch (Exception e) {			
			
			throw new DBException(e.getMessage());
		}
	}
	
	public static void insertRecord(Record record) throws DBException {
		final String DB_NAME = "flexRentDB";
		final String TABLE_NAME = "RENTAL_RECORD";
		String query;
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {

			if(record.getRentFee()==0)
			query = "INSERT INTO " + TABLE_NAME + " VALUES ('" + record.getRID() + "','"+record.getRentDate().getFormattedDate()+"','"+record.getEsReturnDate().getFormattedDate()+"','none',0,0,'"+record.getProperty().getPid()+"','"+record.getCID()+"')";
			else
			query = "INSERT INTO " + TABLE_NAME + " VALUES ('" + record.getRID() + "','"+record.getRentDate().getFormattedDate()+"','"+record.getEsReturnDate().getFormattedDate()+"','"+ record.getAcReturnDate().getFormattedDate() +"',"+ record.getRentFee()  +","+record.getLateFee()+",'"+record.getProperty().getPid()+"','"+record.getCID()+"')";
			stmt.executeUpdate(query);
			con.commit();
			con.close();

			

		} catch (Exception e) {
			throw new DBException(e.getMessage());
			
		}
		
	}
	
	
	
}

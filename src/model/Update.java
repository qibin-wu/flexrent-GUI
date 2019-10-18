package model;

import java.sql.Connection;
import java.sql.Statement;

import controller.DBException;
import utilities.DateTime;

public class Update {
	
	public static void updateProStatus(Property property,String status) throws DBException {
		final String DB_NAME = "flexRentDB";
		final String TABLE_NAME = "RENTAL_PROPERTY";
	
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			String query = "UPDATE " + TABLE_NAME +
					" SET  prostatus = '" +status +"'" +
					" WHERE pid = '"+property.getPid() +"'";
			
			stmt.executeUpdate(query);
			
			
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		
	}	
	
	public static void updateReturnDetail(Property property,DateTime returnDate, double rentfee, double latefee) throws DBException {
		final String DB_NAME = "flexRentDB";
		final String TABLE_NAME = "RENTAL_RECORD";
	
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			String query = "UPDATE " + TABLE_NAME +
					" SET  acReturnDate = '" +returnDate.getFormattedDate() +"', rentFee="+ rentfee+",lateFee="+latefee+" " +
					" WHERE pid = '"+property.getPid() +"' and rentFee=0 and lateFee=0";
			
			stmt.executeUpdate(query);
			
		

		} catch (Exception e) {		
			throw new DBException(e.getMessage());
			
		}
		
	}
	public static void updatecompletionDate(PremiumSuite preSuit,DateTime cDate) throws DBException {
		final String DB_NAME = "flexRentDB";
		final String TABLE_NAME = "RENTAL_PROPERTY";
	
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			String query = "UPDATE " + TABLE_NAME +
					" SET  latestMainDay = '" +cDate.getFormattedDate() +"'" +
					" WHERE pid = '"+ preSuit.getPid() +"'";
			
			stmt.executeUpdate(query);
			
			

		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		
	}

}

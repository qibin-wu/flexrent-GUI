package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import controller.DBException;
import controller.FlexRent;
import controller.InvalidIdException;
import utilities.DateTime;

public class getData {

	public static LinkedList<Property> getProperty() throws DBException{//get Property list from DB
		Property property;
		final String DB_NAME = "flexRentDB";
		final String TABLE_NAME = "RENTAL_PROPERTY";
		String query;
		String sname, suburb, proType, proStatus, lmDay, img,description;
		int sNum, bedNum;
		LinkedList<Property> propertylist = new LinkedList<>();

		// use try-with-resources Statement
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			query = "SELECT * FROM " + TABLE_NAME;

			try (ResultSet resultSet = stmt.executeQuery(query)) {

				while (resultSet.next()) {
					sNum = resultSet.getInt("snum");
					sname = resultSet.getString("sname");
					suburb = resultSet.getString("suburb");
					proType = resultSet.getString("proType");
					proStatus = resultSet.getString("prostatus");
					bedNum = resultSet.getInt("bednum");
					lmDay = resultSet.getString("latestMainDay");
					description = resultSet.getString("description");
					img=resultSet.getString("image");

					if (proType.compareTo("Apartment") == 0) {
						property = new Apartment(sNum, sname, suburb, bedNum, proType, proStatus,img, description);
						propertylist.addFirst(property);

					} else {
						DateTime latestMainDay = new DateTime(Integer.parseInt(lmDay.substring(0, 2)),
								Integer.parseInt(lmDay.substring(3, 5)), Integer.parseInt(lmDay.substring(6)));
						property = new PremiumSuite(sNum, sname, suburb, proType, proStatus, latestMainDay,img,
								description);
						propertylist.addFirst(property);
					}

				}

				con.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}

		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}

		addRecord(propertylist);// add record to property list
		return propertylist;
	}

	public static void addRecord(LinkedList<Property> propertylist) throws DBException { //get Record list from DB
		final String DB_NAME = "flexRentDB";
		final String TABLE_NAME = "RENTAL_RECORD";
		String query, pid, cid, rentDate, esReturnDate, acReturnDate;
		double rentFee, lateFee;
		int i;
		DateTime rentDateDT, esReturnDateDT, acReturnDateDT;
		Record tempRecord;

		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			query = "SELECT * FROM " + TABLE_NAME;

			try (ResultSet resultSet = stmt.executeQuery(query)) {

				while (resultSet.next()) {
					pid = resultSet.getString("pid");
					// rid=resultSet.getString("Rid");
					rentDate = resultSet.getString("rentDate");
					esReturnDate = resultSet.getString("esReturnDate");
					acReturnDate = resultSet.getString("acReturnDate");
					cid = resultSet.getString("cid");
					rentFee = resultSet.getFloat("rentFee");
					lateFee = resultSet.getFloat("lateFee");

					rentDateDT = new DateTime(Integer.parseInt(rentDate.substring(0, 2)),
							Integer.parseInt(rentDate.substring(3, 5)), Integer.parseInt(rentDate.substring(6)));
					esReturnDateDT = new DateTime(Integer.parseInt(esReturnDate.substring(0, 2)),
							Integer.parseInt(esReturnDate.substring(3, 5)),
							Integer.parseInt(esReturnDate.substring(6)));
					
					if(acReturnDate.compareTo("none")==0)
					{
						acReturnDateDT=null;
					}
					else {
					acReturnDateDT = new DateTime(Integer.parseInt(acReturnDate.substring(0, 2)),
							Integer.parseInt(acReturnDate.substring(3, 5)),
							Integer.parseInt(acReturnDate.substring(6)));
					}
				

					for (i = 0; i < propertylist.size(); i++) {
						if (pid.compareTo(propertylist.get(i).getPid()) == 0) {
							if (propertylist.get(i).getRecord().size() <= 10) {
								tempRecord = new Record(rentDateDT, esReturnDateDT, acReturnDateDT, rentFee, lateFee,
										propertylist.get(i), cid);
								propertylist.get(i).addRecord(tempRecord);
							}
							break;
						}
					}

				}
				con.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}

		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static Property getPropertyByID(FlexRent flexrent,String id) throws InvalidIdException //checek id exist
	{
		Property property= null;
		int i,k=0;
		for(i=0;i<flexrent.getProperty().size();i++)
		{
			
			if(flexrent.getProperty().get(i).getPid().compareTo(id)==0)
			{
				property=flexrent.getProperty().get(i);
				k=1;
				break;
			}
			
			
		}
		
		if(k==0)
			throw new InvalidIdException("Error! Property id not exist!!!");
		return property;
	}
}

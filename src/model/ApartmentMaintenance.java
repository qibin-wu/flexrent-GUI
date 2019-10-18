package model;

import controller.DBException;


public interface ApartmentMaintenance  {
	public boolean completeMaintenance()throws DBException;
}

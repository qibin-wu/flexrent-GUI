package model;
import controller.DBException;
import controller.completeMaintenanceException;
import utilities.DateTime;

public interface SuiteMaintenance {
	public void completeMaintenance(DateTime completionDate) throws completeMaintenanceException, DBException;
}

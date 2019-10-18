package controller;

public class performMaintenanceException extends Exception {
	
	private String errMsg;
	public performMaintenanceException(String errMsg) {
	      super(errMsg); 
	      this.errMsg=errMsg;
	     
	  }
	public String getErrMsg() {
		return errMsg;
	} 

}










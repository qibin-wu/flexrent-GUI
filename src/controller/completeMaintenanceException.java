package controller;

public class completeMaintenanceException extends Exception{
	
	private String errMsg;
	public completeMaintenanceException(String errMsg) {
	      super(errMsg); 
	      this.errMsg=errMsg;
	     
	  }
	public String getErrMsg() {
		return errMsg;
	} 

}




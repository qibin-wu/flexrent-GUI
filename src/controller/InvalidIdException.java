package controller;

public class InvalidIdException extends Exception {
	
	private String errMsg;
	public InvalidIdException(String errMsg) {
	      super(errMsg); 
	      this.errMsg=errMsg;
	     
	  }
	public String getErrMsg() {
		return errMsg;
	} 

}






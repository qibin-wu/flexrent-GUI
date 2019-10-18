package controller;

public class addPropertyException extends Exception{
	
	private String errMsg;
	public addPropertyException(String errMsg) {
	      super(errMsg); 
	      this.errMsg=errMsg;
	     
	  }
	public String getErrMsg() {
		return errMsg;
	} 

}




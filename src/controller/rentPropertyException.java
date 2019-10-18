package controller;

public class rentPropertyException extends Exception{
	private String errMsg;
	public rentPropertyException(String errMsg) {
	      super(errMsg); 
	      this.errMsg=errMsg;
	     
	  }
	public String getErrMsg() {
		return errMsg;
	} 

}

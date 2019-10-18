package controller;

public class returnPropertyException extends Exception {
	private String errMsg;
	public returnPropertyException(String errMsg) {
	      super(errMsg); 
	      this.errMsg=errMsg;
	     
	  }
	public String getErrMsg() {
		return errMsg;
	} 
}

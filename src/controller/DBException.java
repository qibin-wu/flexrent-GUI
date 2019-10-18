package controller;

public class DBException extends Exception {

	private String errMsg;
	public DBException(String errMsg) {
	      super(errMsg); 
	      this.errMsg=errMsg;
	     
	  }
	public String getErrMsg() {
		return errMsg;
	} 
}

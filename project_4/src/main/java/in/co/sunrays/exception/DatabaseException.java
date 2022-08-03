package in.co.sunrays.exception;

/**
 * DatabaseException is propogated by DAO classes when an unhandled Database
 * exception occurred
 * 
 * @author Shashank
 *
 */
public class DatabaseException extends Exception {
	
	//: ErrorMessage
	
	public DatabaseException(String msg) {
		
		super(msg);
	}

}

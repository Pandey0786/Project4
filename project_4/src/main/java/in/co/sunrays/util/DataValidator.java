package in.co.sunrays.util;

import java.util.Date;

/**
 * the class which validate input data enter by user
 * 
 * @author Shashank
 *
 */

public class DataValidator {

	/**
	 * Checks if value is Null
	 *
	 *
	 */
	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
     * Checks if value is NOT Null
     *
     * 
     */
	public static boolean isNotNull(String val) {
		return !isNull(val);
	}
	  /**
     * Checks if value is an Integer
     *
     * 
     */
	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				int i = Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}
	 /**
     * Checks if value is Long
     *
     *
     */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				long i = Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}
	 /**
     * Checks if value is valid Email ID
     *
     * 
     */
	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}
	 /**
     * Checks if value is Date
     *
     *
     */
	public static boolean isDate(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}
	  /**
     * Checks if value is Mobile Number
     * 
     * 
     */
	public static boolean isMobileNo(String val) {

		String mobreg = "^[6-9][0-9]{9}$";

		if (isNotNull(val) && val.matches(mobreg)) {

			return true;
		} else {
			return false;
		}
	}
	 /**
     * checks if value is Name
     * 
     */
	public static boolean isName(String val) {

		String namereg = "^[^-\\s][\\p{L} .']+$";

		if (DataValidator.isNotNull(val)) {
			boolean check = val.matches(namereg);
			return check;
		} else {
			return false;
		}
	}
	 /**
     * check if value is Valid Name
     *
     */
	public static boolean isValidName(String val) {

		String namereg = "^[a-zA-Z_-]+$";

		if (DataValidator.isNotNull(val)) {
			boolean check = val.matches(namereg);

			return true;
		} else {
			return false;
		}
	}
	 
    /**
     * check if value is password
     * 
     */
	public static boolean isPassword(String val) {

		String pass = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})";
		System.out.println("Cher 1");

		if (DataValidator.isNotNull(pass)) {
			boolean check = val.matches(pass);
			System.out.println(check);
			return check;

		} else {
			System.out.println("else pass");

			return false;
		}
	}
	   
    /**
     * check if value is Roll no
     * 
     */
	public static boolean isRollNo(String val) {

		String roll = "^[0-9]{2}[A-Z]{2}[0-9]{2,6}$";

		if (DataValidator.isNotNull(roll)) {
			boolean check = val.matches(roll);
			return check;
		} else {
			return false;
		}
	}
	  /**
     * check if value is validate age
     * 
     */
	public static boolean isvalidateAge(String val) {

		Date today = new Date();
		Date enterDate = DataUtility.getDate(val);

		int age = today.getYear() - enterDate.getYear();

		if (age > 18 && isNotNull(val)) {
			return true;
		} else {
			return false;
		}
	}
	 /**
     * Test above methods
     *
     *
     */
	public static void main(String[] args) {

	}

}

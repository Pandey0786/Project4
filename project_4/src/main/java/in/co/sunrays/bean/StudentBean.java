package in.co.sunrays.bean;

import java.util.Date;

/**
 * Student JavaBean encapsulates Student attributes
 * 
 * @author Shashank
 */

public class StudentBean extends BaseBean{

	/**
	 * Student's firstname
	 *
	 */
	private String firstName;
	/**
	 * Student's last name
	 *
	 */
	private String lastName;
	/**
	 * Student Date of birth
	 *
	 */
	private Date dob;
	/**
	 * Student Moblie number
	 *
	 */
	private long mobileNo;
	/**
	 * Student email
	 *
	 */
	private String email;
	/**
	 * College Id
	 *
	 */
	private long collegeId;
	/**
	 * Name of college
	 *
	 */
	private String collegeName;

	public StudentBean() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getkey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getvalue() {
		// TODO Auto-generated method stub
		return firstName;
	}

	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}

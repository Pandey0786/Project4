package in.co.sunrays.bean;

/**
 *  Marksheet JavaBean encapsulates Marksheet attributes
 * 
 * @author Shashank
 *
 */

public class MarksheetBean extends BaseBean {

	 /**
     * Rollno of Student
     **/
	private String rollNo;
	 /**
     * Id of Student
     *
     **/
	private long studentId;
	 /**
     * Name of Student
     **/
	private String name;
	 /**
     * Physics marks of Student
     **/
	private Integer physics;
	/**
     * Chemistry marks of Student
     **/
	private Integer chemistry;
	 /**
     * Maths marks of Student
     **/
	private Integer maths;
	
	public MarksheetBean() {
	}
	
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPhysics() {
		return physics;
	}
	public void setPhysics(Integer physics) {
		this.physics = physics;
	}
	public Integer getChemistry() {
		return chemistry;
	}
	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}
	public Integer getMaths() {
		return maths;
	}
	public void setMaths(Integer maths) {
		this.maths = maths;
	}
	public String getkey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	public String getvalue() {
		// TODO Auto-generated method stub
		return rollNo;
	}
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}

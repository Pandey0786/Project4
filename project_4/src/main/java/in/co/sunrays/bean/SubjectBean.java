package in.co.sunrays.bean;

/**
 * Subject JavaBean encapsulates Subject attributes
 * @author Shashank
 *
 */

public class SubjectBean extends BaseBean {

	/**
	 * subjectname of subject
	 *
	 */
		private String Subject_Name;

	/**
	 * course name of subject
	 *
	 */
		private String Course_Name;
    /**
     * 
     * Course Id of subject
     * 
     */
		private int Course_Id;

	/**
	 * description of subject
	 *
	 */
		private String Description;

	public String getSubject_Name() {
		return Subject_Name;
	}

	public void setSubject_Name(String subject_Name) {
		Subject_Name = subject_Name;
	}

	public String getCourse_Name() {
		return Course_Name;
	}

	public void setCourse_Name(String course_Name) {
		Course_Name = course_Name;
	}

	public int getCourse_Id() {
		return Course_Id;
	}

	public void setCourse_Id(int course_Id) {
		Course_Id = course_Id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getkey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getvalue() {
		// TODO Auto-generated method stub
		return Subject_Name;
	}

	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

		
	
}

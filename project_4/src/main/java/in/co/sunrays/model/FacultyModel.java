package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.bean.FacultyBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;

/**
 * JDBC Implementation of FacultyModel
 * 
 * @author Shashank
 *
 */

public class FacultyModel {

	/**
	 * Next PK of Faculty
	 *
	 */

	public Integer nextPK() throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Getting the PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	/**
	 * Add a Faculty
	 *
	 */

	public long add(FacultyBean fb) throws Exception {

		Connection conn = null;

		int pk = 0;

		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findByPK(fb.getCollege_id());
		String collegeName = cb.getName();

		CourseModel crsm = new CourseModel();
		CourseBean crsb = crsm.findByPk(fb.getCourse_id());
		String courseName = crsb.getCourse_Name();

		SubjectModel sm = new SubjectModel();
		SubjectBean sb = sm.findByPK(fb.getSubject_id());
		String subjectname = sb.getSubject_Name();

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, fb.getFirst_Name());
			ps.setString(3, fb.getLast_Name());
			ps.setString(4, fb.getGender());
			ps.setDate(5, new java.sql.Date(fb.getDOJ().getTime()));
			ps.setString(6, fb.getQualification());
			ps.setString(7, fb.getEmail_id());
			ps.setString(8, fb.getMobile_No());
			ps.setInt(9, fb.getCollege_id());
			ps.setString(10, collegeName);
			ps.setInt(11, fb.getCourse_id());
			ps.setString(12, courseName);
			ps.setInt(13, fb.getSubject_id());
			ps.setString(14, subjectname);
			ps.setString(15, fb.getCreatedBy());
			ps.setString(16, fb.getModifiedBy());
			ps.setTimestamp(17, fb.getCreatedDatetime());
			ps.setTimestamp(18, fb.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();

			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			System.out.println("INSERTION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");

		return pk;

	}

	/**
	 * delete a Faculty
	 *
	 */

	public void delete(FacultyBean fb) throws ApplicationException {

		// log.debug("Faculty Model Delete method End");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			ps.setLong(1, fb.getId());
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			// log.error("DATABASE EXCEPTION ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Faculty Model rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Faculty Model Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Update a Faculty
	 *
	 */

	public void update(FacultyBean fb) throws Exception {

		// log.debug("Model update Started");
		Connection conn = null;

		CollegeModel cmodel = new CollegeModel();
		CollegeBean cbean = cmodel.findByPK(fb.getCollege_id());
		String collegeName = cbean.getName();

		SubjectModel smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(fb.getSubject_id());
		String subjectname = sbean.getSubject_Name();

		FacultyBean beanExist = findByEmail(fb.getFirst_Name());
		if (beanExist != null && fb.getId() != fb.getId()) {
			throw new DuplicateRecordException("faculty already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_FACULTY SET  FIRST_NAME=?, LAST_NAME=?, GENDER=?, DOJ=?,QUALIFICATION=?, EMAIL_ID=?, MOBILE_NO=? , COLLEGE_ID=?, COLLEGE_NAME=?,COURSE_ID=?,COURSE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?, CREATED_BY=? , MODIFIED_BY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=?  WHERE ID= ? ");

			ps.setString(1, fb.getFirst_Name());
			ps.setString(2, fb.getLast_Name());
			ps.setString(3, fb.getGender());
			ps.setDate(4, new java.sql.Date(fb.getDOJ().getTime()));
			ps.setString(5, fb.getQualification());
			ps.setString(6, fb.getEmail_id());
			ps.setString(7, fb.getMobile_No());
			ps.setInt(8, fb.getCollege_id());
			ps.setString(9, collegeName);
			ps.setInt(10, fb.getCourse_id());
			ps.setString(11, fb.getCourse_Name());
			ps.setInt(12, fb.getSubject_id());
			ps.setString(13, subjectname);
			ps.setString(14, fb.getCreatedBy());
			ps.setString(15, fb.getModifiedBy());
			ps.setTimestamp(16, fb.getCreatedDatetime());
			ps.setTimestamp(17, fb.getModifiedDatetime());
			ps.setLong(18, fb.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("DATABASE EXCEPTION ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception in rollback faculty model .." +
				// ex.getMessage());
			}
			// throw new ApplicationException("Exception in faculty model Update Method..");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Find Faculty by Email
	 *
	 */

	public FacultyBean findByEmail(String EmailId) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
		Connection conn = null;
		FacultyBean fb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ps.setString(1, EmailId);
			System.out.println("resultset" + EmailId);
			ResultSet rs = ps.executeQuery();
			System.out.println(" faculty  find by name 1 while");
			while (rs.next()) {
				fb = new FacultyBean();
				fb.setId(rs.getLong(1));
				fb.setCollege_id(rs.getInt(2));
				fb.setSubject_id(rs.getInt(3));
				fb.setCourse_id(rs.getInt(4));
				fb.setFirst_Name(rs.getString(5));
				fb.setLast_Name(rs.getString(6));
				fb.setGender(rs.getString(7));
				fb.setDOJ(rs.getDate(8));
				fb.setEmail_id(rs.getString(9));
				fb.setMobile_No(rs.getString(10));
				fb.setCollege_Name(rs.getString(11));
				fb.setCollege_Name(rs.getString(12));
				fb.setSubject_Name(rs.getString(13));
				fb.setCreatedBy(rs.getString(14));
				fb.setModifiedBy(rs.getString(15));
				fb.setCreatedDatetime(rs.getTimestamp(16));
				fb.setModifiedDatetime(rs.getTimestamp(17));
				fb.setQualification(rs.getString(18));
				// System.out.println(" faculty find by name 3");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
//		System.out.println(" faculty  find by name 4");

		return fb;

	}

	/**
	 * Find Faculty by PK
	 *
	 */

	public FacultyBean findByPK(int pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		Connection conn = null;
		FacultyBean fb = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				fb = new FacultyBean();
				fb.setId(rs.getInt(1));
				fb.setFirst_Name(rs.getString(2));
				fb.setLast_Name(rs.getString(3));
				fb.setGender(rs.getString(4));
				fb.setDOJ(rs.getDate(5));
				fb.setQualification(rs.getString(6));
				fb.setEmail_id(rs.getString(7));
				fb.setMobile_No(rs.getString(8));
				fb.setCollege_id(rs.getInt(9));
				fb.setCollege_Name(rs.getString(10));
				fb.setCourse_id(rs.getInt(11));
				fb.setCourse_Name(rs.getString(12));
				fb.setSubject_id(rs.getInt(13));
				fb.setSubject_Name(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return fb;
	}

	/**
	 * Search Faculty
	 *
	 */
	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Faculty with pagination
	 *
	 */

	public List search(FacultyBean fb, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE true=true");
		if (fb != null) {
			if (fb.getId() > 0) {
				sql.append(" AND id = " + fb.getId());
			}
			if (fb.getCollege_id() > 0) {
				sql.append(" AND college_Id = " + fb.getCollege_id());
			}
			if (fb.getCollege_Name() != null && fb.getCollege_Name().length() > 0) {
				sql.append(" AND college_Name like '" + fb.getCollege_Name() + "%'");
			}
			if (fb.getCourse_id() > 0) {
				sql.append(" AND course_Id = " + fb.getCourse_id());
			}
			if (fb.getCourse_Name() != null && fb.getCourse_Name().length() > 0) {
				sql.append(" AND course_Name like '" + fb.getCourse_Name() + "%'");
			}
			if (fb.getSubject_id() > 0) {
				sql.append(" AND Subject_Id = " + fb.getSubject_id());
			}
			if (fb.getSubject_Name() != null && fb.getSubject_Name().length() > 0) {
				sql.append(" AND subject_Name like '" + fb.getSubject_Name() + "%'");
			}
			if (fb.getFirst_Name() != null && fb.getFirst_Name().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + fb.getFirst_Name() + "%'");
			}
			if (fb.getLast_Name() != null && fb.getLast_Name().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + fb.getLast_Name() + "%'");
			}

			if (fb.getEmail_id() != null && fb.getEmail_id().length() > 0) {
				sql.append(" AND Email_Id like '" + fb.getEmail_id() + "%'");
			}

			if (fb.getGender() != null && fb.getGender().length() > 0) {
				sql.append(" AND Gender like '" + fb.getGender() + "%'");
			}

			if (fb.getMobile_No() != null && fb.getMobile_No().length() > 0) {
				sql.append(" AND Mobile_No like '" + fb.getMobile_No() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("final sql  " + sql);

		Connection conn = null;
		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				fb = new FacultyBean();
				fb.setId(rs.getLong(1));
				fb.setFirst_Name(rs.getString(2));
				fb.setLast_Name(rs.getString(3));
				fb.setGender(rs.getString(4));
				fb.setDOJ(rs.getDate(5));
				fb.setQualification(rs.getString(6));
				fb.setEmail_id(rs.getString(7));
				fb.setMobile_No(rs.getString(8));
				fb.setCollege_id(rs.getInt(9));
				fb.setCollege_Name(rs.getString(10));
				fb.setCourse_id(rs.getInt(11));
				fb.setCourse_Name(rs.getString(12));
				fb.setSubject_id(rs.getInt(13));
				fb.setSubject_Name(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));

				list.add(fb);

			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception .. " , e);
			e.printStackTrace();
			// throw new ApplicationException("Exception : Exception in Search method of
			// Faculty Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
	
	
	/**
	 * List of Faculty 
	 *
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}
	/**
	 * List of Faculty with pagination
	 *
	 */
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
		Connection conn = null;
		ArrayList list = new ArrayList();

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FacultyBean fb = new FacultyBean();
				fb.setId(rs.getLong(1));
				fb.setFirst_Name(rs.getString(2));
				fb.setLast_Name(rs.getString(3));
				fb.setGender(rs.getString(4));
				fb.setDOJ(rs.getDate(5));
				fb.setQualification(rs.getString(6));
				fb.setEmail_id(rs.getString(7));
				fb.setMobile_No(rs.getString(8));
				fb.setCollege_id(rs.getInt(9));
				fb.setCollege_Name(rs.getString(10));
				fb.setCourse_id(rs.getInt(11));
				fb.setCourse_Name(rs.getString(12));
				fb.setSubject_id(rs.getInt(13));
				fb.setSubject_Name(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));
				list.add(fb);
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in list method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		
		return list;
		
		
	}
	
}

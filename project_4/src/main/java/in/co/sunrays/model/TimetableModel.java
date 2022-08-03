package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.bean.TimetableBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.util.JDBCDataSource;

/**
 * JDBC Implementation of TimetableModel
 * 
 * @author Shashank
 *
 */

public class TimetableModel {

	/**
	 * Find next PK
	 *
	 */

	public Integer nextPK() throws ApplicationException {
		// log.debug("Timetable model nextPk method Started ");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception ...", e);
			throw new ApplicationException("Exception in NextPk of TIMETABLE Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * add a timetable
	 *
	 */

	public int add(TimetableBean tb) throws Exception {
		// log.debug("TimeTable model Add method End");
		Connection conn = null;
		int pk = 0;

		CourseModel coumodel = new CourseModel();
		CourseBean coubean = coumodel.findByPk(tb.getCourse_Id());
		String courseName = coubean.getCourse_Name();

		SubjectModel smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(tb.getSubject_Id());
		String subjectName = sbean.getSubject_Name();

		// TimetableBean bean11 = checkBycds(tb.getCourse_Id(),tb.getSemester(), new
		// java.sql.Date(tb.getExam_Date().getTime()));
		// TimetableBean bean12 = checkBycss(tb.getCourse_Id(), tb.getSubject_Id(),
		// tb.getSemester());
		/*
		 * if(bean11 != null || bean12 != null){ throw new
		 * DuplicateRecordException("TimeTable Already Exsist"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, courseName);
			ps.setInt(3, tb.getCourse_Id());
			ps.setString(4, subjectName);
			ps.setInt(5, tb.getSubject_Id());
			ps.setDate(6, new java.sql.Date(tb.getExam_Date().getTime()));
			ps.setString(7, tb.getExam_Time());
			ps.setString(8, tb.getSemester());
			ps.setString(9, tb.getCreatedBy());
			ps.setString(10, tb.getModifiedBy());
			ps.setTimestamp(11, tb.getCreatedDatetime());
			ps.setTimestamp(12, tb.getModifiedDatetime());
			ps.executeUpdate();

			conn.commit();
			ps.close();
			System.out.println("INSERTION DONE");

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in the Rollback of TIMETABLE Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Add method of TIMETABLE Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	/**
	 * delete a timetable
	 *
	 */

	public void delete(TimetableBean tb) throws ApplicationException {
		// log.debug("TIMETABLE Model Delete method Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			ps.setLong(1, tb.getId());
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception ...", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Delte Method of TIMETABLE Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of TIMETABLE Model");
		} finally {
			System.out.println("DELETION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("TIMETABLE Model Delete method End");
	}

	/**
	 * update a timetable
	 * 
	 *
	 */
	public void update(TimetableBean tb) throws Exception {
		// log.debug("TIMETABLE Model update method Started");
		Connection conn = null;

		CourseModel coumodel = new CourseModel();
		CourseBean coubean = coumodel.findByPk(tb.getCourse_Id());
		String courseName = coubean.getCourse_Name();

		SubjectModel smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(tb.getSubject_Id());
		String subjectName = sbean.getSubject_Name();

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_TIMETABLE SET COURSE_NAME=?,COURSE_ID=?,SUBJECT_NAME=?,SUBJECT_ID=?,EXAM_DATE=?,EXAM_TIME=?,SEMESTER=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, courseName);
			ps.setInt(2, tb.getCourse_Id());
			ps.setString(3, subjectName);
			ps.setInt(4, tb.getSubject_Id());
			ps.setDate(5, new java.sql.Date(tb.getExam_Date().getTime()));
			ps.setString(6, tb.getExam_Time());
			ps.setString(7, tb.getSemester());
			ps.setString(8, tb.getCreatedBy());
			ps.setString(9, tb.getModifiedBy());
			ps.setTimestamp(10, tb.getCreatedDatetime());
			ps.setTimestamp(11, tb.getModifiedDatetime());
			ps.setLong(12, tb.getId());

			ps.executeUpdate();
			System.out.println("--------------------");
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Update Method of TimeTable Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in update Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * find a timetable by subjectname
	 *
	 */

	public TimetableBean findByName(String name) throws ApplicationException {
		// log.debug("TimeTable Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Subject_Name = ?");
		Connection conn = null;
		TimetableBean tb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tb = new TimetableBean();

				tb.setId(rs.getInt(1));
				tb.setCourse_Name(rs.getString(2));
				tb.setCourse_Id(rs.getInt(3));
				tb.setSubject_Name(rs.getString(4));
				tb.setSubject_Id(rs.getInt(5));
				tb.setExam_Date(rs.getDate(6));
				tb.setExam_Time(rs.getString(7));
				tb.setSemester(rs.getString(8));
				tb.setCreatedBy(rs.getString(9));
				tb.setModifiedBy(rs.getString(10));
				tb.setCreatedDatetime(rs.getTimestamp(11));
				tb.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByName Method of TimeTable Model");
		} finally {
			System.out.println("SEARCH BY NAME DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("TimeTable Model findByName method End");
		return tb;
	}

	/**
	 * find a timetable by PK
	 *
	 */
	public TimetableBean findByPK(int pk) throws ApplicationException {
		// log.debug("TimeTable Model findBypk method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		Connection conn = null;
		TimetableBean tb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tb = new TimetableBean();

				tb.setId(rs.getInt(1));
				tb.setCourse_Name(rs.getString(2));
				tb.setCourse_Id(rs.getInt(3));
				tb.setSubject_Name(rs.getString(4));
				tb.setSubject_Id(rs.getInt(5));
				tb.setExam_Date(rs.getDate(6));
				tb.setExam_Time(rs.getString(7));
				tb.setSemester(rs.getString(8));
				tb.setCreatedBy(rs.getString(9));
				tb.setModifiedBy(rs.getString(10));
				tb.setCreatedDatetime(rs.getTimestamp(11));
				tb.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("TimeTable Model findByPk method End");
		return tb;
	}

	/**
	 * SEARCH a timetable
	 *
	 */

	public List search(TimetableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * search a timetable with pagination
	 *
	 */

	public List search(TimetableBean tb, int pageNo, int pageSize) throws ApplicationException {

		// log.debug("TimeTable Model search method Started");

		Connection conn = null;
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");

		if (tb != null) {

			if (tb.getId() > 0) {
				sql.append(" AND id = " + tb.getId());
			}

			if (tb.getCourse_Id() > 0) {
				sql.append(" AND Course_ID = " + tb.getCourse_Id());
			}

			if (tb.getSubject_Id() > 0) {
				sql.append(" AND Subject_ID = " + tb.getSubject_Id());
			}

			if (tb.getExam_Date() != null && tb.getExam_Date().getTime() > 0) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				sql.append(" AND Exam_Date = '" + sdf.format(tb.getExam_Date()) + "'");
			}

			if (tb.getCourse_Name() != null && tb.getCourse_Name().length() > 0) {
				sql.append(" AND Course_Name like '" + tb.getCourse_Name() + "%'");
			}

			if (tb.getSubject_Name() != null && tb.getSubject_Name().length() > 0) {
				sql.append(" AND Subject_Name like '" + tb.getSubject_Name() + "%'");
			}
		}

		// Page Size is greater then Zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql queryy " + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tb = new TimetableBean();

				tb.setId(rs.getInt(1));
				tb.setCourse_Name(rs.getString(2));
				tb.setCourse_Id(rs.getInt(3));
				tb.setSubject_Name(rs.getString(4));
				tb.setSubject_Id(rs.getInt(5));
				tb.setExam_Date(rs.getDate(6));
				tb.setExam_Time(rs.getString(7));
				tb.setSemester(rs.getString(8));
				tb.setCreatedBy(rs.getString(9));
				tb.setModifiedBy(rs.getString(10));
				tb.setCreatedDatetime(rs.getTimestamp(11));
				tb.setModifiedDatetime(rs.getTimestamp(12));
				list.add(tb);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in search Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("TimeTable Model search method End");

		return list;

	}

	/**
	 * list of timetable
	 *
	 */
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * list of timetable with pagination
	 *
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {

		// log.debug("TimeTable Model list method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE ");

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		System.out.println("------->>>>>>>>>>---" + sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				TimetableBean tb = new TimetableBean();

				tb.setId(rs.getInt(1));
				tb.setCourse_Name(rs.getString(2));
				tb.setCourse_Id(rs.getInt(3));
				tb.setSubject_Name(rs.getString(4));
				tb.setSubject_Id(rs.getInt(5));
				tb.setExam_Date(rs.getDate(6));
				tb.setExam_Time(rs.getString(7));
				tb.setSemester(rs.getString(8));
				tb.setCreatedBy(rs.getString(9));
				tb.setModifiedBy(rs.getString(10));
				tb.setCreatedDatetime(rs.getTimestamp(11));
				tb.setModifiedDatetime(rs.getTimestamp(12));
				list.add(tb);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	/**
	 * Check by COLLEGE/SUBJECT/SEMESTER - CSS
	 *
	 * 
	 */

	public TimetableBean checkBycss(int CourseId, int SubjectId, String sem) throws ApplicationException {

		System.out.println("in from css.........................<<<<<<<<<<<>>>> ");
		Connection conn = null;
		TimetableBean tb = null;
		// java.util.Date ExamDAte,String ExamTime

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Subject_ID=? AND Semester=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, sem);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tb = new TimetableBean();
				tb.setId(rs.getInt(1));
				tb.setCourse_Name(rs.getString(2));
				tb.setCourse_Id(rs.getInt(3));
				tb.setSubject_Name(rs.getString(4));
				tb.setSubject_Id(rs.getInt(5));
				tb.setExam_Date(rs.getDate(6));
				tb.setExam_Time(rs.getString(7));
				tb.setSemester(rs.getString(8));
				tb.setCreatedBy(rs.getString(9));
				tb.setModifiedBy(rs.getString(10));
				tb.setCreatedDatetime(rs.getTimestamp(11));
				tb.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Timetable Model list method End");
		System.out.println("out from css.........................<<<<<<<<<<<>>>> ");

		return tb;

	}

	/**
	 * Check by cds - COLLEGE/SEMESTER/EXAMDATE
	 *
	 */

	public TimetableBean checkBycds(int CourseId, String sem, Date ExamDate) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Semester=? AND Exam_Date=?");

		Connection conn = null;
		TimetableBean tb = null;
		Date ExDate = new Date(ExamDate.getTime());

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setString(2, sem);
			ps.setDate(3, (java.sql.Date) ExamDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tb = new TimetableBean();
				tb.setId(rs.getInt(1));
				tb.setCourse_Name(rs.getString(2));
				tb.setCourse_Id(rs.getInt(3));
				tb.setSubject_Name(rs.getString(4));
				tb.setSubject_Id(rs.getInt(5));
				tb.setExam_Date(rs.getDate(6));
				tb.setExam_Time(rs.getString(7));
				tb.setSemester(rs.getString(8));
				tb.setCreatedBy(rs.getString(9));
				tb.setModifiedBy(rs.getString(10));
				tb.setCreatedDatetime(rs.getTimestamp(11));
				tb.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return tb;

	}

	/**
	 * Check by semester
	 *
	 */

	public static TimetableBean checkBysemester(long CourseId, long SubjectId, String semester, Date ExamDate) {

		TimetableBean bean = null;

		Date ExDate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " SEMESTER=? AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, (java.sql.Date) ExDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;

	}

	/**
	 * Check by course name.
	 *
	 * 
	 */

	public static TimetableBean checkByCourseName(int CourseId, Date ExamDate) {

		Connection conn = null;
		TimetableBean bean = null;

		Date Exdate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setDate(2, (java.sql.Date) Exdate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Id(rs.getInt(2));
				bean.setCourse_Name(rs.getString(3));
				bean.setSubject_Id(rs.getInt(4));
				bean.setSubject_Name(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExam_Date(rs.getDate(7));
				bean.setExam_Time(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;

	}

}

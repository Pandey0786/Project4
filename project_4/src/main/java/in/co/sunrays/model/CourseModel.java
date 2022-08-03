package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DatabaseException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;

/**
 * JDBC Implementation of CourseModel
 * 
 * @author Shashank
 *
 */

public class CourseModel {

	/**
	 * Find next Primary key of Course
	 *
	 */

	public long nextPK() throws DatabaseException {

		Connection conn = null;

		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

			throw new DatabaseException("Exception : Exception in getting Pk");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	/**
	 * Add a Course
	 *
	 */

	public long add(CourseBean crsb) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		CourseBean duplicateCourseName = findByName(crsb.getCourse_Name());

		if (duplicateCourseName != null) {

			throw new DuplicateRecordException("Course Name already Exist");
		}

		try {

			conn = JDBCDataSource.getConnection();
			pk =  (int) nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, crsb.getCourse_Name());
			ps.setString(3, crsb.getDescription());
			ps.setString(4, crsb.getDuration());
			ps.setString(5, crsb.getCreatedBy());
			ps.setString(6, crsb.getModifiedBy());
			ps.setTimestamp(7, crsb.getCreatedDatetime());
			ps.setTimestamp(8, crsb.getModifiedDatetime());

			ps.executeUpdate();

			conn.commit();
			ps.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();
			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : add Rollback Exception.." + ex.getMessage());
			}

			throw new ApplicationException("Exception in Course Add method");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	/**
	 * Update a Course
	 *
	 */

	public void update(CourseBean crsb) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CourseBean beanExist = findByName(crsb.getCourse_Name());

		if (beanExist != null && beanExist.getId() != crsb.getId()) {

			throw new DuplicateRecordException("Course Already Exist");

		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_COURSE SET COURSE_NAME=?,DESCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, crsb.getCourse_Name());
			ps.setString(2, crsb.getDescription());
			ps.setString(3, crsb.getDuration());
			ps.setString(4, crsb.getCreatedBy());
			ps.setString(5, crsb.getModifiedBy());
			ps.setTimestamp(6, crsb.getCreatedDatetime());
			ps.setTimestamp(7, crsb.getModifiedDatetime());
			ps.setLong(8, crsb.getId());

			ps.executeUpdate();

			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				conn.rollback();

			} catch (Exception ex) {

				ex.printStackTrace();

				throw new ApplicationException("Exception : Exception in Rollback.." + ex.getMessage());
			}

			throw new ApplicationException("Exception in Updating the Course Model");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

	}

	/**
	 * Delete a Course
	 *
	 */

	public void delete(CourseBean crsb) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			ps.setLong(1, crsb.getId());

			ps.executeUpdate();

			conn.commit();
			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Exception in Rollback Method" + ex.getMessage());
			}

			throw new ApplicationException("Exception in Delete Method");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Find by name of a Course
	 *
	 */

	public CourseBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE COURSE_NAME=?");
		CourseBean crsb = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				crsb = new CourseBean();

				crsb.setId(rs.getLong(1));
				crsb.setCourse_Name(rs.getString(2));
				crsb.setDescription(rs.getString(3));
				crsb.setDuration(rs.getString(4));
				crsb.setCreatedBy(rs.getString(5));
				crsb.setModifiedBy(rs.getString(6));
				crsb.setCreatedDatetime(rs.getTimestamp(7));
				crsb.setModifiedDatetime(rs.getTimestamp(8));

			}

			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return crsb;
	}

	/**
	 * Find by primary key of a College
	 *
	 */

	public CourseBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");

		CourseBean crsb = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				crsb = new CourseBean();
				crsb.setId(rs.getLong(1));
				crsb.setCourse_Name(rs.getString(2));
				crsb.setDescription(rs.getString(3));
				crsb.setDuration(rs.getString(4));
				crsb.setCreatedBy(rs.getString(5));
				crsb.setModifiedBy(rs.getString(6));
				crsb.setCreatedDatetime(rs.getTimestamp(7));
				crsb.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in the findbyPk method");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return crsb;
	}

	/**
	 * Search of a College
	 *
	 */

	public List search(CourseBean bean) throws ApplicationException {

		return search(bean, 0, 0);
	}

	public List search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		
		if (bean != null) {
		
			if (bean.getId() > 0) {
			
				sql.append(" AND id = " + bean.getId());
			}
			
				if (bean.getCourse_Name() != null && bean.getCourse_Name().length() > 0) {
			
				sql.append(" AND COURSE_NAME  like '" + bean.getCourse_Name() + "%'");
			}
			
			if (bean.getDuration() != null && bean.getDuration().length() > 0) {
			
				sql.append(" AND DURATION like '" + bean.getDuration() + "%'");
			}
			
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
			
				sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
			}
		}
	
		if (pageSize > 0) {
		
			pageNo = (pageNo - 1) * pageSize;
			
			sql.append(" LIMIT " + pageNo + " , " + pageSize);
		}
		
		System.out.println(sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
		
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
		
			throw new ApplicationException("Exception: Exception in search Course");
	
		} finally {

		
			JDBCDataSource.closeConnection(conn);
		}
		
		return list;
}

	/**
	 * Find list of a Course
	 *
	 */
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}
	
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		
	
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE ");

		if (pageSize > 0) {
		
			pageNo = (pageNo - 1) * pageSize;
			
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
			
				CourseBean crsb = new CourseBean();
				
				crsb.setId(rs.getLong(1));
				crsb.setCourse_Name(rs.getString(2));
				crsb.setDescription(rs.getString(3));
				crsb.setDuration(rs.getString(4));
				crsb.setCreatedBy(rs.getString(5));
				crsb.setModifiedBy(rs.getString(6));
				crsb.setCreatedDatetime(rs.getTimestamp(7));
				crsb.setModifiedDatetime(rs.getTimestamp(8));
				
				list.add(crsb);
			}
		
			rs.close();
		
		} catch (Exception e) {
			
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in CourseModel List method " + e.getMessage());
	
		} finally {
	
			JDBCDataSource.closeConnection(conn);
		}
		
		
		return list;
		
	}
	
}
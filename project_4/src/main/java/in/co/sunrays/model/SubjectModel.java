package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;

/**
 * JDBC Implementation of SubjectModel
 * 
 * @author Shashank
 *
 */

public class SubjectModel {

	/**
	 * Find next PK
	 *
	 */

	public long nextPK() throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				pk = rs.getInt(1);
			}

			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
			// log.error("database Exception ...", e);

			throw new ApplicationException("Exception in NextPk of subject Model");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	/**
	 * add a subject
	 *
	 */

	public int add(SubjectBean sub) throws ApplicationException, DuplicateRecordException {

		// log.debug("Model add Started");

		Connection conn = null;
		int pk = 0;

		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(sub.getCourse_Id());

		String courseName = coursebean.getCourse_Name();

		System.out.println("----------------->" + courseName);

		SubjectBean DuplicateSubjectName = findByName(sub.getSubject_Name());

		if (DuplicateSubjectName != null) {

			throw new DuplicateRecordException("Subject name Already Exsist");
		}

		try {

			conn = JDBCDataSource.getConnection();
			pk = (int) nextPK();
			System.out.println(pk + " in ModelJDBC");

			conn.setAutoCommit(false); // Begin transaction

			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, sub.getSubject_Name());
			ps.setString(3, courseName);
			ps.setInt(4, sub.getCourse_Id());
			ps.setString(5, sub.getDescription());
			ps.setString(6, sub.getCreatedBy());
			ps.setString(7, sub.getModifiedBy());
			ps.setTimestamp(8, sub.getCreatedDatetime());
			ps.setTimestamp(9, sub.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();

		} catch (Exception e) {

			e.printStackTrace();
			// log.error("Database Exception..", e);

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in add Subject");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");

		return pk;

	}

	/**
	 * update a subject
	 *
	 */

	public void update(SubjectBean sub) throws ApplicationException, DuplicateRecordException {

		// log.debug("Model update Started");

		Connection conn = null;

		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(sub.getCourse_Id());

		String courseName = coursebean.getCourse_Name();

		SubjectBean beanExist = findByName(sub.getSubject_Name());

		if (beanExist != null && sub.getId() != sub.getId()) {

			throw new DuplicateRecordException("Subject name Already Exsist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_SUBJECT SET Subject_Name=?,Course_NAME=?,Course_ID=?,Discription=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, sub.getSubject_Name());
			ps.setString(2, courseName);
			ps.setInt(3, sub.getCourse_Id());
			ps.setString(4, sub.getDescription());
			ps.setString(5, sub.getCreatedBy());
			ps.setString(6, sub.getModifiedBy());
			ps.setTimestamp(7, sub.getCreatedDatetime());
			ps.setTimestamp(8, sub.getModifiedDatetime());
			ps.setLong(9, sub.getId());

			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();

		} catch (Exception e) {

			e.printStackTrace();
			// log.error("Database Exception..", e);

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : UPDATE rollback exception " + ex.getMessage());
			}
			// throw new ApplicationException("Exception in updating Subject ");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * delete a subject
	 *
	 */

	public void delete(SubjectBean sub) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
			ps.setLong(1, sub.getId());
			ps.executeUpdate();
			conn.commit();

		} catch (Exception e) {

			e.printStackTrace();

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException(
						"Exception in Rollback of Delte Method of Subject Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of Subject Model");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * find a subject by PK
	 *
	 */

	public SubjectBean findByPK(long pk) throws ApplicationException {

		// log.debug("Subject Model findBypk method Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		Connection conn = null;
		SubjectBean sub = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				sub = new SubjectBean();

				sub.setId(rs.getLong(1));
				sub.setSubject_Name(rs.getString(2));
				sub.setCourse_Id(rs.getInt(4));
				sub.setCourse_Name(rs.getString(3));
				sub.setDescription(rs.getString(5));
				sub.setCreatedBy(rs.getString(6));
				sub.setModifiedBy(rs.getString(7));
				sub.setCreatedDatetime(rs.getTimestamp(8));
				sub.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {

			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of Subject Model");

		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Subject Model findByPk method End");

		return sub;
	}

	/**
	 * find a subject by name
	 *
	 */

	public SubjectBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE SUBJECT_NAME=?");
		Connection conn = null;
		SubjectBean stb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				stb = new SubjectBean();

				stb.setId(rs.getLong(1));
				stb.setSubject_Name(rs.getString(2));
				stb.setCourse_Id(rs.getInt(3));
				stb.setCourse_Name(rs.getString(4));
				stb.setDescription(rs.getString(5));
				stb.setCreatedBy(rs.getString(6));
				stb.setModifiedBy(rs.getString(7));
				stb.setCreatedDatetime(rs.getTimestamp(8));
				stb.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			e.printStackTrace();
			
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Subject Model findByName method End");
		return stb;
	}

	/**
	 * search a subject
	 *
	 */

	public List search(SubjectBean sub) throws ApplicationException {

		return search(sub, 0, 0);
	}

	/**
	 * search subject with pagination
	 *
	 */

	public List search(SubjectBean sub, int pageNo, int pageSize) throws ApplicationException {

		// log.debug("Subject Model search method Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1 ");

		System.out.println("model search" + sub.getId());

		if (sub != null) {

			if (sub.getId() > 0) {

				sql.append(" AND id = " + sub.getId());
			}

			if (sub.getCourse_Id() > 0) {

				sql.append(" AND Course_ID = " + sub.getCourse_Id());
			}

			if (sub.getSubject_Name() != null && sub.getSubject_Name().length() > 0) {

				sql.append(" AND Subject_Name like '" + sub.getSubject_Name() + "%'");
			}

			if (sub.getCourse_Name() != null && sub.getCourse_Name().length() > 0) {

				sql.append(" AND Course_Name like '" + sub.getCourse_Id() + "%'");
			}

			if (sub.getDescription() != null && sub.getDescription().length() > 0) {

				sql.append(" AND discription like '" + sub.getDescription() + " % ");
			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		System.out.println("sql is" + sql);

		Connection conn = null;
		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				sub = new SubjectBean();

				sub.setId(rs.getLong(1));
				sub.setSubject_Name(rs.getString(2));
				sub.setCourse_Id(rs.getInt(4));
				sub.setCourse_Name(rs.getString(3));
				sub.setDescription(rs.getString(5));
				sub.setCreatedBy(rs.getString(6));
				sub.setModifiedBy(rs.getString(7));
				sub.setCreatedDatetime(rs.getTimestamp(8));
				sub.setModifiedDatetime(rs.getTimestamp(9));

				list.add(sub);
			}
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
			// log.error("database Exception....", e);

			throw new ApplicationException("Exception in search Method of Subject Model");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	/**
	 * list of subject
	 *
	 */

	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * list of subject with pagination
	 *
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {

		// log.debug("Subject Model list method Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT ");

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;

		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				SubjectBean sub = new SubjectBean();

				sub.setId(rs.getLong(1));
				sub.setSubject_Name(rs.getString(2));
				sub.setCourse_Id(rs.getInt(4));
				sub.setCourse_Name(rs.getString(3));
				sub.setDescription(rs.getString(5));
				sub.setCreatedBy(rs.getString(6));
				sub.setModifiedBy(rs.getString(7));
				sub.setCreatedDatetime(rs.getTimestamp(8));
				sub.setModifiedDatetime(rs.getTimestamp(9));

				list.add(sub);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Subject Model list method End");
		return list;
	}

}

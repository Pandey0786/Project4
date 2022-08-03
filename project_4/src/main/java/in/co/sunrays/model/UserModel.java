package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.exception.RecordNotFoundException;
import in.co.sunrays.util.EmailBuilder;
import in.co.sunrays.util.EmailMessage;
import in.co.sunrays.util.EmailUtility;
import in.co.sunrays.util.JDBCDataSource;

/**
 * JDBC Implementation of Usermodel
 * 
 * @author Shashank
 *
 */

public class UserModel {

	private long roleId;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Find next PK
	 *
	 */

	public Integer nextPK() throws Exception {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER ");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				pk = rs.getInt(1);
			}

			rs.close();
		} catch (Exception e) {
			throw new Exception("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;

	}

	/**
	 * Add a User
	 *
	 */

	public long add(UserBean bean) throws Exception {

		Connection conn = null;
		int pk = 0;

		UserBean existbean = findbylogin(bean.getLogin());
		if (existbean != null) {
			throw new DuplicateRecordException("LOGIN ID ALREADY EXIST");
		}

		try {

			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setLong(7, bean.getMobileNo());
			pstmt.setLong(8, bean.getRoleId());
			pstmt.setInt(9, bean.getUnSuccessfulLogin());
			pstmt.setString(10, bean.getGender());
			pstmt.setTimestamp(11, bean.getLastLogin());
			pstmt.setString(12, bean.getLock());
			pstmt.setString(13, bean.getRegisteredIP());
			pstmt.setString(14, bean.getLastLoginIP());
			pstmt.setString(15, bean.getCreatedBy());
			pstmt.setString(16, bean.getModifiedBy());
			pstmt.setTimestamp(17, bean.getCreatedDatetime());
			pstmt.setTimestamp(18, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception("Exception : ADD ROLL BACK Exception" + ex.getMessage());
			}
			e.printStackTrace();
			throw new Exception("Exception : EXCEPTION IN ADD USER");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	/**
	 * delete a User
	 *
	 */

	public void delete(UserBean bean) throws Exception {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // START TRANSACTION
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // END TRANSACTION
			pstmt.close();

		} catch (Exception e) {
			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new Exception("Exception : Delete rollback exception " + ex.getMessage());

			}

			throw new Exception("Exception : Exception in delete User");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

	}

	/**
	 * find a User by loginId
	 *
	 */

	public UserBean findbylogin(String login) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		UserBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getLong(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}

			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

			throw new Exception("EXCEPTION : EXCEPTION IN GETTING USER BY LOGIN");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

		return bean;
	}

	/**
	 * find User by PK
	 *
	 */

	public UserBean findbyPK(long pk) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getLong(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

			throw new Exception("EXCEPTION : EXCEPTION IN GETTING USER BY PK");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

		return bean;
	}

	/**
	 * update a User
	 *
	 */

	public void Update(UserBean bean) throws Exception {

		Connection conn = null;

		UserBean beanExist = findbylogin(bean.getLogin());

		if (beanExist != null && !(beanExist.getId() == bean.getId())) {

			throw new Exception("LOGIN ID ALREADY EXIST");

		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // START TRANSACTIION
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN =?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setLong(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getRoleId());
			pstmt.setInt(8, bean.getUnSuccessfulLogin());
			pstmt.setString(9, bean.getGender());
			pstmt.setTimestamp(10, bean.getLastLogin());
			pstmt.setString(11, bean.getLock());
			pstmt.setString(12, bean.getRegisteredIP());
			pstmt.setString(13, bean.getLastLoginIP());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());
			pstmt.setLong(18, bean.getId());

			pstmt.execute();
			conn.commit(); // END TRANSACTION
			pstmt.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new Exception("EXCEPTION : DELETE ROLLBACK EXCEPTION  " + ex.getMessage());
			}

			throw new Exception("Exception in Updation User");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

	}

	/**
	 * search a User
	 *
	 */

	public List search(UserBean bean) throws Exception {

		return search(bean, 0, 0);

	}

	/**
	 * search a User with pagination
	 *
	 */

	public List search(UserBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {

				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {

				sql.append(" AND First_Name like '" + bean.getFirstName() + "%'");

			}

			if (bean.getLastName() != null && bean.getLastName().length() > 0) {

				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");

			}

			if (bean.getLogin() != null && bean.getLogin().length() > 0) {

				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");

			}

			if (bean.getPassword() != null && bean.getPassword().length() > 0) {

				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");

			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {

				sql.append(" AND DOB = " + bean.getGender());
			}

			if (bean.getMobileNo() != 0 && bean.getMobileNo() > 0) {

				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());

			}
			if (bean.getRoleId() > 0) {

				sql.append(" AND ROLE_ID = " + bean.getRoleId());

			}
			if (bean.getUnSuccessfulLogin() > 0) {

				sql.append(" AND UNSUCCESSFUL_LOGIN = " + bean.getUnSuccessfulLogin());

			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {

				sql.append(" AND GENDER like '" + bean.getGender() + "%'");

			}
			if (bean.getLastLogin() != null && bean.getLastLogin().getTime() > 0) {

				sql.append(" AND LAST_LOGIN = " + bean.getLastLogin());

			}
			if (bean.getRegisteredIP() != null && bean.getRegisteredIP().length() > 0) {

				sql.append(" AND REGISTERED_IP like '" + bean.getRegisteredIP() + "%'");

			}
			if (bean.getLastLoginIP() != null && bean.getLastLoginIP().length() > 0) {

				sql.append(" AND LAST_LOGIN_IP like '" + bean.getLastLoginIP() + "%'");

			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

		}

		System.out.println(sql);
		ArrayList list = new ArrayList();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getLong(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

				list.add(bean);

			}
			rs.close();

		} catch (Exception e) {
           
			throw new Exception("EXCEPTION : EXCEPTION IN SEARCH USER");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

		return list;

	}

	/**
	 * list of User
	 *
	 */

	public List list() throws Exception {

		return list(0, 0);

	}

	/**
	 * list of User with pagination
	 *
	 */

	public List list(int pageNo, int pageSize) throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from ST_USER");

		// if page size is greater than zero then apply pagination

		if (pageSize > 0) {

			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);

		}

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getLong(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

				list.add(bean);

			}
			rs.close();

		} catch (Exception e) {

			throw new Exception("Exception : Exception in getting list of users");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

		return list;

	}

	/**
	 * Get Role of a User
	 *
	 */

	public List GetRoles(UserBean bean) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE role_Id=?");
		Connection conn = null;
		List list = new ArrayList();

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRoleId());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getLong(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {

			throw new Exception("Exception : Exception in get roles");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/**
	 * authenticate a User
	 *
	 */

	public UserBean authenticate(String login, String password) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, login);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getLong(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();

		} catch (Exception e) {
			
			  throw new Exception("Exception : Exception in get roles");
			 

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public boolean lock(String login) throws Exception {

		boolean flag = false;
		UserBean beanExist = null;

		try {
			beanExist = findbylogin(login);

			if (beanExist != null) {

				beanExist.setLock(UserBean.ACTIVE);
				Update(beanExist);
				flag = true;
			} else {

				throw new Exception("LoginId not exist");
			}
		} catch (Exception e) {

			throw new Exception("Database Exception");
		}
		return flag;
	}

	/**
	 * Change Password
	 *
	 */

	public boolean ChangePassword(Long id, String oldpassword, String newpassword) throws Exception {

		boolean flag = false;
		UserBean beanExist = null;

		beanExist = findbyPK(id);
		if (beanExist != null && beanExist.getPassword().equals(oldpassword)) {
			beanExist.setPassword(newpassword);
			try {
				Update(beanExist);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new Exception("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;

	}

	public UserBean updateAccess(UserBean bean) throws Exception {

		return null;

	}

	/**
	 * Reset User
	 *
	 */

	public long RegisterUser(UserBean bean) throws Exception {

		long pk = add(bean);

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successfull for ORS ");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	/**
	 * Forget Password
	 *
	 */

	public boolean ForgetPassword(String login) throws Exception {

		boolean flag = false;
		UserBean userdata = findbylogin(login);

		if (userdata == null) {

			throw new RecordNotFoundException("Email ID does not exists !");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", userdata.getLogin());
		map.put("password", userdata.getPassword());
		map.put("firstName", userdata.getFirstName());
		map.put("lastName", userdata.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(login);
		msg.setSubject("ORS PASSWORD RESET");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		flag = true;

		return flag;

	}
}

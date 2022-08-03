package in.co.sunrays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.UserBean;
import in.co.sunrays.model.UserModel;

/**
 * for usermodel testing
 * 
 * @author Shashank
 *
 */

public class UserModelTest {

	public static void main(String[] args) throws Exception {

		// testAdd();
		// testDelete();
		// testUpdate();
		// testfindbyPk();
		// testfindbylogin();
		// testGetRoles();
	//	testList();
		 testSearch();
		// testRegisterUser();
		// testChangePassword();
		// testForgetPassword();
	}

	private static void testForgetPassword() {

		UserModel model = new UserModel();

		try {

			boolean b = model.ForgetPassword("shekhar@gmail.com");

			System.out.println("Success : Test Forget Password Success");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void testChangePassword() throws Exception {
		
		UserModel model = new UserModel();

		UserBean bean = model.findbylogin("shekhar@gmail.com");
		String oldPassword = bean.getPassword();
		bean.setId(1l);
		bean.setPassword("Shinu@12#");
		bean.setConfirmPassword("Shinu@12#");
		String newPassword = bean.getPassword();
		try {
			model.ChangePassword(1l, oldPassword, newPassword);
			System.out.println("password has been change successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void testRegisterUser() {
		
		try {
			
			UserModel model = new UserModel();

			UserBean bean = new UserBean();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			bean.setId(5L);
			bean.setFirstName("harsh");
			bean.setLastName("pandey");
			bean.setLogin("pandeuy@gmail.com");
			bean.setPassword("shashank123@");
			bean.setDob(sdf.parse("1/1/1999"));
			bean.setMobileNo(7747830969L);
			bean.setRoleId(2L);
			bean.setUnSuccessfulLogin(3);
			bean.setGender("male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setRegisteredIP("as@3");
			bean.setLastLoginIP("LastLog123");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.RegisterUser(bean);
			
			System.out.println("Successfully register");
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			
			UserBean registerbean = model.findbyPK(pk);
		
			if (registerbean != null) {
				System.out.println("Test registation fail");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}


	}

	private static void testList() {

		try {
			UserModel model = new UserModel();

			UserBean bean = new UserBean();

			List list = new ArrayList();

			list = model.list(1, 10);

			if (list.size() < 0) {

				System.out.println("Test list fail");

			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void testGetRoles() {

		try {

			UserModel model = new UserModel();
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setRoleId(5L);

			list = model.GetRoles(bean);

			if (list.size() < 0) {

				System.out.println("Test Get Roles fail");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void testSearch() {

		try {

			UserModel model = new UserModel();
			UserBean bean = new UserBean();

			List list = new ArrayList();

			bean.setFirstName("BABA");

			list = model.search(bean, 0, 0);

			if (list.size() < 0) {

				System.out.println("Test Serach fail");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void testfindbylogin() {

		try {

			UserModel model = new UserModel();
			UserBean bean = new UserBean();
			bean = model.findbylogin("shekhar@gmail.com");

			if (bean == null) {

				System.out.println("TEST FIND BY LOGIN FAIL");

			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private static void testfindbyPk() {

		try {

			UserModel model = new UserModel();
			UserBean bean = new UserBean();
			long pk = 3L;

			bean = model.findbyPK(pk);

			if (bean == null) {

				System.out.println("TEST FIND BY PK FAIL");

			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void testUpdate() {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyy");
			UserModel model = new UserModel();
			UserBean bean = model.findbyPK(3L);

			bean.setFirstName("BABA");
			bean.setLastName("Vyas");
			bean.setLogin("shekhar@gmail.com");
			bean.setPassword("pass8888");
			bean.setDob(sdf.parse("22-9-1998"));
			bean.setMobileNo(7768789999L);
			bean.setRoleId(5L);
			bean.setUnSuccessfulLogin(2);
			bean.setGender("male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setRegisteredIP("33A");
			bean.setLastLoginIP("LastSd");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.Update(bean);

			UserBean updatedbean = model.findbyPK(3L);

			if (!"sdfdsfd".equals(updatedbean.getLogin())) {

				System.out.println("TEST UPDATE SUCCESS");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testDelete() {

		try {

			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			long pk = 1L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("TEST DELETE SUCCESSFULL" + bean.getId());

			UserBean deletedbean = model.findbyPK(pk);

			if (deletedbean != null) {

				System.out.println("TEST DELETE FAIL");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testAdd() {

		try {

			UserBean bean = new UserBean();
			UserModel model = new UserModel();

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			bean.setId(1);
			bean.setFirstName("BABALU");
			bean.setLastName("PANDEY");
			bean.setLogin("OOO@gmail.com");
			bean.setPassword("PASSWORD124");
			bean.setDob(sdf.parse("22-9-1998"));
			bean.setMobileNo(7747835907L);
			bean.setRoleId(1L);
			bean.setUnSuccessfulLogin(5);
			bean.setGender("MALE");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("YES");
			bean.setRegisteredIP("300D");
			bean.setLastLoginIP("lastINip08");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			UserBean addedbean = model.findbyPK(pk);
			System.out.println("TEST ADD SUCCESSFULL");
			if (addedbean == null) {
				System.out.println("TEST ADD FAIL");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}

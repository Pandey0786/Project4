package in.co.sunrays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.RoleBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.RoleModel;

/**
 * For Role model testing
 * 
 * @author Shashank
 *
 */

public class RoleModelTest {

	public static void main(String[] args) throws Exception {

		// testAdd();
//		testDelete();
//		testfindbypk();
//		testfindbyname();
//		testSearch();
//		testlist();
		testUpdate();
	}

	private static void testUpdate() {

		try {
			RoleModel model = new RoleModel();
			RoleBean bean = model.findByPK(2L);
			bean.setName("paji");
			bean.setDescription("str");
			model.update(bean);

			RoleBean updatedbean = model.findByPK(2L);
			/*
			 * if (!"12".equals(updatedbean.getName())) {
			 * System.out.println("Test Update fail"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	private static void testlist() {

		try {
			RoleModel model = new RoleModel();
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testSearch() {

		try {
			RoleModel model = new RoleModel();

			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("sha");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testfindbyname() {

		try {
			RoleBean bean = new RoleBean();
			RoleModel model = new RoleModel();

			bean = model.findByName("shashank");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testfindbypk() {

		try {
			RoleBean bean = new RoleBean();
			RoleModel model = new RoleModel();
			long pk = 3L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testDelete() {

		try {
			RoleBean bean = new RoleBean();
			RoleModel model = new RoleModel();
			// long pk = 3L;
			bean.setId(1);
			model.delete(bean);
			RoleBean deletedbean = model.findByPK(1);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testAdd() throws Exception {

		try {
			RoleBean bean = new RoleBean();
			RoleModel model = new RoleModel();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			// bean.setId(5L);
			bean.setName("shashank");
			bean.setDescription("student");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			RoleBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

}

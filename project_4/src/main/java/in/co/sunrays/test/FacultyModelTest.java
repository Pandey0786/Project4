package in.co.sunrays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.FacultyBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.FacultyModel;
import in.co.sunrays.model.UserModel;

/**
 * for Faculty model testing
 * 
 * @author Shashank
 *
 */

public class FacultyModelTest {

	public static void main(String[] args) throws Exception {

		// testAdd();
		// testDelete();
		// testUpdate();
		// testfindbypk();
		// testfindbyemail();
		// testSearch();
		testList();
	}

	private static void testList() throws ApplicationException {

		// TODO Auto-generated method stub
		FacultyBean fb = new FacultyBean();
		FacultyModel fm = new FacultyModel();

		List list = new ArrayList();
		list = fm.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			fb = (FacultyBean) it.next();
			System.out.println(fb.getCollege_id());
			System.out.println(fb.getCourse_Name());
			// System.out.println(fb.getDescription());
			System.out.println(fb.getCreatedBy());
		}

	}

	private static void testSearch() {

		try {
			FacultyModel model = new FacultyModel();
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			bean.setFirst_Name("Akanksha");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourse_Name());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testfindbyemail() {

		try {

			FacultyModel model = new FacultyModel();
			FacultyBean bean = new FacultyBean();
			bean = model.findByEmail("shekhar@124");

			System.out.println(bean.getId());
			System.out.println(bean.getCourse_Name());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private static void testfindbypk() {

		try {
			FacultyBean bean = new FacultyBean();
			FacultyModel model = new FacultyModel();
			int pk = 1;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourse_Name());
			System.out.println(bean.getFirst_Name());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws Exception {

		FacultyModel fm = new FacultyModel();
		FacultyBean fb = fm.findByPK(2);
		fb.setFirst_Name("Akanksha");
		fb.setLast_Name("Rawal");
		fb.setGender("Female");

		fm.update(fb);

		FacultyBean bean1 = fm.findByPK(2);

		System.out.println("Update");

	}

	private static void testDelete() {

		try {
			FacultyBean bean = new FacultyBean();
			FacultyModel model = new FacultyModel();
			long pk = 1L;
			bean.setId(pk);
			model.delete(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testAdd() throws Exception {

		FacultyBean fb = new FacultyBean();
		FacultyModel fm = new FacultyModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// fb.setId(2);
		fb.setFirst_Name("son");
		fb.setLast_Name("rat");
		fb.setGender("MALE");
		fb.setCollege_id(1);
		fb.setCourse_id(1);
		fb.setDOJ(sdf.parse("12/12/2009"));
		fb.setSubject_id(1);
		fb.setQualification("MBA");
		fb.setMobile_No("9876544211");
		fb.setCreatedBy("admin");
		fb.setModifiedBy("admin");
		fb.setCreatedDatetime(new Timestamp(new Date().getTime()));
		fb.setModifiedDatetime(new Timestamp(new Date().getTime()));
		fb.setEmail_id("shekhar@124");
		long pk = fm.add(fb);

	}

}

package in.co.sunrays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.StudentBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.StudentModel;

/**
 * For Student model testing
 * 
 * @author Shashank
 *
 */

public class StudentModelTest {

	public static void main(String[] args) throws Exception {

// testAdd();	
//		testDelete();
		testUpdate();
//		testFindbyPk();
//		testFindbyEmail();
//		testSearch();
//		testList();
	}

	private static void testList() {

		try {
			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	

	private static void testSearch() {

		try {

			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();

			List list = new ArrayList();

			bean.setFirstName("pankaj");

			list = model.search(bean, 0, 0);

			if (list.size() < 0) {

				System.out.println("Test Serach fail");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeId());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testFindbyEmail() {

		try {

			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();

			bean = model.findByEmailId("pankaj@FGSDF.VOM");

			if (bean != null) {

				System.out.println("Test Find By EmailId fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testFindbyPk() {

		try {

			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();
			long pk = 1L;

			bean = model.findbyPK(pk);

			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	public static void testUpdate() throws Exception {

		try {
			StudentModel model = new StudentModel();
			StudentBean bean = model.findbyPK(1L);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setCollegeId(1L);
			bean.setCollegeName("SIT Indore");
			bean.setFirstName("Ak");
			bean.setLastName("dfdsf");
			bean.setDob(sdf.parse("1/12/1990"));
			bean.setMobileNo(917525);
			bean.setEmail("As@FGSDF.VOM");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setId(1);
			model.update(bean);

			StudentBean updatedbean = model.findbyPK(1L);
			if (!"rr".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update success");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	
	private static void testDelete() {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			StudentBean bean = new StudentBean();
			StudentModel model = new StudentModel();

			long pk = 10L;

			bean.setId(1);
			model.delete(bean);

			StudentBean deletedbean = model.findbyPK(pk);

			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testAdd() throws Exception {

		try {
			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			//bean.setId(6);
			// bean.setCollegeName("SIT Indore");
			bean.setFirstName("yadav");
			bean.setLastName("karma");
			bean.setDob(sdf.parse("1/11/1992"));
			bean.setMobileNo(917525333);
			bean.setEmail("jjj@FGSDF.VOM");
			bean.setCollegeId(1L);
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			StudentBean addedbean = model.findbyPK(pk);
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

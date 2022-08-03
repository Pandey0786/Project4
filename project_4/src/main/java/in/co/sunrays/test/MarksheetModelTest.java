package in.co.sunrays.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.MarksheetBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.MarksheetModel;

/**
 * For marksheet model testing
 * 
 * @author Shashank
 *
 */

public class MarksheetModelTest {

	public static void main(String[] args) {

		// testAdd();
		 testUpdate();
		// testDelete();
		// testfindbypk();
		// testfindbyrollno();
		// testMeritlist();
		// testSearch();
		//testList();
	}

	private static void testList() {

		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model = new MarksheetModel();
			List list = new ArrayList();
			list = model.list(1, 6);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
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
			MarksheetModel model = new MarksheetModel();
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			bean.setName("Ak dfdsf");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testMeritlist() {

		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model = new MarksheetModel();
			List list = new ArrayList();
			list = model.getMeritList(1, 5);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testfindbyrollno() {

		try {
			MarksheetModel model = new MarksheetModel();
			MarksheetBean bean = model.findbyRollNo("4");
			if (bean == null) {
				System.out.println("Test Find By RollNo fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testfindbypk() {

		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model = new MarksheetModel();
			long pk = 1L;
			bean = model.findByPK(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testDelete() {

		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model = new MarksheetModel();
			long pk = 2L;
			bean.setId(2);
			model.delete(bean);
			MarksheetBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() {

		try {
			MarksheetModel model = new MarksheetModel();
			MarksheetBean bean = model.findByPK(1);
			// bean.setName("IPS");
			bean.setChemistry(65);
			bean.setMaths(66);
			// bean.setStudentId(2);
			model.update(bean);

			MarksheetBean updatedbean = model.findByPK(1L);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	private static void testAdd() {

		try {

			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model = new MarksheetModel();

			bean.setId(2);
			bean.setRollNo("2");
			bean.setPhysics(99);
			bean.setChemistry(99);
			bean.setMaths(80);
			bean.setStudentId(1L);

			long pk = model.add(bean);

			MarksheetBean addedbean = model.findByPK(pk);

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

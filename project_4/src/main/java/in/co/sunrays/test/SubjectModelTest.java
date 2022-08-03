package in.co.sunrays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.model.SubjectModel;

/**
 * for Subject model testing
 * 
 * @author Shashank
 *
 */

public class SubjectModelTest {

	public static void main(String[] args) throws Exception {

		 testAdd();
		// testUpdate();
		// testdelete();
		// testSearch();
		// testList();
		//testfindbyname();
		//testfindByPk();
	}

	private static void testfindByPk() {
		
		try {
			SubjectModel model = new SubjectModel();
            SubjectBean bean = new SubjectBean();
            long pk = 2L;
            bean = model.findByPK(pk);
            if (bean == null) {
                System.out.println("Test Find By PK fail");
            }else{
            System.out.println(bean.getId());
            System.out.println(bean.getSubject_Name());
            System.out.println(bean.getDescription());
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
		
	}

	private static void testfindbyname() {

		

			 try {
	                SubjectModel model = new SubjectModel();			 
		            
	                SubjectBean bean = new SubjectBean();
		            
	                bean = model.findByName("Science");
		            
	                if (bean == null) {
		                System.out.println("Test Find By Name fail");
		            }else{
		            System.out.println(bean.getId());
		            System.out.println(bean.getSubject_Name());
		            System.out.println(bean.getDescription());
		           
		            }
		        } catch (ApplicationException e) {
		            e.printStackTrace();
		        }

	}

	private static void testList() {

		try {
			SubjectBean bean = new SubjectBean();
			SubjectModel model = new SubjectModel();
			List list = new ArrayList();
			list = model.list(1, 6);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubject_Name());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getCourse_Id());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testSearch() {

		try {
			SubjectBean bean = new SubjectBean();
			SubjectModel model = new SubjectModel();
			List list = new ArrayList();
			bean.setSubject_Name("Hindi");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubject_Name());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getCourse_Id());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testdelete() throws Exception {

		try {

			SubjectBean bean = new SubjectBean();
			SubjectModel model = new SubjectModel();

			bean.setId(1L);

			model.delete(bean);

			SubjectBean addedBean = model.findByPK(1L);

			if (addedBean == null) {

				System.out.println("Test DELETE fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws Exception {

		try {

			SubjectModel model = new SubjectModel();
			SubjectBean bean = model.findByPK(1);
			bean.setSubject_Name("chesdmi");
			bean.setCourse_Name("bsc");
			bean.setCourse_Id(1);
			bean.setDescription("dfsda");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			SubjectBean addedBean = model.findByPK(1);
			if (addedBean == null) {
				System.out.println("Test UPDATE fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testAdd() throws Exception {

		try {
			SubjectBean bean = new SubjectBean();
			SubjectModel model = new SubjectModel();
			bean.setSubject_Name("Science");
			bean.setCourse_Name("PG");
			bean.setCourse_Id(1);
			bean.setDescription("dfsda");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			SubjectBean addedBean = model.findByPK(pk);
			if (addedBean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}

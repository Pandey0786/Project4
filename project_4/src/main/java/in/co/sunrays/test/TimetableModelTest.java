package in.co.sunrays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.TimetableBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.TimetableModel;

/**
 * For timetable model testing
 * @author Shashank
 *
 */

public class TimetableModelTest {

	public static void main(String[] args) throws Exception, Exception {
		
//		   testAdd();
//		   testDelete();
//		   testUpdate();
//		   testList();
//		   testFindByPK();
	testcheckbysem();
		
	}

	

	private static void testcheckbysem() {
		TimetableBean bean = new TimetableBean();
		TimetableModel model = new TimetableModel();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		
		try {
			bean = model.checkBysemester(1, 1, "5th", sdf.parse("2012-03-04"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (bean == null) {
            System.out.println("Test Find By Name fail");
        }else{
        System.out.println(bean.getId());
        System.out.println(bean.getSubject_Name());
        System.out.println(bean.getCourse_Id());
        System.out.println(bean.getExam_Date());
       
        }
		
		
	}



	private static void testFindByPK() throws Exception {
		
		TimetableBean stb = new TimetableBean();

		TimetableModel stm = new TimetableModel();

		stb = stm.findByPK(1);

		System.out.println(stb.getId());
		System.out.println(stb.getCourse_Id());
		System.out.println(stb.getCourse_Name());
		System.out.println(stb.getCreatedBy());
		System.out.println(stb.getModifiedBy());
		System.out.println(stb.getCreatedDatetime());
		System.out.println(stb.getModifiedDatetime());
		
	}

	private static void testList() throws Exception {
		
		TimetableBean bean = new TimetableBean();
        TimetableModel model= new TimetableModel();
        List list = new ArrayList();
        list = model.list(1, 6);
        if (list.size() < 0) {
            System.out.println("Test List fail");
        }
        Iterator it = list.iterator();
        
        while (it.hasNext()) {
        	 bean = (TimetableBean) it.next();
        System.out.println(bean.getCourse_Name());
        System.out.println(bean.getSemester());
        }
		
	}

	private static void testUpdate() throws Exception, DuplicateRecordException {
		
		TimetableModel tm = new TimetableModel();
		TimetableBean tb = tm.findByPK(1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = new Date();
		
		tb.setSemester("5th");
		System.out.println("successfull");
		tm.update(tb);
		
	}

	private static void testDelete() {
		
		try {
			TimetableBean bean = new TimetableBean();
			TimetableModel model = new TimetableModel();

			int pk = 1;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
			TimetableBean deletedbean = model.findByPK(pk);
			if (deletedbean == null) {
				System.out.println("Test Delete fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void testAdd() throws Exception, DuplicateRecordException {
		
		TimetableBean tb = new TimetableBean();
		TimetableModel tm = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = new Date();
		tb.setCourse_Name("B.sc");
		tb.setCourse_Id(1);
		tb.setSubject_Name("maths");
		tb.setSubject_Id(1);
		tb.setSemester("3rd");
		tb.setExam_Date(sdf.parse("04/03/2012"));
		tb.setExam_Time("4:00");
		tb.setModifiedBy("faculty");
		tb.setCreatedBy("admin");
		tb.setCreatedDatetime(new Timestamp(new Date().getTime()));
		tb.setModifiedDatetime(new Timestamp(new Date().getTime()));
		tm.add(tb);
		System.out.println("dsfdas");
		
	}
	
	
	
}

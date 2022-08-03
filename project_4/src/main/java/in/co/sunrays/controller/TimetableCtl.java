package in.co.sunrays.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.bean.TimetableBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.model.SubjectModel;
import in.co.sunrays.model.TimetableModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * TimeTable functionality Controller. Performs operation for add, update, delete
 * and get TimeTable.
 * 
 * @author Shashank
 *
 */
@WebServlet("/ctl/TimetableCtl")
public class TimetableCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimetableCtl.class);
	
	protected void preload(HttpServletRequest request) {
		CourseModel crsm = new CourseModel();
		SubjectModel stm = new SubjectModel();
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		try {
			clist = crsm.list();
			slist = stm.list();
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected boolean validate(HttpServletRequest request) {
		log.debug("validate method of TimeTable Ctl started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExTime"))) {
			request.setAttribute("ExTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}

		log.debug("validate method of TimeTable Ctl End");
		return pass;
	}
	
	
	protected TimetableBean populateBean(HttpServletRequest request) {
		log.debug("populateBean method of TimeTable Ctl start");
		TimetableBean tb = new TimetableBean();

		tb.setId(DataUtility.getLong(request.getParameter("id")));

		tb.setSubject_Id(DataUtility.getInt(request.getParameter("subjectId")));

		tb.setCourse_Id(DataUtility.getInt(request.getParameter("courseId")));
		

		tb.setSemester(DataUtility.getString(request.getParameter("semester")));

		tb.setExam_Date(DataUtility.getDate(request.getParameter("ExDate")));
		tb.setExam_Time(DataUtility.getString(request.getParameter("ExTime")));

		populateDTO(tb, request);
		log.debug("populateBean method of TimeTable Ctl End");
		return tb;
	}
	
	
	 /**
     * Contains Display logics
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do Get method of TimeTable Ctl Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		int id = (int) DataUtility.getLong(request.getParameter("id"));

		TimetableModel tm = new TimetableModel();
		TimetableBean tb = null;
		if (id > 0) {
			try {
				tb = tm.findByPK(id);
				ServletUtility.setBean(tb, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}

		log.debug("do Get method of TimeTable Ctl End");

		ServletUtility.forward(getView(), request, response);
	}
	
	
	 /**
     * Contains Submit logics
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do post method of TimeTable Ctl start");

		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		TimetableModel tm = new TimetableModel();
		System.out.println("12345");

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TimetableBean tb = (TimetableBean) populateBean(request);
			try {
				if (id > 0) {
					tm.update(tb);
					ServletUtility.setSuccessMessage(" TimeTable Data is Successfully Updated", request);
				} else {

				long pk = 	tm.add(tb);
                tb.setId(pk);
					ServletUtility.setSuccessMessage(" TimeTable is Successfully Saved", request);
				}
				ServletUtility.setBean(tb, request);

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setBean(tb, request);
				ServletUtility.setErrorMessage("Time Table already Exists", request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}
	
	
	@Override
	protected String getView() {
		
		return ORSView.TIMETABLE_VIEW;
	}

}

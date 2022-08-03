package in.co.sunrays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.StudentBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.StudentModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * Student functionality Controller. Performs operation for add, update, delete
 * and get Student.
 * 
 * @author Shashank
 *
 */

@WebServlet("/ctl/StudentCtl")
public class StudentCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(StudentCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		CollegeModel cm = new CollegeModel();

		try {

			List l = cm.list();
			request.setAttribute("collegeList", l);

		} catch (ApplicationException e) {
			log.error(e);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("StudentCtl Method validate Started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstname"))) {

			request.setAttribute("firstname", PropertyReader.getValue("error.require", "First Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("firstname"))) {

			request.setAttribute("firstname", "First name must contain only Characters ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastname"))) {

			request.setAttribute("lastname", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("lastname"))) {

			request.setAttribute("lastname", "Last name must contain only Characters ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobile"))) {

			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;

		} else if (!DataValidator.isMobileNo(request.getParameter("mobile"))) {

			request.setAttribute("mobile", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {

			request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
			pass = false;

		} else if (!DataValidator.isEmail(request.getParameter("email"))) {

			request.setAttribute("email", PropertyReader.getValue("error.email", "Email "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {

			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegename"))) {

			request.setAttribute("collegename", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}

		log.debug("StudentCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		StudentBean sb = new StudentBean();

		sb.setId(DataUtility.getLong(request.getParameter("id")));
		sb.setFirstName(DataUtility.getString(request.getParameter("firstname")));
		sb.setLastName(DataUtility.getString(request.getParameter("lastname")));
		sb.setDob(DataUtility.getDate(request.getParameter("dob")));
		sb.setMobileNo(DataUtility.getLong(request.getParameter("mobile")));
		sb.setEmail(DataUtility.getString(request.getParameter("email")));
		sb.setCollegeId(DataUtility.getLong(request.getParameter("collegename")));

		populateDTO(sb, request);
		log.debug("StudentCtl Method populatesb Ended");
		return sb;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("StudentCtl Method doGet Started");

		// String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		StudentModel sm = new StudentModel();
		StudentBean sb;

		try {
			sb = sm.findbyPK(id);
			ServletUtility.setBean(sb, request);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doGett Ended");

	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("StudentCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		StudentModel sm = new StudentModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			StudentBean sb = (StudentBean) populateBean(request);
			try {
				if (id > 0) {
					sm.update(sb);
					ServletUtility.setBean(sb, request);
					ServletUtility.setSuccessMessage("Student data is successfully updated", request);

				} else {

					long pk = sm.add(sb);
					sb.setId(pk);
					ServletUtility.setBean(sb, request);
					ServletUtility.setSuccessMessage("Student data is successfully saved", request);

				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(sb, request);
				ServletUtility.setErrorMessage("Student Email Id already exists", request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("StudentCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.STUDENT_VIEW;
	}

}

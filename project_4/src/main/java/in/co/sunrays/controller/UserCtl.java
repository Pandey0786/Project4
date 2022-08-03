package in.co.sunrays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.RoleModel;
import in.co.sunrays.model.UserModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * User functionality Controller. Performs operation for add, update, delete
 * and get User.
 * 
 * @author Shashank
 *
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl{

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(UserCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel rm = new RoleModel();
		try {
			List l = rm.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("UserCtl Method validate Started");

		boolean pass = true;
		long id = DataUtility.getLong(request.getParameter("id"));

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "First Name must contain only Characters ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Last Name must contain only Characters ");
			pass = false;
		}

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login ID "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Password must contain alphanumeric characters");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Confirm  Password  should  be matched.");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role Id"));
			pass = false;
		}

		log.debug("UserCtl Method validate Ended");

		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("UserCtl Method populatebean Started");

		UserBean ub = new UserBean();

		ub.setId(DataUtility.getLong(request.getParameter("id")));

		ub.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		ub.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		ub.setLastName(DataUtility.getString(request.getParameter("lastName")));

		ub.setLogin(DataUtility.getString(request.getParameter("login")));

		ub.setPassword(DataUtility.getString(request.getParameter("password")));

		ub.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		ub.setGender(DataUtility.getString(request.getParameter("gender")));

		ub.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(ub, request);

		log.debug("UserCtl Method populateub Ended");

		return ub;
	}
	
	
	 /**
     * Contains DIsplay logics
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModel um = new UserModel();
		int id = (int) DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("check one " + id);
			UserBean ub = null;
			try {
				ub = um.findbyPK(id);
				ServletUtility.setBean(ub, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doGet Ended");
	}
	
	
	 /**
     * Contains Submit logics
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel um = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			UserBean ub = (UserBean) populateBean(request);

			try {
				if (id > 0) {
					um.Update(ub);
					ServletUtility.setBean(ub, request);
					ServletUtility.setSuccessMessage("User is successfully updated", request);
				} else {
				
					long pk = um.add(ub);
				
				ub.setId(pk);
				
					ServletUtility.setBean(ub, request);
					ServletUtility.setSuccessMessage("User is successfully saved", request);
				}
		
			} catch (ApplicationException e) {
				System.out.println("this obe");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(ub, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			} catch (Exception e) {			
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserBean ub = (UserBean) populateBean(request);
			try {
				um.delete(ub);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPostEnded");
	}
	
	
	@Override
	protected String getView() {
		
		return ORSView.USER_VIEW;
	}

}

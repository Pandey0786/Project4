package in.co.sunrays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.RoleBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.UserModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 * 
 * @author Shashank
 */

@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {

	public static final String OP_SIGN_UP = "SignUp";

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("firstName"))) {

			request.setAttribute("firstName", "First Name must contain only  Characters ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {

			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("lastName"))) {

			request.setAttribute("lastName", "Last Name must contain only  Characters");
			pass = false;
		}

		if (DataValidator.isNull(login)) {

			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;

		} else if (!DataValidator.isEmail(login)) {

			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
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
			request.setAttribute("confirmPassword", "Confirm  Password should be matched.");

			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		// log.debug("UserRegistrationCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));

		bean.setRoleId(RoleBean.STUDENT);

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);

		return bean;

	}

	/**
	 * Display concept of user registration
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Submit concept of user registration
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		UserModel model = new UserModel();

		try {

			UserBean bean = (UserBean) populateBean(request);

			if (OP_SIGN_UP.equalsIgnoreCase(op)) {

				long pk = model.add(bean);
				bean.setId(pk);

				ServletUtility.setSuccessMessage("Data is successfully saved", request);
				ServletUtility.forward(getView(), request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.forward(getView(), request, response);
				return;
			}

		} catch (ApplicationException e) {

			ServletUtility.handleException(e, request, response);
			return;

		} catch (DuplicateRecordException e) {

			ServletUtility.setErrorMessage("Login ID already exists", request);
			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {

		return ORSView.USER_REGISTRATION_VIEW;
	}

}

package in.co.sunrays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 *
 * @author Shashank
 *
 */

@WebServlet("/ctl/CollegeCtl")
public class CollegeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/**
	 * Validate input data
	 *
	 */

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {

			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("name"))) {

			request.setAttribute("name", "Name must contain only Character");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {

			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));

			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("state"))) {

			request.setAttribute("state", PropertyReader.getValue("error.require", "state"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("state"))) {

			request.setAttribute("state", "State Name Must Contain Only Character");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("city"))) {

			request.setAttribute("city", PropertyReader.getValue("error.require", "city"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("city"))) {

			request.setAttribute("city", "City name must contain only Character");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileno"))) {

			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "mobileno"));
			pass = false;

		} else if (!DataValidator.isMobileNo(request.getParameter("mobileno"))) {

			request.setAttribute("mobileno", "mobile num must be in 10 count and series start only from 6-7-8-9");
			pass = false;
		}

		return pass;

	}

	protected BaseBean populateBean(HttpServletRequest request) {

		CollegeBean cb = new CollegeBean();

		cb.setId(DataUtility.getLong(request.getParameter("ID")));
		cb.setName(DataUtility.getString(request.getParameter("name")));
		cb.setAddress(DataUtility.getString(request.getParameter("address")));
		cb.setState(DataUtility.getString(request.getParameter("state")));
		cb.setCity(DataUtility.getString(request.getParameter("city")));
		cb.setPhoneNo(DataUtility.getString(request.getParameter("mobileno")));

		populateDTO(cb, request);
		return cb;

	}

	/**
	 * Contains display logic
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		CollegeModel cm = new CollegeModel();
		long id = DataUtility.getLong(request.getParameter("ID"));
		if (id > 0) {

			CollegeBean cb;
			try {

				cb = cm.findByPK(id);
				ServletUtility.setBean(cb, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

			}

		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logic
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		CollegeModel cm = new CollegeModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CollegeBean cb = (CollegeBean) populateBean(request);

			try {

				if (id > 0) {

					cm.update(cb);
					ServletUtility.setBean(cb, request);
					ServletUtility.setSuccessMessage("DATA is Successfully UPDATED", request);

				} else {

				long pk	= cm.add(cb);
				
				cb.setId(pk);
				
				ServletUtility.setBean(cb, request);
					
					ServletUtility.setSuccessMessage("Data is SUcessfully Saved", request);

				}

			} catch (ApplicationException e) {

				e.printStackTrace();

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(cb, request);
				ServletUtility.setErrorMessage("College Name Already Exists", request);

				e.printStackTrace();
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.forward(getView(), request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.COLLEGE_VIEW;
	}

}

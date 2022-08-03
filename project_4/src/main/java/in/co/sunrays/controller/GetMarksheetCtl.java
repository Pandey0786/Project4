package in.co.sunrays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.MarksheetBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.MarksheetModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * To Get Student Marksheet
 * @author Shashank
 *
 */

@WebServlet("/ctl/GetMarksheetCtl")
public class GetMarksheetCtl extends BaseCtl  {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);
	
	@Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("GetMarksheetCTL Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo",
                    PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }else if(!DataValidator.isRollNo(request.getParameter("rollNo"))) {
                request.setAttribute("rollNo","Roll Number must be alphanumeric");
                pass = false;
            }

        log.debug("GetMarksheetCTL Method validate Ended");

        return pass;
    }
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("GetMarksheetCtl Method populatebean Started");

		MarksheetBean mb = new MarksheetBean();

		mb.setId(DataUtility.getLong(request.getParameter("id")));

		mb.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		mb.setName(DataUtility.getString(request.getParameter("name")));

		mb.setPhysics(DataUtility.getInt(request.getParameter("physics")));

		mb.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

		mb.setMaths(DataUtility.getInt(request.getParameter("maths")));

		log.debug("GetMarksheetCtl Method populatemb Ended");

		return mb;
	}
	
	
	/**
	 * Contain View logic
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}
	
	
	/**
	 * Contain submit logic
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("GetMarksheetCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		MarksheetModel mm = new MarksheetModel();

		MarksheetBean mb = (MarksheetBean) populateBean(request);

		int id = (int) DataUtility.getLong(request.getParameter("id"));

		if (OP_GO.equalsIgnoreCase(op)) {

			try {
				mb = mm.findbyRollNo(mb.getRollNo());
				if (mb != null) {
					ServletUtility.setBean(mb, request);
				} else {
					ServletUtility.setErrorMessage("Roll number does not exists", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet Ended");
	}
	
	
	@Override
	protected String getView() {
		
		return ORSView.GET_MARKSHEET_VIEW;
	}

}

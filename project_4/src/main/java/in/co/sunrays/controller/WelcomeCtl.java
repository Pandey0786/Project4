package in.co.sunrays.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.co.sunrays.util.ServletUtility;
/**
 *  Welcome functionality Controller. Performs operation for Show Welcome page
 *
 * @author Shashank
 *
 */
@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	 /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);

	}

	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}

}

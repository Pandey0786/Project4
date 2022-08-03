package in.co.sunrays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.util.ServletUtility;

/**
 * Servlet implementation class ErrorCtl
 *
 * @author Shashank
 *
 */
@WebServlet ("/ErrorCtl")
public class ErrorCtl extends BaseCtl  {

	
	 /**
     * Contains Display logics
     */
	 @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	    /**
	     * Contains Submit logics
	     */
	 @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}
	
	
	@Override
	protected String getView() {
		
		return ORSView.ERROR_VIEW; 
	}

}

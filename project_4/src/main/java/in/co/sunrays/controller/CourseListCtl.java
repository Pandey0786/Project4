package in.co.sunrays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * Course functionality Controller. Performs operation for list, search and
 * delete operations of Course.
 *
 * @author Shashank
 *
 */
@WebServlet("/ctl/CourseListCtl")
public class CourseListCtl extends BaseCtl {

	public static Logger log = Logger.getLogger(CourseListCtl.class);

	protected void preload(HttpServletRequest request) {

		CourseModel crsm = new CourseModel();

		try {

			List clist = crsm.list();

			request.setAttribute("CourseList", clist);

		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	protected BaseBean populateBean(HttpServletRequest request) {

		CourseBean crsb = new CourseBean();

		crsb.setId(DataUtility.getLong(request.getParameter("cname")));
		populateDTO(crsb, request);

		return crsb;

	}

	/**
	 * Contains Display logics
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("do get method of CourseCtl Started");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseBean crsb = (CourseBean) populateBean(request);
		CourseModel crsm = new CourseModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			list = crsm.search(crsb, pageNo, pageSize);
			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("do get method of CourseCtl End");
	}

	/**
	 * Contains Submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(request.getParameter("pageSize")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		CourseBean crsb = (CourseBean) populateBean(request);
		CourseModel crsm = new CourseModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {

			pageNo = 1;

		} else if (OP_NEXT.equalsIgnoreCase(op)) {

			pageNo++;

		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {

			pageNo--;

		} else if (OP_NEW.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			pageNo = 1;

			if (ids != null && ids.length > 0) {

				CourseBean deletebean = new CourseBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));

					try {
						crsm.delete(deletebean);

					} catch (ApplicationException e) {

						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("Course Data Deleted Successfully", request);
				}

			} else {

				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}

		try {

			list = crsm.search(crsb, pageNo, pageSize);
			ServletUtility.setBean(crsb, request);

		} catch (ApplicationException e) {

			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record Found", request);
		}

		ServletUtility.setBean(crsb, request);

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {

		return ORSView.COURSE_LIST_VIEW;

	}

}

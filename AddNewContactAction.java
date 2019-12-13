package com.mime.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mime.bean.NotesDTO;
import com.mime.delegate.NotesDelegate;
import com.mime.exception.ConnectionException;
import com.mime.util.UtilConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class AddNewContactAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public AddNewContactAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		HttpSession session = request.getSession();
		NotesDTO notesDTO = new NotesDTO();
		Map map = request.getParameterMap();
		Vector<NotesDTO> opv = null;
		try {
			BeanUtils.populate(notesDTO, map);
			opv = new NotesDelegate().viewContactGroups((Integer) session
					.getAttribute("userid"));
			if (!opv.isEmpty()) {
				request.setAttribute("viewcontactgroup", opv);
				path = UtilConstants._VIEW_CONTACTS;
			} else if (opv.isEmpty()) {
				request.setAttribute("status",
						UtilConstants._NO_CONTACTS_GROUPS);
				path = UtilConstants._VIEW_CONTACTS;
			} else {
				request.setAttribute("status",
						UtilConstants._NO_CONTACTS_GROUPS);
				path = UtilConstants._VIEW_CONTACTS;
			}
		} catch (Exception e) {
			request.setAttribute("status", UtilConstants._NO_CONTACTS_GROUPS);
			path = UtilConstants._VIEW_REMAINDERS;
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

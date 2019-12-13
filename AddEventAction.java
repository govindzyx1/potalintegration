package com.mime.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mime.bean.NotesDTO;
import com.mime.delegate.NotesDelegate;
import com.mime.exception.ConnectionException;
import com.mime.util.UtilConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class AddEventAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public AddEventAction() {
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
		RequestDispatcher rd = null;
		boolean flag = false;
		String path = "";
		NotesDTO notesDTO = new NotesDTO();
		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(notesDTO, map);
			flag = new NotesDelegate().addEvents(notesDTO);
			if (flag) {
				path = UtilConstants._STATUS;
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._EVENT_ADDED_SUCCESS);
			} else {
				path = UtilConstants._STATUS;
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._EVENT_ADD_FAIL);
			}
		} catch (IllegalAccessException illegalAccessException) {
			illegalAccessException.printStackTrace();
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._EVENT_ADD_FAIL);
		} catch (InvocationTargetException invocationTargetException) {
			invocationTargetException.printStackTrace();
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._EVENT_ADD_FAIL);
		} catch (ConnectionException connectionException) {
			connectionException.printStackTrace();
			request.setAttribute(UtilConstants._STATUS_STRING,
					connectionException.getMessage());
		} 
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}
}

package com.mime.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mime.delegate.UserDelegate;
import com.mime.util.UtilConstants;

public class CheckUserAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String user = new UserDelegate().checkUser(request
					.getParameter(UtilConstants._USERNAME1));
			if (user.equalsIgnoreCase("")) {
				request.setAttribute(UtilConstants._STATUS_STRING2,
						UtilConstants._AVAILABLE);
			} else
				request.setAttribute(UtilConstants._STATUS_STRING2,
						UtilConstants._USER_NO_AVAILABLE);
		} catch (Exception e) {
			request.setAttribute(UtilConstants._STATUS_STRING2,
					UtilConstants._AVAILABLE);
		}
		RequestDispatcher rd = request.getRequestDispatcher(request
				.getParameter("path"));
		rd.forward(request, response);
	}
}

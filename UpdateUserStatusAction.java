package com.mime.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mime.delegate.UserDelegate;
import com.mime.exception.ConnectionException;
import com.mime.util.UtilConstants;

public class UpdateUserStatusAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		boolean flag = false;
		int userid = Integer.parseInt(request
				.getParameter(UtilConstants._USERID));
		try {
			flag = new UserDelegate().updateUserStatus(userid);
			if (flag) {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._USER_STATUS);
				path = UtilConstants._STATUS;
			} else {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._USER_STATUS_FAIL);
				path = UtilConstants._STATUS;
			}
		} catch (ConnectionException connectionException) {
			request.setAttribute(UtilConstants._STATUS_STRING,
					connectionException.getMessage());
			path = UtilConstants._STATUS;
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

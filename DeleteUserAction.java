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

public class DeleteUserAction extends HttpServlet {

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
		RequestDispatcher rd = null;
		boolean flag = false;
		String path = "";
		String ch[] = request.getParameterValues("ch");
		try {
			for (int i = 0; i < ch.length; i++) {
				flag = new UserDelegate().deleteUser(Integer.parseInt(ch[i]));
			}
			if (flag) {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._USER_DELETE_SUCCESS);
				path = UtilConstants._STATUS;
			} else {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._USER_DELETE_FAIL);
				path = UtilConstants._STATUS;
			}
		} catch (NumberFormatException numberFormatException) {
			numberFormatException.printStackTrace();
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._USER_DELETE_FAIL);
			path = UtilConstants._STATUS;
		} catch (ConnectionException connectionException) {
			connectionException.printStackTrace();
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._USER_DELETE_FAIL);
			path = UtilConstants._STATUS;
		}
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

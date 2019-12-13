package com.mime.action;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mime.bean.ProfileTO;
import com.mime.delegate.UserDelegate;
import com.mime.exception.DataNotFoundException;
import com.mime.util.UtilConstants;

public class ViewUsersAction extends HttpServlet {

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
		HttpSession session = request.getSession();
		String user = request.getParameter("role");
		String status = request.getParameter("user_status");
		System.out.println("ViewUSERs :" + user);
		System.out.println("ViewUSERs :" + status);
		Vector<ProfileTO> opv = null;
		try {
			path = request.getRealPath("/images");
			opv = new UserDelegate().viewUser(path, user, status);
			if (!opv.isEmpty()) {
				request.setAttribute("userinfo", opv);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_USER;
			} else if (opv.isEmpty()) {
				request.setAttribute("status", UtilConstants._NO_USER);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_ALL__USER;
			} else {
				request.setAttribute("status", UtilConstants._NO_USER);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_ALL__USER;
			}
		} catch (Exception e) {

			request.setAttribute("status", UtilConstants._INVALIED_ENTRY);
			path = UtilConstants._VIEW_ALL__USER;
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

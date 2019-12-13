package com.mime.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mime.bean.ProfileTO;
import com.mime.delegate.UserDelegate;
import com.mime.util.UtilConstants;

public class ViewProfileAction extends HttpServlet {

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
		String requesttype = request.getParameter("requesttype");
		String user = request.getParameter("userid");
		Vector<ProfileTO> opv = null;
		try {
			path = request.getRealPath("/images");
			opv = new UserDelegate().viewUser(user, path);
			if (!opv.isEmpty()) {
				request
						.setAttribute("status",
								UtilConstants._USER_PROFILE_INFO);
				request.setAttribute("userinfo", opv);
				request.setAttribute("user", user);
				if (requesttype.equals("view"))
					path = UtilConstants._VIEW_USER_PROFILE;
				else
					path = UtilConstants._UPDATE_USER_PROFILE;
			} else {
				request.setAttribute("status", UtilConstants._NO_USER_PROFILE);
				request.setAttribute("user", user);
				path = UtilConstants._VIEW_USER_PROFILE;
			}
		} catch (Exception e) {
			request.setAttribute("status", UtilConstants._NO_USER_PROFILE);
			path = UtilConstants._VIEW_USER_PROFILE;
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

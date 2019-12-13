package com.mime.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mime.bean.ProfileTO;
import com.mime.delegate.UserDelegate;
import com.mime.exception.ConnectionException;
import com.mime.exception.LoginException;
import com.mime.util.UtilConstants;

public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "";
		HttpSession session = request.getSession();
		ProfileTO pro = new ProfileTO();
		Vector<ProfileTO> vpro = new Vector<ProfileTO>();
		String username = request.getParameter(UtilConstants._USERNAME);
		pro.setUserName(username);
		String password = request.getParameter(UtilConstants._PASSWORD);
		pro.setPassword(password);
		try {
			vpro = new UserDelegate().loginCheck(pro);
			Iterator it = vpro.listIterator();
			while (it.hasNext()) {
				pro = (ProfileTO) it.next();
			}
			if (pro.getLoginType().equalsIgnoreCase(UtilConstants._ADMIN)) {
				request.setAttribute(UtilConstants._STATUS_STRING, "Welcome "
						+ username);
				target = UtilConstants._ADMIN_HOME;
				session.setAttribute(UtilConstants._LOGINUSER, username);
				session.setAttribute(UtilConstants._LOGINUSERID, pro
						.getUserid());
				session.setAttribute(UtilConstants._ROLE, pro.getLoginType());
			} else if (pro.getLoginType().equalsIgnoreCase(UtilConstants._USER)) {
				request.setAttribute(UtilConstants._STATUS_STRING, "Welcome "
						+ username);
				target = UtilConstants._USER_HOME;
				session.setAttribute(UtilConstants._LOGINUSER, username);
				session.setAttribute(UtilConstants._LOGINUSERID, pro
						.getUserid());
				session.setAttribute(UtilConstants._ROLE, pro.getLoginType());
			} else {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._INVALID_USER);
				target = UtilConstants._LOGIN_FAILED_PAGE;
			}
		} catch (ConnectionException connectionException) {
			request.setAttribute(UtilConstants._STATUS_STRING,
					connectionException.getMessage());
			target = UtilConstants._LOGIN_FAILED_PAGE;
		} catch (LoginException e) {
			target = UtilConstants._LOGIN_FAILED_PAGE;
		} catch (Exception e) {
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._INVALID_USER);
			target = UtilConstants._LOGIN_FAILED_PAGE;
		}
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}
}

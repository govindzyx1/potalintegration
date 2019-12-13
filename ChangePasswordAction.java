package com.mime.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mime.bean.ProfileTO;
import com.mime.delegate.UserDelegate;
import com.mime.exception.ConnectionException;
import com.mime.util.UtilConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class ChangePasswordAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2941564269120432640L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = null;
		boolean flag = false;
		String path = "";
		ProfileTO pro = new ProfileTO();
		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(pro, map);
			flag = new UserDelegate().changePass(pro);
			if (flag) {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._PASSWORD_SUCCESS);
				path = UtilConstants._USER_PASSWORD_CHANGE;
			} else {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._PASSWORD_FAILED);
				path = UtilConstants._USER_PASSWORD_CHANGE;
			}
		} catch (IllegalAccessException illegalAccessException) {
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._PASSWORD_FAILED);
			path = UtilConstants._USER_PASSWORD_CHANGE;
		} catch (InvocationTargetException invocationTargetException) {
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._PASSWORD_FAILED);
			path = UtilConstants._USER_PASSWORD_CHANGE;

		} catch (ConnectionException connectionException) {
			request.setAttribute(UtilConstants._STATUS_STRING,
					connectionException.getMessage());
			path = UtilConstants._USER_PASSWORD_CHANGE;
		}
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

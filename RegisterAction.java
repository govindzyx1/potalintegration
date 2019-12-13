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

public class RegisterAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		ProfileTO pf = new ProfileTO();
		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(pf, map);
			flag = new UserDelegate().insertNewUser(pf);
			if (flag) {
				path = UtilConstants._LOGIN_PAGE;
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._REGISTERED_SUCCESS);
			} else {
				path = UtilConstants._LOGIN_PAGE;
				request.setAttribute("status", UtilConstants._REGISTERED_FAIL);
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
			path = UtilConstants._STATUS;
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._INVALID_ENTRIES);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}

package com.mime.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mime.bean.ProfileTO;
import com.mime.delegate.UserDelegate;
import com.mime.util.UtilConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class RecoverPasswordAction extends HttpServlet {

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
		String path = "";
		boolean flag = true;
		HttpSession session =request.getSession();
		ProfileTO pro = new ProfileTO();

		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(pro, map);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

		try {
			flag = new UserDelegate().passwordRecovery(pro);

			if (flag) {
				String loginid=pro.getUserName();
				request.setAttribute("status",
						UtilConstants._RECOVER_PASSWORD_SUCCESS);
				path = UtilConstants._NEW_PASSWORD;
				session.setAttribute("loginid", loginid);
				System.out.println(path);
			} else {
				request.setAttribute("status",
						UtilConstants._RECOVER_PASSWORD_FAILED);
				path = UtilConstants._RECOVER_PASSWORD;
			}

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("status", "INVALID ENTRIES");

			path = UtilConstants._RECOVER_PASSWORD;
		}

		rd = request.getRequestDispatcher(path);

		rd.forward(request, response);

	}

}

package com.mime.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mime.delegate.NotesDelegate;
import com.mime.util.UtilConstants;

public class DeleteMailsIDsAction extends HttpServlet {
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
				System.out.println(ch[i]);
				flag = new NotesDelegate().deleteMailIDs(Integer
						.parseInt(ch[i]));
			}
			if (flag) {
				request.setAttribute("status",
						UtilConstants._CONTACT_DELETE_SUCCESS);
				path = UtilConstants._CONTACT_STATUS;
			} else {
				request.setAttribute("status",
						UtilConstants._CONTACT_DELETE_FAIL);
				path = UtilConstants._CONTACT_STATUS;
			}
		} catch (Exception e) {
			request.setAttribute("status", e.getMessage());
			path = UtilConstants._CONTACT_STATUS;
		}
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

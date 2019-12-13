package com.mime.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Pop3Action extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2941564269120432640L;
	String target = "fail";
	Process p;
	String path = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd;
		try {
			String switches = request.getParameter("switch");
			path = request.getSession().getServletContext()
					.getRealPath("/POP3");
			if (switches.equalsIgnoreCase("on")) {
				if (p == null)
					Run(path);
				rd = request
						.getRequestDispatcher("/AdminHome.jsp?status=Server Started successfully..");
			} else {
				p.destroy();
				p = null;
				rd = request
						.getRequestDispatcher("/AdminHome.jsp?status=Server Stop successfully..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rd = request
					.getRequestDispatcher("/AdminHome.jsp?status=Some Technical Problem please Try again........");
		}
		rd.forward(request, response);
	}

	void Run(String path) throws Exception {
		Runtime rt = Runtime.getRuntime();
		p = rt.exec(path + "/babypop3.exe");
	}
}

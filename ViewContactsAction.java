package com.mime.action;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mime.bean.NotesDTO;
import com.mime.delegate.NotesDelegate;
import com.mime.util.UtilConstants;

public class ViewContactsAction extends HttpServlet {
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
		Vector<NotesDTO> opv = null;
		try {
			System.out.println("haiii");
			path = request.getRealPath("/images");
			opv = new NotesDelegate().viewContactMails((Integer) session
					.getAttribute("userid"), Integer.parseInt(request.getParameter("groupid")));
			if (!opv.isEmpty()) {
				request.setAttribute("remainderinfo", opv);
				path = UtilConstants._VIEW_REMAINDERS;
			} else if (opv.isEmpty()) {
				request.setAttribute("status", UtilConstants._NO_REAMINDER);
				path = UtilConstants._VIEW_REMAINDERS;
			} else {
				request.setAttribute("status", UtilConstants._NO_REAMINDER);
				path = UtilConstants._VIEW_REMAINDERS;
			}
		} catch (Exception e) {

			request.setAttribute("status", UtilConstants._INVALIED_ENTRY);
			path = UtilConstants._VIEW_REMAINDERS;
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

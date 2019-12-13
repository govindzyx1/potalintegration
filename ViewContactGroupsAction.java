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

public class ViewContactGroupsAction extends HttpServlet {

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
			opv = new NotesDelegate().viewContactGroups((Integer) session
					.getAttribute("userid"));
			if (!opv.isEmpty()) {
				request.setAttribute("viewcontactgroup", opv);
				path = UtilConstants._VIEW_CONTACTS_GROUPS;
			} else if (opv.isEmpty()) {
				request.setAttribute("status", UtilConstants._NO_REAMINDER);
				path = UtilConstants._VIEW_CONTACTS_GROUPS;
			} else {
				request.setAttribute("status", UtilConstants._NO_REAMINDER);
				path = UtilConstants._VIEW_CONTACTS_GROUPS;
			}
		} catch (Exception e) {
			request.setAttribute("status", UtilConstants._NO_REAMINDER);
			path = UtilConstants._VIEW_REMAINDERS;
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

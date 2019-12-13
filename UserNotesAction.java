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
import com.mime.bean.NotesDTO;
import com.mime.delegate.NotesDelegate;
import com.mime.util.UtilConstants;

public class UserNotesAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserNotesAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		boolean flag = false;
		String path = "";
		NotesDTO notes;
		Vector<NotesDTO> notesDTO = null;
		HttpSession session = request.getSession();
		try {
			notesDTO = new NotesDelegate().viewFolders((Integer) session
					.getAttribute("userid"));
			if (notesDTO != null) {
				Iterator it = notesDTO.listIterator();
				while (it.hasNext()) {
						notes = (NotesDTO) it.next();
					System.out.println(notes.getFolderid());
				}
				request.setAttribute("notesDTO", notesDTO);
				request.setAttribute("userid", (Integer) session
						.getAttribute("userid"));
				path = UtilConstants._USER_NOTES;
			} else {
				request.setAttribute("status", UtilConstants._NO_USER_NOTES);
				request.setAttribute("userid", (Integer) session
						.getAttribute("userid"));
				path = UtilConstants._USER_NOTES;
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			request.setAttribute("status", "Enter  Password properly");
			path = UtilConstants._STATUS;
		}
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

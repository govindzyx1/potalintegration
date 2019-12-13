package com.mime.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mime.bean.NotesDTO;
import com.mime.delegate.NotesDelegate;
import com.mime.util.UtilConstants;

public class DisplaysubFoldersAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DisplaysubFoldersAction() {
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
		int folderId = 0;
		NotesDTO notesDTO = new NotesDTO();
		String path = "";
		HttpSession session = request.getSession();
		try {
			if (request.getParameter("folderid") != null) {
				folderId = Integer.parseInt(request.getParameter("folderid"));
				session.removeAttribute("folderid");
				session.setAttribute("folderid", folderId);
				notesDTO.setFolderid(folderId);
			} else {
				folderId = (Integer) session.getAttribute("folderid");
				notesDTO.setFolderid(folderId);
			}

			notesDTO.setUser(request.getParameter("user"));
			notesDTO
					.setRemoveSubfolder(request.getParameter("removeSubfolder"));
			notesDTO.setOldName(request.getParameter("oldName"));
			notesDTO.setRename(request.getParameter("rename"));
			notesDTO.setAddFolder(request.getParameter("addFolder"));
			flag = new NotesDelegate().userFolders(notesDTO);
			if (flag) {
				path = UtilConstants._STATUS;
			} else {
				request.setAttribute(UtilConstants._STATUS_STRING,
						UtilConstants._ERROR_MAILS);
				path = UtilConstants._STATUS;
			}
		} catch (Exception e) {
			request.setAttribute(UtilConstants._STATUS_STRING,
					UtilConstants._ERROR_MAILS);
			path = UtilConstants._STATUS;
		}
		rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}

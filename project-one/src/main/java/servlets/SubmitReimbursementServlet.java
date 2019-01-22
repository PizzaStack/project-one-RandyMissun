package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import models.Reimbursement;

@WebServlet("/SubmitReimbursementServlet")
public class SubmitReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Database dao = new Database();
		HttpSession session = req.getSession(false);
		if (session != null) {
			String username = (String) session.getAttribute("currentUsername");
			String description = req.getParameter("description");
			Reimbursement r = new Reimbursement(username, description);
			System.out.println(req.getParameter("image"));
			r.setImageFromUrl(req.getParameter("image"));
			dao.insertReimbursement(r);
			resp.sendRedirect("./EmployeeHome.html");
		} else {
			resp.sendRedirect("./Login.html");
		}
	}
}

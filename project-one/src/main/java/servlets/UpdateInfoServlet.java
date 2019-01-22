package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import models.Reimbursement;
import models.User;

@WebServlet("/UpdateInfoServlet")
public class UpdateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Database dao = new Database();
		HttpSession session = req.getSession();
		if (session != null) {
			User u = dao.retrieveUser((String) session.getAttribute("currentUsername"));
			List<Reimbursement> reimbursements = dao.retrieveReimbursementsByEmployee(u.username);
			if (!req.getParameter("username").equals("")) {
				u.username = req.getParameter("username");
				session.setAttribute("currentUsername", u.username);
				for (Reimbursement r : reimbursements) {
					r.employee = u.username;
					dao.updateReimbursement(r);
				}
			}
			if (!req.getParameter("password").equals("")) {
				u.password = req.getParameter("password");
				session.setAttribute("currentPassword", u.password);
			}
			dao.updateUser(u);
			resp.sendRedirect("./EmployeeHome.html");
		} else {
			resp.sendRedirect("./Login.html");
		}
	}
}

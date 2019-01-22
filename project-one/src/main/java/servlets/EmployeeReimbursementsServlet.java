package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import models.Reimbursement;

@WebServlet("/EmployeeReimbursementsServlet")
public class EmployeeReimbursementsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		Database dao = new Database();
		HttpSession session = req.getSession(false);
		if (session != null) {
			String username = (String) session.getAttribute("currentUsername");
			List<Reimbursement> reimbursements = dao.retrieveReimbursementsByEmployee(username);
			for (Reimbursement r : reimbursements) {
				out.write(r.toString() + "\n");
			}
		} else {
			resp.sendRedirect("./Login.html");
		}
	}
}

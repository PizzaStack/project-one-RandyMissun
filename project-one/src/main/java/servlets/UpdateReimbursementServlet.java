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
import models.ReimbursementStatus;

@WebServlet("/UpdateReimbursementServlet")
public class UpdateReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Database dao = new Database();
		HttpSession session = req.getSession(false);
		if (session != null) {
			String[] idAndStatus = req.getReader().readLine().split(",");
			System.out.println(idAndStatus[0] + "," + idAndStatus[1]);
			Reimbursement r = dao.retrieveReimbursementById(Integer.parseInt(idAndStatus[0]));
			r.manager = (String) session.getAttribute("currentUsername");
			if (idAndStatus[1].equals("Approved"))
				r.status = ReimbursementStatus.Approved;
			else
				r.status = ReimbursementStatus.Denied;
			System.out.println(r.toString());
			dao.updateReimbursement(r);
			resp.sendRedirect("./EmployeeHome.html");
		} else {
			resp.sendRedirect("./Login.html");
		}
	}
}
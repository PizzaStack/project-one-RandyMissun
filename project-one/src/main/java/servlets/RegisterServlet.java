package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Database;
import models.User;
import models.UserType;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		Database dao = new Database();
		User u = dao.retrieveUser(req.getParameter("username"));
		if (u != null) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Username already exists.');");
			out.println("</script>");
			req.getRequestDispatcher("./Register.html").include(req, resp);
		} else {
			u = new User();
			u.type = UserType.valueOf(req.getParameter("usertype"));
			u.username = req.getParameter("username");
			u.password = req.getParameter("password");
			dao.insertUser(u);
			HttpSession session = req.getSession();
			u = dao.retrieveUser(u.username);
			session.setAttribute("currentId", u.id);
			session.setAttribute("currentType", u.type.toString());
			session.setAttribute("currentUsername", u.username);
			session.setAttribute("currentPassword", u.password);
			if (u.type == UserType.Employee) {
				resp.sendRedirect("./EmployeeHome.html");
			} else {
				resp.sendRedirect("./ManagerHome.html");
			}
		}
	}
}
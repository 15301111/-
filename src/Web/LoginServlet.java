package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.UserService;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("”√ªß√˚");
		String password = request.getParameter("√‹¬Î");

		String role = "";
		int userID = -1;
		// String message = "";

		if (userService.login(name, password)) {
			userID = userService.getUserID(name);
			role = userService.getRolenifo(userID);
		}

		if (role.equals("admin")) {
			System.out.println("this is test");
			HttpSession session = null;
			session = request.getSession();

			session.setAttribute("userName", name);
			session.setAttribute("userID", userID);

			response.sendRedirect("toAdmin");

		} else if (role.equals("operator")) {
			HttpSession session = null;
			session = request.getSession();

			session.setAttribute("userName", name);
			session.setAttribute("userID", userID);

			response.sendRedirect("toOperator");

		}
	}

}

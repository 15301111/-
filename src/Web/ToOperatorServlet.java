package Web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for accessing contract operator page
 */
@SuppressWarnings("serial")
public class ToOperatorServlet extends HttpServlet {

	/**
	 * Jump to contract operator page
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = null;
		session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userID");
		
		if (userId == null) {
			response.sendRedirect("toLogin");
		}else {
			request.getRequestDispatcher("/landing.html").forward(request, response);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}

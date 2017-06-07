package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ToAdminServlet extends HttpServlet {
       


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = null;
		session = request.getSession();
		
		Integer userID = (Integer)session.getAttribute("userID");
		
		if(userID == null){
			response.sendRedirect("toLogin");
		}else{
			request.getRequestDispatcher("/frame1.jsp").forward(request, response);
		}
		
	}
	


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	this.doGet(request, response);
	}
}

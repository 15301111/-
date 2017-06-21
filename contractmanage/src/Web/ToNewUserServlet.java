package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@SuppressWarnings("serial")
public class ToNewUserServlet extends HttpServlet {

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			request.setCharacterEncoding("UTF-8");
			
			HttpSession session = null;
			session = request.getSession();
			
			Integer userId = (Integer)session.getAttribute("userID");
			
			if(userId == null){
				System.out.println("this is my test");
				request.getRequestDispatcher("toLogin");
			}else{
				
				request.getRequestDispatcher("/newUser.jsp").forward(request, response);
			}
	}

}

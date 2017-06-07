package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.UserService;


public class LoginServlet extends HttpServlet {

  


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		int userID = -1;
		String message = "";
		
		
			UserService user = new UserService();
			if(user.login(name, password)){
				userID = 1;
				
			}
			
			if(userID > 0){
				HttpSession session = null;
				session = request.getSession();
				
				session.setAttribute("userName", name);
				session.setAttribute("userID", userID);
				
				response.sendRedirect("toNewUser");
						
			}else{
				message = "用户名或密码错误";
				
				request.setAttribute("message",message);
				request.setAttribute("UserName", name);
				request.setAttribute("userID", userID);
				
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}

	

}

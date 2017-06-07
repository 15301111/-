package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.UserService;
import Utils.DBUtil;


@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

  


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		String role = "";
		int userID = -1;
		//String message = "";
		
		
			UserService user = new UserService();
			DBUtil util = new DBUtil();
			if(user.login(name, password)){
				userID = util.getUserID(name);
				role = util.getRolenifo(userID);
			}
			
			if(role.equals("admin")){
				System.out.println("this is test");
				HttpSession session = null;
				session = request.getSession();
				
				session.setAttribute("userName", name);
				session.setAttribute("userID", userID);
				
				response.sendRedirect("toAdmin");
						
			}else if(role.equals("operator")){
				HttpSession session = null;
				session = request.getSession();
				
				session.setAttribute("userName", name);
				session.setAttribute("userID", userID);
				
				response.sendRedirect("toOperator");
				
			}
		}

	

}

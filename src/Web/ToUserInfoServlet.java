package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.User;
import Service.UserService;

/**
 * Servlet implementation class ToUserInfoManage
 */
@WebServlet("/ToUserInfoServlet")
public class ToUserInfoServlet extends HttpServlet {
	UserService userservice = new UserService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = null;
		session = request.getSession();
		
		Integer userId = (Integer)session.getAttribute("userID");
		
		if(userId == null){
			request.getRequestDispatcher("toLogin");
		}else{
			User user = new User();
			user = userservice.getUserByid(userId);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/用户信息管理.jsp").forward(request, response);
		}
	}

}

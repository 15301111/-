package Web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.CBEntity;
import Entity.User;
import Service.ContractService;
import Service.UserService;

/**
 * Servlet implementation class UserinfoServlet
 */
@WebServlet("/UserinfoServlet")
public class UserinfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       UserService userservice = new UserService();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		
		HttpSession session = null;
		boolean flag = false;
		session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userID");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
		
		if (userId == null) {
			response.sendRedirect("toLogin");
		}else {
			User user = new User();
			user = userservice.getUserByid(userId);
			request.setAttribute("user", user);
			if( userservice.changePersoninfo(userId, password, password2)){
				System.out.println("修改信息成功");
				response.sendRedirect("toAdmin");
			}else{
				System.out.println("修改信息失败");
				response.sendRedirect("ToUserInfoServlet");
			}
			
			
			
	   	}
	

	}

}

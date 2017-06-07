package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.User;
import Service.UserService;


@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet{

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		System.out.println("this is my test1");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
		boolean flag = false;
		
		String massage = "";
		
		User user = new User();
		UserService userService = new UserService();
		
		
		if(name == ""|| password == "" ||password2 == ""){
			System.out.println("不能为空");
			massage = "不能为空";
		}else if(!password2.equals(password)){
			System.out.println("两次密码不一致");
			massage = "两次密码不一致";
		}else{
			user.setName(name);
			user.setPassword(password);
			
			
			flag = userService.register(name, password);
			if(flag){
				//System.out.println("注册成功！");
				
				massage = "注册成功";
			}else{
				System.out.println("注册失败");
				massage = "注册失败";
			}		
		}
		request.setAttribute("massage", massage);
		//System.out.println(request.getAttribute("massage")+"this is test");
		
		request.getRequestDispatcher("/toregister").forward(request, response);
	}
	
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
	}
	

}

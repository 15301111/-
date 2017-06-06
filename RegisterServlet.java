package Web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.User;
import Service.UserService;


public class RegisterServlet extends HttpServlet{
	
	public void dopost(HttpServletRequest request, HttpServletResponse respose) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
		boolean flag = false;
		
		String massage = "";
		
		User user = new User();
		UserService userService = new UserService();
		
		
		if(name == ""|| password == "" ||password2 == ""){
			System.out.println("����Ϊ��");
			massage = "����Ϊ��";
		}else if(!password2.equals(password)){
			System.out.println("�������벻һ��");
			massage = "�������벻һ��";
		}else{
			user.setName(name);
			user.setPassword(password);
			
			flag = userService.register(name, password);
			if(flag){
				System.out.println("ע��ɹ���");
				massage = "ע��ɹ�";
			}else{
				System.out.println("ע��ʧ��");
				massage = "ע��ʧ��";
			}		
		}
		request.setAttribute("massage", massage);
		
		request.getRequestDispatcher("/toRegister").forward(request, respose);
	}
	
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
	}
	

}

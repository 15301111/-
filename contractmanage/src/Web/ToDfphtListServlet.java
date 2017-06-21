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
import Service.ContractService;
import Service.UserService;


public class ToDfphtListServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		
		HttpSession session = null;
	
		session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userID");
		
		
		if (userId == null) {
			response.sendRedirect("toLogin");
		}else {
			ContractService contractservice = new ContractService();
			
			List<CBEntity>  contractList = new ArrayList<CBEntity>();
			
			contractList = contractservice.getDraphtList();
			
			request.setAttribute("contractList", contractList);
			
			request.getRequestDispatcher("/∑÷≈‰∫œÕ¨.jsp").forward(request, response);
	   	}
	}
}



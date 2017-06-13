package Web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.CBEntity;
import Service.ContractService;
import Utils.AppException;

public class ToSignedListServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = null;
		session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userID");
		
		if (userId == null) {
			response.sendRedirect("toLogin");
		}else {
			
			try {
				ContractService contractService = new ContractService();
				List<CBEntity> contractList = new ArrayList<CBEntity>();
				contractList = contractService.getSignedList(userId);
				request.setAttribute("contractList", contractList);
				request.getRequestDispatcher("/signedList.jsp").forward(request, response);
			} catch (AppException e) {
				e.printStackTrace();
				response.sendRedirect("toError");
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

}

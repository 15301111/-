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

public class ToSCListServlet extends HttpServlet{
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
				contractList = contractService.getSCList(userId);
				request.setAttribute("contractList", contractList);
				request.getRequestDispatcher("/给脸哥（原scList）需要写跳转.jsp").forward(request, response);
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

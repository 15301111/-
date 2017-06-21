package Web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.ConProcess;
import Service.ContractService;
import Utils.AppException;

public class BreachServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = null;
		session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userID");
		
		int conId = Integer.parseInt(request.getParameter("conId"));
		
		ConProcess conProcess = new ConProcess();
		conProcess.setConId(conId);
		conProcess.setUserId(userId);
		
		ContractService contractService = new ContractService();
		try {
			contractService.breach(conProcess);
			response.sendRedirect("toBreach");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}

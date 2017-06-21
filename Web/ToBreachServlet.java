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
import Utils.AppException;
import Service.ContractService;

@SuppressWarnings("serial")
public class ToBreachServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//��ȡ��ǰ�û�id
		Integer userID;
		HttpSession session = null;
		session = request.getSession();
		userID = (Integer)session.getAttribute("userID");
		
		try {
			ContractService contractService = new ContractService();
			List<CBEntity> contractList = new ArrayList<CBEntity>();
			//��ȡ��Ҫ��˺�ͬ��list
			contractList = contractService.getSignedList(userID);			
			request.setAttribute("contractList", contractList);
			request.getRequestDispatcher("/breach.jsp").forward(request, response);
		} catch (AppException e) {
			e.printStackTrace();
			
			response.sendRedirect("toError");
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doPost(request, response);
	}
}

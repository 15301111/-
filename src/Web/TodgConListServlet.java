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
import Service.ContractService2;

public class TodgConListServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	
	Integer userID;
	HttpSession session = null;
	session = request.getSession();
	userID = (Integer)session.getAttribute("userID");
	
	ContractService2 contractService2 = new ContractService2();
	List<CBEntity> contractList = new ArrayList<CBEntity>();
	contractList = contractService2.getdgConList(userID);			
	request.setAttribute("contractList", contractList);
	
	request.getRequestDispatcher("/¸øÅô¸ç£¨Ô­ddghtList£©.jsp").forward(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}

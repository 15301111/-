package Web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Contract;
import Service.ContractService;

public class ConfirmListSerlvet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 322914919356373748L;
	private ContractService contractService = new ContractService();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = null;
		session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userID");
		List<Contract> contractList = contractService.getContractList(userId,2);
		for (Contract contract : contractList) {
			System.out.println(contract);
		}
		request.setAttribute("list", contractList);
		request.getRequestDispatcher("敲定合同列表.jsp").forward(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}

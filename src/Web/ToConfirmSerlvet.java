package Web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Contract;
import Service.ContractService;

public class ToConfirmSerlvet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4990929738299485951L;
	private ContractService contractService = new ContractService();
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		Contract contract = contractService.getContract(name);
		request.setAttribute("contract", contract);
		request.getRequestDispatcher("ÇÃ¶¨ºÏÍ¬.jsp").forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}

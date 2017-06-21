package Web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.Contract;
import Service.ContractService;

/**
 * Servlet implementation class ToContractinfoServlet
 */
@WebServlet("/ToContractinfoServlet")
public class ToContractinfoServlet extends HttpServlet {
	ContractService conservice = new ContractService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Contract> contractlist = new ArrayList<Contract>();
		contractlist = conservice.getallcontractList();
		request.setAttribute("contractList", contractlist);
		request.getRequestDispatcher("/合同信息查询.jsp").forward(request, response);
	}

}

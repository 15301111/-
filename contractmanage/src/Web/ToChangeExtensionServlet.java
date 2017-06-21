package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Contract;
import Service.ContractService;
import Utils.AppException;

/**
 * Servlet implementation class ToChangeExtensionServlet
 */
@WebServlet("/ToChangeExtensionServlet")
public class ToChangeExtensionServlet extends HttpServlet {

	ContractService conservice = new ContractService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contract contract = new Contract();
		int conId = Integer.parseInt(request.getParameter("conId"));
		try {
			contract = conservice.getContract(conId);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("contract", contract);
		request.getRequestDispatcher("/changeextension.jsp").forward(request, response);
	}

}

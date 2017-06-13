package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entity.Contract;
import Service.ContractService;
import Utils.AppException;

@SuppressWarnings("serial")
public class ToACOperateServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//获取当前选择合同id
		int ACId = Integer.parseInt(request.getParameter("conId"));

		try {
			ContractService contractService = new ContractService();
			Contract contract = contractService.getContract(ACId);

			request.setAttribute("contract", contract);
			request.getRequestDispatcher("/审批合同acorporate（存在radio无法显示问题）.html").forward(
					request, response);
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

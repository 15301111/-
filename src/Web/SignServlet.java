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
import Utils.Constant;

public class SignServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = null;
		session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userID");

		if (userId == null) {
			response.sendRedirect("toLogin");
		} else {

			int conId = Integer.parseInt("3");
			String content = request.getParameter("content");

			ConProcess conProcess = new ConProcess();
			conProcess.setConId(conId);
			conProcess.setUserId(userId);
			conProcess.setContent(content);
			
			try {
				ContractService contractService = new ContractService();
				contractService.sign(conProcess);

				response.sendRedirect("toSCList");
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
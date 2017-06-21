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

public class ApprovingServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = null;
		session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userID");

		if (userId == null) {
			response.sendRedirect("toLogin");
		} else {
			//��ȡid
			int conId = Integer.parseInt(request.getParameter("conId"));
			//��д����
			String content = request.getParameter("content");
			//�Ƿ�ͨ��
			String approveState = request.getParameter("approve");

			ConProcess conProcess = new ConProcess();
			conProcess.setConId(conId);
			conProcess.setUserId(userId);
			conProcess.setContent(content);
			
			if (approveState.equals("true")) {
				conProcess.setState(Constant.DONE);
			} else {
				conProcess.setState(Constant.VETOED);
			}

			try {
				ContractService contractService = new ContractService();
				//д�����ݿⲢ����
				contractService.approving(conProcess);
				//ˢ��
				response.sendRedirect("toACList");
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
package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utils.Constant;
import Service.ContractService;


public class AssignOperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");   
		HttpSession session = null;
		session = request.getSession();
		Integer userID = (Integer)session.getAttribute("userID");
		
		if(userID == null){
			response.sendRedirect("toLogin");
		}else{
			
			int conId = Integer.parseInt(request.getParameter("conId"));
			
			String[] hqht = request.getParameterValues("hqht");
		
			String[] spht = request.getParameterValues("spht");
			
			String[] qdht = request.getParameterValues("qdht");

			
				
				ContractService contractService = new ContractService();

				for (String hq : hqht) {
					contractService.distribute(conId, Integer.parseInt(hq),
							Constant.PROCESS_CSIGN);
				}

				
				for (String sp : spht) {
					contractService.distribute(conId, Integer.parseInt(sp),
							Constant.PROCESS_APPROVE);
				}

				
				for (String qd : qdht) {
					contractService.distribute(conId, Integer.parseInt(qd),
							Constant.PROCESS_SIGN);
				}
				
				response.sendRedirect("toDfphtList");
			
			
		}
	}

}

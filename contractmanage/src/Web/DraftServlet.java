package Web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Contract;
import Service.ContractService;


public class DraftServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    
	    HttpSession session = null;
	    
	    session = request.getSession();
	    String message = "";
	    
	    Integer userID = (Integer)session.getAttribute("userID");
	    
	    if(userID == null){
	    	response.sendRedirect("toLogin");
	    }else{
			String name = request.getParameter("name");
			String customer = request.getParameter("customer");
			String content = request.getParameter("content");
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			
			Date begin = new Date();
			Date end =new Date();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			
			
			try {
				begin = format.parse(beginTime);
				end = format.parse(endTime);
				
				Contract contract = new Contract();
				
				contract.setUserId(userID);
				contract.setCustomer(customer);
				contract.setName(name);
				contract.setContent(content);
				contract.setBeginTime(begin);
				contract.setEndTime(end);
				
				ContractService contractservice = new ContractService();
				if(contractservice.Draft(contract)){
					System.out.println("起草成功！");
					message = "起草成功";
					
				}else{
					System.out.println("起草失败！");
					message = "起草失败";
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("添加合同（原addContract）.jsp").forward(request, response);
	    }
	}

}

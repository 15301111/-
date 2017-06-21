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


	/**
	 * 
	 */
	private static final long serialVersionUID = 7756117054192244809L;
	private ContractService contractService = new ContractService();


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
			String breachString = request.getParameter("chengfa");
			int breach = Integer.parseInt(breachString);
			
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
				contract.setBreach(breach);
				
				if(contractService.Draft(contract)){
					System.out.println("起草成功！");
					message = "起草成功";
					
				}else{
					System.out.println("起草失败！");
					message = "起草失败";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("landing.html").forward(request, response);
	    }
	}

}

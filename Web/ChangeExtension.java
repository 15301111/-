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

import Service.ContractService;

/**
 * Servlet implementation class ChangeExtension
 */
@WebServlet("/ChangeExtension")
public class ChangeExtension extends HttpServlet {

	ContractService conservice = new ContractService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String endtime = request.getParameter("xyTime");
		String name = request.getParameter("name");
		Date newtime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		try {
			newtime = format.parse(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean flag = false;
		flag = conservice.updateEndTimeByname(name, newtime);
		
		response.sendRedirect("toAdmin");
		
	}

}

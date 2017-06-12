package Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ToDraftServlet extends HttpServlet {




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
        	   request.getRequestDispatcher("/addContract.jsp").forward(request, response);
           }
           
	}

}

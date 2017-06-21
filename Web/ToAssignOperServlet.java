package Web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Contract;
import Entity.User;
import Service.ContractService;
import Service.UserService;
import Utils.AppException;


public class ToAssignOperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Set the request's character encoding
				request.setCharacterEncoding("UTF-8");
				// Declare session
				HttpSession session = null;
				// Get session by using request
				session = request.getSession();
				Integer userId = (Integer)session.getAttribute("userID");
				
				// If user is not login, jump to login page
				if (userId == null) {
					response.sendRedirect("toLogin");
				}else {
					// Get contract id
					int conId = Integer.parseInt(request.getParameter("conId"));
					
				
					// Initialize contractService
					ContractService contractService = new ContractService();
					// Query contract information according to contract id
					Contract contract = new Contract();
					try {
						contract = contractService.getContract(conId);
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Initialize userService
					UserService userService = new UserService();

					String  role = "operator";
					// Initialize userList
					List<User> userList = new ArrayList<User>();
					// Get user list according to role id
					userList = userService.getUserListByRole(role);
					
					// Save contract to request
					request.setAttribute("contract", contract);
					// Save userList to request
					request.setAttribute("userList", userList);
					// Forward to operator assignment page
					request.getRequestDispatcher("/给富总（原assignOperator）.jsp").forward(request,
							response);
					
				}

	}

}

package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateDaoImpl;
import dao.UserDaoImplementation;
import pojos.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDaoImplementation userDao = null;
	CandidateDaoImpl candidateDao = null;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		try {
			 userDao = new UserDaoImplementation();
			 candidateDao = new CandidateDaoImpl();
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		
		try {
			userDao.cleanUp();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		try(PrintWriter pw = response.getWriter()){
			User user = userDao.authenticateUser(request.getParameter("email"), request.getParameter("passwd"));
			if(user==null) {
				pw.print("<h4>Invalid Email or Password.., Please <a href='login.html'>Retry</a></h4>");
			}else {
				
				//Cookie c1 = new Cookie("userCookie", user.toString());
				HttpSession hs = request.getSession();
				hs.setAttribute("userDetails", user);
				hs.setAttribute("user_Dao", userDao);
				hs.setAttribute("candidate_Dao", candidateDao);
				
				if(user.getRole().equals("admin")) {
					response.sendRedirect("admin");
				}else {
					if(user.isStatus() == true) {
						response.sendRedirect("logout");
					}else {
					//	response.addCookie(c1);
						response.sendRedirect("candidate");
					}
				}
			}
			
		} catch (SQLException e) {
			throw new ServletException("err in do-post"+getClass() ,e);
		}
		
	}

}

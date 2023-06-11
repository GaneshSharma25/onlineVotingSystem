package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImplementation;

/**
 * Servlet implementation class RegisterVoter
 */
@WebServlet("/register")
public class RegisterVoter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDaoImplementation userDao;

	public void init() throws ServletException {
		try {
			userDao = new UserDaoImplementation();
		} catch (SQLException e) {
			throw new ServletException();
		}

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			userDao.cleanUp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {

			try {
				
				int check = userDao.addUser(request.getParameter("fname"), request.getParameter("lname"),
						request.getParameter("email"), request.getParameter("password"),
						Date.valueOf(request.getParameter("dob")));

				if (check == 1) {
					pw.print("<h3 style='color: #008000; text-align: center; font-size: 24px;'>Congratulations, " + request.getParameter("fname") + "! Registration Successful.</h3>");
					pw.print("<div style='text-align: center;'>");
					pw.print("  <a href='login.html'>Go to Login Page</a>");
					pw.print("</div>");

				} else if (check == 2) {
					pw.print("<h3 style='color: #ff0000; text-align: center; font-size: 24px;'>Registration Failed..</h3>");
					pw.print("<div style='text-align: center;'>");
					pw.print("  <a href='register.html'>Retry</a> &nbsp; &nbsp;");
					pw.print("  <a href='login.html'>Go to Login Page</a>");
					pw.print("</div>");
				} else {
					pw.print("<h3 style='color: #ff0000; text-align: center;'>Registration Failed</h3>");
					pw.print("<p>Oops, it seems you're not old enough to cast your vote just yet!</p>");
					pw.print("<p>No worries though, you'll be able to participate in future elections when you turn 21. In the meantime, there's plenty of time to get informed about the issues and candidates.</p>");
					pw.print("<p>Feel free to <a href='register.html'>retry</a> the registration process once you meet the minimum age requirement. If you'd like to explore more, you can also <a href='login.html'>go to the login page</a> and see what else is happening.</p>");

				}

			} catch (Exception e) {
				pw.print("<h3 style='color: #ff0000; text-align: center; font-size: 24px;'>Registration Failed..</h3>");
				pw.print("<div style='text-align: center;'>");
				pw.print("  <a href='register.html'>Retry</a> &nbsp; &nbsp;");
				pw.print("  <a href='login.html'>Go to Login Page</a>");
				pw.print("</div>");
                System.out.println(e);
				throw new ServletException();

			}
		}

	}
}

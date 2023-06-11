package pages;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		try(PrintWriter pw = response.getWriter()){
			pw.print("<h4 style='text-align: center;'>In Logout.. Servlet..</h4>");
			HttpSession session = request.getSession();
			
			User voter = (User) session.getAttribute("userDetails");
			if(voter != null) {
				
				if(voter.isStatus()) {
					pw.print("<h4 style='text-align: center;'> hello "+voter.getFirstName()+" </h4>");
					pw.print("<h4 style='text-align: center;'> you have already voted...</h4>");
				}else {
					
					UserDaoImplementation userDao = (UserDaoImplementation) session.getAttribute("user_Dao");
					CandidateDaoImpl canDao = (CandidateDaoImpl) session.getAttribute("candidate_Dao");
					pw.print("<h4 style='text-align: center;' > hello "+voter.getFirstName()+" </h4>");
					int candidateId = Integer.parseInt(request.getParameter("cid")) ;
					
					pw.print("<h4 style='text-align: center;'>" +userDao.updateVotingStatus(voter.getId()) +"</h4>");
					pw.print("<h4 style='text-align: center;'>"+canDao.incrementVote(candidateId) +"</h4>");
				}
				
			}else {
			    pw.print("<h5 style='text-align: center;'>No Session tracking..</h5>");
			    session.invalidate();
			    pw.print("<h5 style='text-align: center;'> You have been logged out..</h5>");
			    
			}
			
			
		}catch(Exception e) {
			throw new ServletException("err in do-get..",e);
		}
	}

}

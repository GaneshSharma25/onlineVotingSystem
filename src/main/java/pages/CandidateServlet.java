package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateDaoImpl;
import pojos.Candidate;
import pojos.User;

/**
 * Servlet implementation class CandidateServlet
 */
@WebServlet("/candidate")
public class CandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
//		try(PrintWriter pw = response.getWriter()){
//			pw.print("<h4>Inside Candidate Servlet..</h4>");	
//			//pw.print("<h4> Validated User email = "+response.getHeader("email"));
//			String str="";
//			Cookie[] cookie = request.getCookies();
//			if(cookie != null) {
//				for (Cookie c : cookie) {
//					if(c.getName().equals("userCookie")) {
//						str = c.getValue();
//						break;
//					}
//				}
//				pw.print("<h4> Validated User email = "+str+"</h4>");
//			}else {
//				pw.print("<h4> cannot show user detail Cookie/s is blocked.." +"</h4>");
//			}
//		}
		try (PrintWriter pw = response.getWriter()) {
			HttpSession session = request.getSession();
			User userDetail = (User)session.getAttribute("userDetails") ;
			
			if (userDetail != null) {
			    pw.print("<h4 style='text-align: center;'>Hello " + userDetail.getFirstName() + " " + userDetail.getLastName() + ", cast your vote.</h4>");

			    CandidateDaoImpl candidateDao = (CandidateDaoImpl) session.getAttribute("candidate_Dao");

			    List<Candidate> candidates = candidateDao.getAllCandidate();
			    pw.print("<form action='logout' style='text-align: center;'>");
			    for (Candidate candidate : candidates) {
			        pw.print("<h4><input type='radio' name='cid' value='" + candidate.getCandidateId() + "'/> " + candidate.getName() + "</h4>");
			    }
			    pw.print("<button type='submit' name='btn'>Vote</button>");
			    pw.print("</form>");
			} else {
			    pw.print("<h5 style='text-align: center;'>Session tracking failed... No cookie.</h5>");
			}
              } catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}

}

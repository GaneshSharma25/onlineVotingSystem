package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateDaoImpl;
import dao.UserDaoImplementation;
import pojos.Candidate;

/**
 * Servlet implementation class AdminPage
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			HttpSession admin = request.getSession();
			CandidateDaoImpl canDao = new CandidateDaoImpl();
			List<Candidate> topTwoCandidates = canDao.getTopTwoCandidates();

			pw.print("<h4 style='font-weight: bold;'>In Admin Servlet..</h4>");
			pw.print("<hr>");
			pw.print("<div>");
			pw.print("<h2 style='text-align: center;'>TOP TWO CANDIDATES WITH MAX VOTES</h2>");
			for (Candidate candidate : topTwoCandidates) {
				pw.print("<p style='margin-bottom: 5px;'><span style='font-weight: bold;'>ID:</span> "
						+ candidate.getCandidateId() + "</p>");
				pw.print("<p style='margin-bottom: 5px;'><span style='font-weight: bold;'>Name:</span> "
						+ candidate.getName() + "</p>");
				pw.print("<p style='margin-bottom: 5px;'><span style='font-weight: bold;'>Party:</span> "
						+ candidate.getParty() + "</p>");
				pw.print("<p style='margin-bottom: 10px;'><span style='font-weight: bold;'>Votes:</span> "
						+ candidate.getVotes() + "</p>");
			}
			pw.print("</div>");
			pw.print("<hr>");

			Map<String, Integer> votesAnalysisByParty = canDao.getVotesAnalysisByParty();
			pw.print("<hr>");
			pw.print("<div>");
			pw.print("<h2 style='text-align: center;'>VOTE ANALYSIS BY POLITICAL PARTY</h2>");
			pw.print("<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>");
			pw.print(
					"<tr style='background-color: #f2f2f2;'><th style='padding: 10px; text-align: left;'>Party</th><th style='padding: 10px; text-align: left;'>Votes</th></tr>");
			votesAnalysisByParty.forEach((party, votes) -> pw.print("<tr><td style='padding: 10px;'>" + party
					+ "</td><td style='padding: 10px;'>" + votes + "</td></tr>"));
			pw.print("</table>");
			pw.print("</div>");
			pw.print("<hr>");

		} catch (SQLException e) {
			throw new ServletException("Err in admin Servlet: ", e);
		}
	}

}
/*
 * pw.print("<h4>In Admin Servlet..</h4>"); pw.print("<hr>"); pw.print("<div>");
 * pw.print("<h2> TOP TWO CANDIDATES WITH MAX VOTES </h2>"); for (Candidate
 * candidate : topTwoCandidates) { pw.print("<h4>" + "ID: " +
 * candidate.getCandidateId() + ", Name: " + candidate.getName() + ", Party: " +
 * candidate.getParty() + ", Votes: " + candidate.getVotes() +"<br>"); }
 * pw.print("</h4>"); pw.print("</div>"); pw.print("<hr>");
 * 
 * Map<String, Integer> votesAnalysisByParty = canDao.getVotesAnalysisByParty();
 * pw.print("<hr>"); pw.print("<div>");
 * pw.print("<h2> VOTE ANALAYSIS BY POLITICAL PARTY</h2>");
 * pw.print("<table border='2px'>");
 * votesAnalysisByParty.forEach((party,votes)-> pw.print("<tr> <td>"+party
 * +"</td> <td> "+votes+"</td></tr>")); //
 * pw.print("<h4>"+votesAnalysisByParty+"</h4>"); pw.print("</table>");
 * pw.print("</div>"); pw.print("<hr>");
 */

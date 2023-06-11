package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pojos.Candidate;
import utils.DbUtils;

public class CandidateDaoImpl implements CandidateDao {

	private Connection cn;
	private PreparedStatement pst1;
	private PreparedStatement pst2;
	private PreparedStatement pst4;
	private PreparedStatement pst5;

	public CandidateDaoImpl() throws SQLException {
		cn = DbUtils.openConnection();
		pst1 = cn.prepareStatement("select * from candidates");
		pst2 = cn.prepareStatement("update candidates set votes = votes + 1 where id = ?");
		pst4 = cn.prepareStatement("select * from candidates order by votes desc limit 2");
		pst5 = cn.prepareStatement("select party, sum(votes) from candidates group by party order by sum(votes) desc;");
		System.out.println("CandidateDao created..");
	}

	@Override
	public List<Candidate> getAllCandidate() throws SQLException {
		try (ResultSet rst = pst1.executeQuery()) {
			List<Candidate> candidates = new ArrayList<Candidate>();
			while (rst.next()) {
				candidates.add(new Candidate(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4)));
			}
			return candidates;
		}
	}

	public String incrementVote(int id) throws SQLException {
		pst2.setInt(1, id);
		int update = pst2.executeUpdate();
		if (update == 1) {
			return "vote incremented successfull..";
		} else {
			return "vote increment unsuccessfull..";
		}

	}

	public List<Candidate> getTopTwoCandidates() throws SQLException {
		ResultSet rst = pst4.executeQuery();
		// Candidate candidate;
		List<Candidate> candidates = new ArrayList<Candidate>();
		while (rst.next()) {
			candidates.add(new Candidate(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4)));
		}
		return candidates;
	}

	public Map<String, Integer> getVotesAnalysisByParty() throws SQLException {
		ResultSet rst = pst5.executeQuery();
		Map<String, Integer> voteMap = new LinkedHashMap<String, Integer>();
		while (rst.next()) {
			voteMap.put(rst.getString(1), rst.getInt(2));
		}
		return voteMap;

	}

	public void cleanDao() throws SQLException {
		if (pst1 != null) {
			pst1.close();
		}
		DbUtils.closeConnection();
		System.out.println("Candidate Dao Closed...");
	}
}

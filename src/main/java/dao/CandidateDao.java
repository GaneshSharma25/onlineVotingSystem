package dao;

import java.sql.SQLException;
import java.util.List;

import pojos.Candidate;

public interface CandidateDao {
	
	List<Candidate> getAllCandidate() throws SQLException;

}

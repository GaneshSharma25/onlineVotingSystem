package dao;

import static utils.DbUtils.closeConnection;
import static utils.DbUtils.openConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pojos.Candidate;
import pojos.User;

public class UserDaoImplementation implements UserDao {

	private Connection cn;
	private PreparedStatement pst1;
	private PreparedStatement pst2;
	private PreparedStatement pst3;

	public UserDaoImplementation() throws SQLException {
		cn = openConnection();
		pst1 = cn.prepareStatement("select * from users where email = ? and password = ?");
		pst2 = cn.prepareStatement(
				"insert into users(first_name,last_name,email,password,dob,status,role) values(?,?,?,?,?,?,?)");
		pst3 = cn.prepareStatement("update users set status = 1 where id = ?");

		
	}

	@Override
	public User authenticateUser(String email, String password) throws SQLException {

		pst1.setString(1, email);
		pst1.setString(2, password);

		try (ResultSet rst = pst1.executeQuery()) {
			if (rst.next()) {
				// id | first_name | last_name | email | password | dob | status | role
				User user = new User(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
						rst.getString(5), rst.getDate(6), rst.getBoolean(7), rst.getString(8));
				return user;
			}

		}
		return null;
	}
	
	public int addUser(String firstName,String lastName,String email, String password, Date dob) throws SQLException {
		
		pst2.setString(1, firstName);
		pst2.setString(2, lastName);
		pst2.setString(3, email);
		pst2.setString(4, password);
		pst2.setDate(5, dob);
		pst2.setInt(6, 0);
		pst2.setString(7, "voter");
		
		 int age = Period.between( dob.toLocalDate(),LocalDate.now()).getYears() ;
		System.out.println(age);
		if(age<21) {
			System.out.println("Insert Unsuccessfull..Minimum age should be 21 to cast vote.");	
			return -1;
		}
			pst2.execute();
			int count = pst2.getUpdateCount();
			if(count == 1) {
				System.out.println("Insert Succesfull!!!");
				return 1;
				
			}else {
				System.out.println("Insert Unsuccessfull..");
				return 2;
			}
			
		}	
	
	public String updateVotingStatus(int voterId) throws SQLException {
		pst3.setInt(1, voterId);
		int update = pst3.executeUpdate();
		
		if(update == 1) {
			return "Voting Status Updated Successfully..";
		}else {
			return "Status Update unsuccessfull..";
		}
	}
	

	public void cleanUp() throws SQLException {
		if (pst1 != null) {
			pst1.close();
		}
		if(pst2 != null) {
			pst2.close();
		}
			closeConnection();
			System.out.println("user DAO cleaned..");	
	}

}

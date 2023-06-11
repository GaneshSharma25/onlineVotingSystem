package dao;

import java.sql.SQLException;

import pojos.User;

public interface UserDao {
	
	User authenticateUser(String email, String password) throws SQLException;

}

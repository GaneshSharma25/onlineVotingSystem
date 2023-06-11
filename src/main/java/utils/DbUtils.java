package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
	
	private static Connection cn;
	
	public static Connection openConnection() throws SQLException {
		String dbUrl = "jdbc:mysql://localhost:3306/advjava?useSSL=false&allowPublicKeyRetrieval=true";
		cn = DriverManager.getConnection(dbUrl, "root", "ganesh123");
		System.out.println("DB Connection established..");
		return cn;
	}
	
	public static void closeConnection() throws SQLException {
		if(cn!=null) {
			cn.close();
		}
		System.out.println("DB Connection Closed");
	}
}

package UserInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection connect() throws SQLException {
	    String url = "jdbc:sqlite:C:/Users/WassimIssa/Desktop/UserInterface/userinterface.db";
	    Connection conn = DriverManager.getConnection(url);
	    System.out.println("✅ SQLite connection successful");
	    return conn;
	}
}
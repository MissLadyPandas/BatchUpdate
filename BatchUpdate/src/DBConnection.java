import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// database connection string requirements
public class DBConnection {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/user_database?useSSL=false"; 
	private static final String JDBC_USER = "root"; 
	private static final String JDBC_PASSWORD = ""; 
	// connection method to get a Connection object to the MySQL with defined credentials
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}


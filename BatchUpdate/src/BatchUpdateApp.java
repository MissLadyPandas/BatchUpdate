import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchUpdateApp {

	//main method
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            // creates table
            createTable(connection);

            // batch update, calculate time
            long batchStartTime = System.currentTimeMillis();
            executeBatchUpdate(connection);
            long batchDuration = System.currentTimeMillis() - batchStartTime;
            System.out.println("Batch update completed. Time taken: " + batchDuration + "ms");

            // non-Batch update, calculate time
            long nonBatchStartTime = System.currentTimeMillis();
            executeNonBatchUpdate(connection);
            long nonBatchDuration = System.currentTimeMillis() - nonBatchStartTime;
            System.out.println("Non-Batch update completed. Time taken: " + nonBatchDuration + "ms");

          //exception catch for debugging
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //method to create Temp in database, SQL command, create statement object and execute table command
    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Temp (num1 DOUBLE, num2 DOUBLE, num3 DOUBLE)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }
    //method to insert 1000 records with batch update, SQL command, create PreparedStatement and insert values, loop 1000 times to insert 1000 records
    private static void executeBatchUpdate(Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            for (int i = 0; i < 1000; i++) {
                pstmt.setDouble(1, Math.random() * 100);
                pstmt.setDouble(2, Math.random() * 100);
                pstmt.setDouble(3, Math.random() * 100);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
    // method to insert without update, SQL command, create PreparedStatement and insert values, loop 1000 times to insert 1000 records
    private static void executeNonBatchUpdate(Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            for (int i = 0; i < 1000; i++) {
                pstmt.setDouble(1, Math.random() * 100);
                pstmt.setDouble(2, Math.random() * 100);
                pstmt.setDouble(3, Math.random() * 100);
                pstmt.executeUpdate();
            }
        }
    }
}

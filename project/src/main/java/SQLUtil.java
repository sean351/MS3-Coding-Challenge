import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SQLUtil {


    protected Connection conn = null;
    protected Statement statement = null;
    protected PreparedStatement preparedStatement = null;
    protected String dbName = "CodingChallenge";


    /**
     * These are the methods to be implemented.
     * CONNECT
     * DISCONNECT
     * CREATE TABLE
     * DROP TABLE
     * UPDATE TABLE
     * SElECT RECORD
     * DELETE RECORD
     * CHECK IF TABLE EXISTS
     * CHECK IF RECORD EXISTS
     */


    /**
     * This method allows connection to the database.
     * If the database requested does NOT exist, it will create a new DB
     */
    public void connect() {
        if (conn != null) {
            return;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            System.out.println("Opened database successfully");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * This method will disconnect any connections to the Database.
     */
    public void disconnect() {
        if (conn != null) {
            return;
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method will check to see if a table already exists
     *
     * @param tableName
     * @return The boolean result if a table exists.
     */
    public boolean tableExists(String tableName) {
        connect();
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            rs.last();

            return rs.getRow() > 0;

        } catch (SQLException ex) {

        }
        disconnect();
        return false;
    }


    /**
     * This method will create a new table with the given params.
     *
     * @param tableName
     * @param createSQL
     */
    public void createTable(String tableName, String createSQL) {
        connect();


        try {
            statement = conn.createStatement();

            statement.executeUpdate(createSQL);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        disconnect();
    }

    /**
     * This method will DROP the table from the Database
     *
     * @param tableName
     */
    public void dropTable(String tableName) {
        connect();

        try {
            statement = conn.createStatement();
            Boolean res = statement.execute("DROP TABLE " + dbName + "." + tableName);

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        disconnect();
    }

    /**
     * This method will update the table records
     *
     * @param tableName
     * @param updateSQL
     * @param tableContents
     */
    public void updateRecord(String tableName, String updateSQL, HashMap<Integer, String> tableContents) {
        connect();
        try {
            preparedStatement = conn.prepareStatement(updateSQL);


            for (Integer i : tableContents.keySet()) {
                preparedStatement.setString(i, tableContents.get(i));
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        disconnect();
    }


    /**
     * This method will delete records from a table
     *
     * @param tableName
     * @param deleteSQL
     * @param tableContents
     */
    public void deleteRecord(String tableName, String deleteSQL, HashMap<Integer, String> tableContents) {

    }


}

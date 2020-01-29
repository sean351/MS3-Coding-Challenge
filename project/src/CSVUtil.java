import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CSVUtil {



    private static String fileSplitBy = ",";
    private static String fileContents = null;


    public static void main(String[] args){
        //args[0] = "-f"
        //args[1] = "filename"
        String[] currentTable = importCSV(args[1],"csv");
    }


    public static String[] importCSV(String fileName, String fileType){
        BufferedReader bufferedReader = null;
        String fileLine = "";
        String[] table = null;

        File importFile = new File(fileName);

        if(importFile.isFile()){
            //do stuff
            try {

                bufferedReader = new BufferedReader(new FileReader(importFile));
                while ((fileLine = bufferedReader.readLine()) != null) {

                    table = fileLine.split(fileSplitBy);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.err.println(fileName + " Not Found");
        }


        return table;
    }

    public static void importDB(String dbName,String fileType, String tableName, String[] fileContents) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + "(A,B,C,D,E,F,G,H,I,J) values (?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1,fileContents[0]);
            statement.setString(2,fileContents[1]);
            statement.setString(3,fileContents[2]);
            statement.setString(4,fileContents[3]);
            statement.setString(5,fileContents[4]);
            statement.setString(6,fileContents[5]);
            statement.setString(7,fileContents[6]);
            statement.setString(8,fileContents[7]);
            statement.setString(9,fileContents[8]);
            statement.setString(10,fileContents[9]);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

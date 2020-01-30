import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CSVRunner {


    public static void main(String[] args) {
        //Create a new CSVUtil Object
        CSVUtil csvUtil = new CSVUtil();

        Scanner scanner = new Scanner(System.in);

        //Get some basic information from the user.
        System.out.println("Enter File Name: ");

        //Store the fileName
        String fileName = scanner.nextLine();


        System.out.println("Enter File Type: ");

        //Store the fileType
        String fileType = scanner.nextLine();


        try {
            //Read in the CSV File
            List<Map<String, String>> response = csvUtil.readCSV("project/src/main/resources/" + fileName, fileType);


            // writeToFile(response);

            //Verify the Record
            csvUtil.verifyRecord(response);

            //Export the bad Records to a new CSV
            csvUtil.exportBadRecords(response, fileName);

            //Export the stats to a log file
            csvUtil.exportStats(response, fileName);

            //Import the data into the Database
            //csvUtil.importDB(fileName, fileType, fileName, response);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

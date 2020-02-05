import java.io.IOException;
import java.util.Scanner;

public class TestRunner {


    public static void main(String[] args){
        CSVUtil csvUtil = new CSVUtil();

        Scanner scanner = new Scanner(System.in);

        //Get some basic information from the user.
        System.out.println("Enter File Name: ");

        //Store the fileName
        String fileName = scanner.nextLine();


        System.out.println("Enter File Type: ");

        //Store the fileType
        String fileType = scanner.nextLine();

        //Test bad files
        try {
            csvUtil.exportBadRecords(csvUtil.readCSV("project/src/main/resources/" + fileName,fileType),fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

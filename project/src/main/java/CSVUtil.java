

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.sql.*;
import java.util.*;

public class CSVUtil {


    private static String fileSplitBy = ",";
    private static String fileContents = null;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Get some basic information from the user.
        System.out.println("Enter File Name: ");
        String fileName = scanner.nextLine();
        //Store the fileName
        System.out.println("Enter File Type: ");
        String fileType = scanner.nextLine();
        //Store the fileType


        List<Map<String, String>> response = new LinkedList<Map<String, String>>();
        //Read in the CSV and map it to POJO Objects.

        try {
           response = readCSV("project/src/main/resources/" + fileName, fileType);
         // writeToFile(response);
            importDB(fileName,fileType,"DATA",response);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public static List<Map<String, String>> readCSV(String fileName,String fileType) throws JsonProcessingException, IOException {
        String correctFile = fileName + fileType;
        File file = new File(correctFile);
        List<Map<String, String>> response = new LinkedList<Map<String, String>>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        MappingIterator<Map<String, String>> iterator = mapper.reader(Map.class)
                .with(schema)
                .readValues(file);
        while (iterator.hasNext() && iterator != null) {
            response.add(iterator.next());
        }
        return response;
    }


    public static void writeToFile(List<Map<String,String>> input){
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("test.json"));

            for(Map<String, String> map : input) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();

                    String value = entry.getValue();

                    bufferedWriter.write(entry.toString());

                }
            }

            //bufferedWriter.write();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void importDB(String fileName, String fileType, String tableName, List<Map<String,String>> fileContents){
        SQLUtil sqlUtil = new SQLUtil();
        String sql = "INSERT INTO " + tableName + "(A,B,C,D,E,F,G,H,I,J) values (?,?,?,?,?,?,?,?,?,?)";
        HashMap<Integer,String> updateValues = new HashMap<Integer, String>();


        for(Map<String, String> map : fileContents) {
            Set set = map.entrySet();

           for

            updateValues.put(1,map.get("A"));
            updateValues.put(2,map.get("B"));
            updateValues.put(3,map.get("C"));
            updateValues.put(4,map.get("D"));
            updateValues.put(5,map.get("E"));
            updateValues.put(6,map.get("F"));
            updateValues.put(7,map.get("G"));
            updateValues.put(8,map.get("H"));
            updateValues.put(9,map.get("I"));
            updateValues.put(10,map.get("J"));


           // sqlUtil.updateRecord(tableName,sql,updateValues);

        }





    }






}

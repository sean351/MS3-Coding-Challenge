

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CSVUtil {


    private static char fileSplitBy = ',';
    private static String fileContents = null;
    private static Map<String, String> badRecords = null;

    public List<Map<String, String>> readCSV(String fileName, String fileType) throws JsonProcessingException, IOException {
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





    public void importDB(String fileName, String fileType, String tableName, List<Map<String, String>> fileContents) {
        SQLUtil sqlUtil = new SQLUtil();
        String sql = "INSERT INTO " + tableName + "(A,B,C,D,E,F,G,H,I,J) values (?,?,?,?,?,?,?,?,?,?)";
        HashMap<Integer, String> updateValues = new HashMap<Integer, String>();
        createTable(tableName);
        for (Map<String, String> map : fileContents) {
            Set set = map.entrySet();


            updateValues.put(1, map.get("A"));
            updateValues.put(2, map.get("B"));
            updateValues.put(3, map.get("C"));
            updateValues.put(4, map.get("D"));
            updateValues.put(5, map.get("E"));
            updateValues.put(6, map.get("F"));
            updateValues.put(7, map.get("G"));
            updateValues.put(8, map.get("H"));
            updateValues.put(9, map.get("I"));
            updateValues.put(10, map.get("J"));


            sqlUtil.updateRecord(tableName, sql, updateValues);

        }


    }

    public void createTable(String tableName) {
        SQLUtil sqlUtil = new SQLUtil();
        String createSQL = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(A TEXT, " +
                "B TEXT," +
                "C TEXT," +
                "D TEXT," +
                "E TEXT," +
                "F TEXT, " +
                "G TEXT, " +
                "H BOOLEAN, " +
                "I BOOLEAN, " +
                "J TEXT)";
        sqlUtil.createTable(tableName, createSQL);


    }


    /**
     * Check records to make sure they contain all needed information
     * Return true if the record contains a blank entry
     */
    public Integer verifyRecord(List<Map<String, String>> fileContents) {
        Boolean blankEntry = false;
        Integer counter = 0;
        badRecords = new HashMap<String, String>();
        for (Map<String, String> map : fileContents) {
            blankEntry = map.containsValue("");

            Set headerSet = map.entrySet();

            if (blankEntry) {
                counter++;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    badRecords.put(key, value);
                }

            }
        }


        return counter;
    }


    /**
     * Output a CSV File containing the Bad Records
     */
    public void exportBadRecords(List<Map<String, String>> fileContents, String fileName) {

        try {




            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + "-bad.csv"));
            for (Map<String, String> map : fileContents) {
                if (verifyRecord(fileContents) > 1) {

                    Iterator it = badRecords.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();




                    }


                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Export a log file with the stats about the operation.
     */
    public void exportStats(List<Map<String, String>> fileContents, String fileName) {
        Integer totalRecords = fileContents.size();
        Integer correctRecords = 0;
        Integer badRecords = verifyRecord(fileContents);


        correctRecords = totalRecords - badRecords;
        for (Map<String, String> map : fileContents) {


            Set headerSet = map.entrySet();

        }

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("project/src/main/logs/" + fileName + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("Number of Total Records: " + totalRecords);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Number of Bad Records: " + badRecords);

        logger.info("Number of Correct Records: " + correctRecords);
    }


}

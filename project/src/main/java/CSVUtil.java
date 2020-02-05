

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
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
    private static Integer badRecordsCount = 0;
    private static HashMap<String, String> badRecords = null;


    @JsonPropertyOrder({"name", "age"})
    class Data {
        public String A;
        public String B;
        public String C;
        public String D;
        public String E;
        public String F;
        public String G;
        public String H;
        public String I;
        public String J;

        public Data() {
        }

        public String getA() {
            return A;
        }

        public void setA(String a) {
            A = a;
        }

        public String getB() {
            return B;
        }

        public void setB(String b) {
            B = b;
        }

        public String getC() {
            return C;
        }

        public void setC(String c) {
            C = c;
        }

        public String getD() {
            return D;
        }

        public void setD(String d) {
            D = d;
        }

        public String getE() {
            return E;
        }

        public void setE(String e) {
            E = e;
        }

        public String getF() {
            return F;
        }

        public void setF(String f) {
            F = f;
        }

        public String getG() {
            return G;
        }

        public void setG(String g) {
            G = g;
        }

        public String getH() {
            return H;
        }

        public void setH(String h) {
            H = h;
        }

        public String getI() {
            return I;
        }

        public void setI(String i) {
            I = i;
        }

        public String getJ() {
            return J;
        }

        public void setJ(String j) {
            J = j;
        }
    }

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
     * Output a CSV File containing the Bad Records
     */
    public void exportBadRecords(List<Map<String, String>> fileContents, String fileName) throws IOException {


        Map<String,String> outputMap = new HashMap<>();
        String correctFile = fileName + "-bad.csv";
        File file = new File(correctFile);




        for (Map<String, String> map : fileContents) {



            //Update Bad Records Count
            if(map.values().contains("")) {
                badRecordsCount++;
                outputMap = map;
            }








            int x = 0;
        }
        csvWriter(outputMap,new FileWriter(file));
    }


    /**
     * Helper method for writing CSV files.
     * @param m
     * @param writer
     * @throws IOException
     */
    public void csvWriter(Map<String, String> m, Writer writer) throws IOException {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> e : m.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();


            //builder.append(key);
            builder.append(',');
            builder.append(value);
          //  builder.append(System.getProperty("line.separator"));
            writer.write(builder.toString());

        }





        writer.close();
    }



    /**
     * Export a log file with the stats about the operation.
     */
    public void exportStats(List<Map<String, String>> fileContents, String fileName) {
        Integer totalRecords = fileContents.size();
        Integer correctRecords = 0;
        Integer badRecords = badRecordsCount;


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

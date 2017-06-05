package sample;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 * Created by ralfpopescu on 5/22/17.
 */
public class FileHandler {


    public FileHandler(){

    }

    public HashMap<String, ArrayList<EmployeePositionInfo>> handleEmployeePositionInfos() {
        ArrayList<EmployeePositionInfo> infos = new ArrayList<EmployeePositionInfo>();
        HashMap<String, ArrayList<EmployeePositionInfo>> hashInfos = new HashMap<String, ArrayList<EmployeePositionInfo>>();
        String xlsxFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/Timesheet3.xlsx";
        try {
            FileInputStream fis = new FileInputStream(new File(xlsxFileAddress));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = spreadsheet.iterator();
            XSSFRow row;


            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellnum = 0;
                String name = "";
                EmployeePositionInfo info = new EmployeePositionInfo();

                while (cellnum <= 9) {
                    Cell cell = row.getCell(cellnum, Row.RETURN_BLANK_AS_NULL);
                    if(cell == null){
                        cellnum++;
                        continue;
                    }
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            if(cellnum == 9){
                                String dollarAmount = new DataFormatter().formatCellValue(cell);
                                dollarAmount = dollarAmount.replace("$","");
                                float dollarNum = 0;
                                if(isParsable(dollarAmount)){
                                    dollarNum = Float.parseFloat(dollarAmount);
                                }
                                info.setHourlyRate(dollarNum);

                                ArrayList<EmployeePositionInfo> val = hashInfos.get(name);
                                if(val == null){
                                    ArrayList<EmployeePositionInfo> newInfo = new ArrayList<EmployeePositionInfo>();
                                    newInfo.add(info);
                                    hashInfos.put(name, newInfo);
                                } else {
                                    val.add(info);
                                    hashInfos.put(name, val);
                                }
                            }

                            break;
                        case Cell.CELL_TYPE_STRING:
                            if(cellnum == 1){
                                name = cell.getStringCellValue();
                            }
                            if(cellnum == 2){
                                name = cell.getStringCellValue() + ", " + name;
                            }
                            if (cellnum == 3){
                                info.setEmpPosition(cell.getStringCellValue());
                            }

                            break;
                    }
                    cellnum++;
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return hashInfos;
    }

    public HashMap<String, ArrayList<EmployeeHourInfo>> handleEmployeeHourInfos() {
        ArrayList<EmployeeHourInfo> infos = new ArrayList<EmployeeHourInfo>();
        HashMap<String, ArrayList<EmployeeHourInfo>> hashInfos = new HashMap<String, ArrayList<EmployeeHourInfo>>();
        String xlsxFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/Timesheet3.xlsx";
        try {
            FileInputStream fis = new FileInputStream(new File(xlsxFileAddress));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(1);

            Iterator<Row> rowIterator = spreadsheet.iterator();
            XSSFRow row;
            int rowNum = 0;
            EmployeeHourInfo info = new EmployeeHourInfo();
            String name = "";
            ArrayList<EmployeeHourInfo> individualInfos = new ArrayList<EmployeeHourInfo>();

            while (rowIterator.hasNext()) {

                if(rowNum < 2){
                    rowNum++;
                    row = (XSSFRow) rowIterator.next();
                    continue;
                }

                row = (XSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellnum = 0;
                info = new EmployeeHourInfo();


                while(cellnum <= 9){
                    Cell cell = row.getCell(cellnum, Row.RETURN_BLANK_AS_NULL);
                    if(cell == null){
                        cellnum++;
                        continue;
                    }
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            if(cellnum == 3){
                                info.setRegHours((float) cell.getNumericCellValue());
                            }
                            if(cellnum == 4){
                                info.setOTHours((float) cell.getNumericCellValue());
                            }
                            break;

                        case Cell.CELL_TYPE_STRING: //make sure to fix the first entry to infos
                            //System.out.println(cell.getStringCellValue());
                            if(cellnum == 1){
                                infos.add(info);
                                individualInfos = new ArrayList<EmployeeHourInfo>();

                                name = cell.getStringCellValue();
                                String[] split = name.split("\\s+");
                                if(split.length > 1) {
                                    name = split[1] + ", " + split[0];
                                } else {
                                    //name = split[0];
                                    name = split[0];
                                }
                            }
                            if(cellnum == 2){
                                info.setEmpPosition(cell.getStringCellValue());
                                info.setEmpName(name);
                            }

                            break;
                    }
                    cellnum++;

                }

                if(info.getEmpName() != null) {
                    individualInfos.add(info);
                    //System.out.println(individualInfos.size());
                    hashInfos.put(name, individualInfos);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return hashInfos;


    }





    public ArrayList<CC> handleCC(){
        String xlsxFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/CC3.xlsx";

        ArrayList<CC> CCs = new ArrayList<CC>();
        HashMap<String, CC> hashCCs = new HashMap<String, CC>();

        try {
            FileInputStream fis = new FileInputStream(new File(xlsxFileAddress));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = spreadsheet.iterator();
            XSSFRow row;

            while (rowIterator.hasNext())
            {
                row = (XSSFRow) rowIterator.next();

                if(!isCCRow(row)){ //skip row unless it is a valid row
                    continue;
                }

                Iterator<Cell> cellIterator = row.cellIterator();
                int cellnum = 0;
                CC cc = new CC();
                String name = "";

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            //System.out.println(cell.getNumericCellValue());
                            if (cellnum == 1){
                                cc.setEmpID((int)cell.getNumericCellValue());
                            }
                            if(cellnum == 2){
                                name = "Patio " + cellnum;
                            }
                            if(cellnum == 5){
                                cc.setTips((float) cell.getNumericCellValue());
                            }
                            if(cellnum == 6){
                                cc.setSales((float) cell.getNumericCellValue());
                            }

                            break;
                        case Cell.CELL_TYPE_STRING:
                            //System.out.println(cell.getStringCellValue());
                            if (cellnum == 2){
                                name = cell.getStringCellValue();
                            }
                            if(cellnum == 3){
                                name += "," + cell.getStringCellValue();
                                cc.setEmpName(name);
                            }
                            if(name.contains("Patio") || name.contains("patio") ||
                                    ((name.contains("Front")) || name.contains("front") &&
                                    (name.contains("Bar")) || name.contains("bar"))){
                                cc.setIsBar(true);
                            }

                            break;
                    }
                    cellnum++;
                }

                CCs.add(cc);
                hashCCs.put(name,cc);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return CCs;

    }

    public HashMap<String, CC> hashCC(){
        String xlsxFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/CC3.xlsx";

        ArrayList<CC> CCs = new ArrayList<CC>();
        HashMap<String, CC> hashCCs = new HashMap<String, CC>();

        try {
            FileInputStream fis = new FileInputStream(new File(xlsxFileAddress));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = spreadsheet.iterator();
            XSSFRow row;

            while (rowIterator.hasNext())
            {
                row = (XSSFRow) rowIterator.next();

                if(!isCCRow(row)){ //skip row unless it is a valid row
                    continue;
                }

                Iterator<Cell> cellIterator = row.cellIterator();
                int cellnum = 0;
                CC cc = new CC();
                String name = "";

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            //System.out.println(cell.getNumericCellValue());
                            if (cellnum == 1){
                                cc.setEmpID((int)cell.getNumericCellValue());
                            }
                            if(cellnum == 2){
                                name = "Patio " + cellnum;
                            }
                            if(cellnum == 5){
                                cc.setTips((float) cell.getNumericCellValue());
                            }
                            if(cellnum == 6){
                                cc.setSales((float) cell.getNumericCellValue());
                            }

                            break;
                        case Cell.CELL_TYPE_STRING:
                            //System.out.println(cell.getStringCellValue());
                            if (cellnum == 2){
                                name = cell.getStringCellValue();
                            }
                            if(cellnum == 3){
                                name += "," + cell.getStringCellValue();
                                cc.setEmpName(name);
                            }
                            if(name.contains("Patio") || name.contains("patio") ||
                                    ((name.contains("Front")) || name.contains("front") &&
                                            (name.contains("Bar")) || name.contains("bar"))){
                                cc.setIsBar(true);
                            }

                            break;
                    }
                    cellnum++;
                }

                CCs.add(cc);
                hashCCs.put(name,cc);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashCCs;

    }

    public boolean isCCRow(XSSFRow row){

        if(row.getLastCellNum() > 5) {
            return true;
        } else {
            return false;
        }

//        boolean CC = false;
//        Iterator<Cell> cellIterator = row.cellIterator();
//
//        while (cellIterator.hasNext())
//        {
//            Cell cell = cellIterator.next();
//            switch (cell.getCellType())
//            {
//                case Cell.CELL_TYPE_NUMERIC:
//
//                    break;
//                case Cell.CELL_TYPE_STRING:
//
//                    break;
//            }
//        }
//
//        return true;

    }

    public HashMap<String, Integer> getEmpKeys(){
        HashMap<String, Integer> empKeys = new HashMap<String, Integer>();
        String textFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/EmployeeKeys3.txt";
        try {
            FileInputStream fstream = new FileInputStream("/Users/ralfpopescu/PTPayroll/src/sample/EmployeeKeys3.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\s+");
                String name = split[1] + " " + split[2];
                int num = Integer.parseInt(split[0]);
                empKeys.put(name, num);

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return empKeys;

    }

    public void csvToXLSX() {
        try {
            String csvFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/CC3.csv"; //csv file address
            String xlsxFileAddress = "CC3.xlsx"; //xlsx file address
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine=null;
            int RowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    String s = str[i];
                    s = s.replace("\"", "");
                    if(isParsable(s)){
                        currentRow.createCell(i).setCellValue(Float.parseFloat(s));
                    } else {
                        if(!s.equals(" ")) {
                            currentRow.createCell(i).setCellValue(s);
                        }
                    }
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "Exception in try");
        }
    }

    public void alphabetizeEmployees(){
        try {
            FileInputStream fstream = new FileInputStream("/Users/ralfpopescu/PTPayroll/src/sample/EmployeeKeys3.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            ArrayList<String> names = new ArrayList<String>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\s");
                String name = split[1] + split[2] + split[0];
                names.add(name);
            }
            Collections.sort(names);
            try{
                PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
                for(String name: names){
                    writer.println(name);
                }
            } catch (IOException e) {
                // do something
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }


    public static boolean isParsable(String input){
        boolean parsable = true;
        try{
            Float.parseFloat(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }
}

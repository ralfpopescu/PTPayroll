package sample;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
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

    public ArrayList<EmployeePositionInfo> handleEmployeePositionInfos(){
        ArrayList<EmployeePositionInfo> info = new ArrayList<EmployeePositionInfo>();

        return info;
    }

    public ArrayList<CC> handleCC(){
        String xlsxFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/CC.xlsx";

        ArrayList<CC> CCs = new ArrayList<CC>();

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
                            if (cellnum == 1){
                                cc.setEmpID((int)cell.getNumericCellValue());
                            }
                            if(cellnum == 2){
                                name = "Patio " + cellnum;
                            }
                            if(cellnum == 5){
                                cc.setTips((float)cell.getNumericCellValue());
                            }
                            if(cellnum == 6){
                                cc.setSales((float) cell.getNumericCellValue());
                            }

                            break;
                        case Cell.CELL_TYPE_STRING:
                            if (cellnum == 2){
                                name = cell.getStringCellValue();
                            }
                            if(cellnum == 3){
                                name += ", " + cell.getStringCellValue();
                            }

                            break;
                    }
                    cellnum++;
                }

                CCs.add(cc);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return CCs;

    }

    public boolean isCCRow(XSSFRow row){

        if(row.getLastCellNum() > 6) {
            return true;
        }

        boolean CC = false;
        Iterator<Cell> cellIterator = row.cellIterator();

        while (cellIterator.hasNext())
        {
            Cell cell = cellIterator.next();
            switch (cell.getCellType())
            {
                case Cell.CELL_TYPE_NUMERIC:

                    break;
                case Cell.CELL_TYPE_STRING:

                    break;
            }
        }

        return true;

    }

    public HashMap<String, Integer> getEmpKeys(){
        HashMap<String, Integer> empKeys = new HashMap<String, Integer>();
        String textFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/EmployeeKeys";
        try {
            FileInputStream fstream = new FileInputStream("/Users/ralfpopescu/PTPayroll/src/sample/EmployeeKeys.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\s+");
                String name = split[1] + split[2];
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
            String csvFileAddress = "/Users/ralfpopescu/PTPayroll/src/sample/CC.csv"; //csv file address
            String xlsxFileAddress = "CC.xlsx"; //xlsx file address
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
                    currentRow.createCell(i).setCellValue(s);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
    }
}

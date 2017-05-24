package sample;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

/**
 * Created by ralfpopescu on 5/23/17.
 */


public class SheetCreator {

    FileHandler fileHandler;

    public SheetCreator(){
        fileHandler = new FileHandler();
    }

    public void makeSheet(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Payroll");

        int rowNum = 0;

        XSSFRow first = spreadsheet.createRow(rowNum);
        first.createCell(0).setCellValue("Legal");
        first.createCell(1).setCellValue("PayGroup");
        first.createCell(2).setCellValue("Division");
        first.createCell(3).setCellValue("Key");
        first.createCell(4).setCellValue("Name");
        first.createCell(5).setCellValue("LaborValue1");
        first.createCell(6).setCellValue("E_Regular_Hours");
        first.createCell(7).setCellValue("E_Regular_ORRate");
        first.createCell(8).setCellValue("E_Average_OT_Hours");
        first.createCell(9).setCellValue("E_Salary_Dollars");
        first.createCell(10).setCellValue("E_Commission_Dollars");
        first.createCell(11).setCellValue("E_Sub Minimum Reg_Hours");
        first.createCell(12).setCellValue("E_Waitstaff Overt_Hours");
        first.createCell(13).setCellValue("E_Charge Tips_Dollars");
        first.createCell(14).setCellValue("E_Tipped Sales_Dollars");

        rowNum++;

        HashMap<String, EmployeeHourInfo> hourInfos = fileHandler.handleEmployeeHourInfos();
        ArrayList<EmployeePositionInfo> positionInfos = fileHandler.handleEmployeePositionInfos();
        ArrayList<CC> CCs = fileHandler.handleCC();
        HashMap<String, Integer> empKeys = fileHandler.getEmpKeys();
        ArrayList<String> names = new ArrayList<String>();
        names.addAll(empKeys.keySet());


        for(int i = 0; i < empKeys.keySet().size(); i++){
            String name = names.get(i);
            int empKey = empKeys.get(name);

            hourInfos.get(name);



            for (int k = 0;  k< hourInfos.size(); k++){

            }
            for (int l = 0; l < hourInfos.size(); l++){

            }


        }



    }
}

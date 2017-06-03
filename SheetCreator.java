package sample;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        String sheetTitle = "Payroll";

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

        HashMap<String, ArrayList<EmployeeHourInfo>> hourInfos = fileHandler.handleEmployeeHourInfos();
        ArrayList<EmployeePositionInfo> positionInfos = fileHandler.handleEmployeePositionInfos();
        ArrayList<CC> CCs = fileHandler.handleCC();
        HashMap<String, Integer> empKeys = fileHandler.getEmpKeys();
        ArrayList<String> names = new ArrayList<String>();
        names.addAll(empKeys.keySet());


        for(int i = 0; i < empKeys.keySet().size(); i++){
            String name = names.get(i);
            int empKey = empKeys.get(name);

            XSSFRow empRow = spreadsheet.createRow(rowNum);
            float ccTips = 0;
            float ccSales = 0;

            ArrayList<EmployeeHourInfo> empHourList = hourInfos.get(name);
            if(empHourList != null) {
                for (int j = 0; j < empHourList.size(); j++){
                    if(j == 0) {
                        empRow.createCell(0).setCellValue("GA2295");
                        empRow.createCell(1).setCellValue("Bi-Weekly");
                        empRow.createCell(4).setCellValue(name);
                        empRow.createCell(6).setCellValue(empHourList.get(0).getRegHours());
                        empRow.createCell(7).setCellValue(empHourList.get(0).getOTHours());
                    } else {
                        rowNum++;
                        empRow = spreadsheet.createRow(rowNum);
                        empRow.createCell(6).setCellValue(empHourList.get(j).getRegHours());
                        empRow.createCell(7).setCellValue(empHourList.get(j).getOTHours());
                    }

                }
            } else {
                empRow.createCell(0).setCellValue("Missing info or typo for " + name);
            }



            for (int k = 0;  k < CCs.size(); k++){
                CC cc = CCs.get(k);
                String ccName = cc.getEmpName();

                //System.out.println(ccName);

                if(ccName.equals(name)){
                    ccTips = cc.getTips();
                    ccSales = cc.getSales();
                    break;
                }
            }

            empRow.createCell(13).setCellValue(ccTips);
            empRow.createCell(14).setCellValue(ccSales);

            rowNum++;

            for (int l = 0; l < hourInfos.size(); l++){

            }


        }

        for (int i=0; i< spreadsheet.getPhysicalNumberOfRows(); i++){
            spreadsheet.autoSizeColumn(i);
        }

        try {
            FileOutputStream out = new FileOutputStream(
                    new File(sheetTitle + ".xlsx"));
            try {
                workbook.write(out);
                out.close();
                System.out.println(
                        "Writesheet.xlsx written successfully");
            } catch (IOException e){
                System.out.println("ay");
            }
        } catch (FileNotFoundException e){
            System.out.println(
                    "shit");

        }



    }
}

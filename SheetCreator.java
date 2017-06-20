package sample;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;

/**
 * Created by ralfpopescu on 5/23/17.
 */


public class SheetCreator {

    FileHandler fileHandler;
    State state;

    public SheetCreator(State s){
        fileHandler = new FileHandler();
        state = s;
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
        first.createCell(8).setCellValue("E_Average OT_Hours");
        first.createCell(9).setCellValue("E_Salary_Dollars");
        first.createCell(10).setCellValue("E_Commission_Dollars");
        first.createCell(11).setCellValue("E_Sub Minimum Reg_Hours");
        first.createCell(12).setCellValue("E_Waitstaff Overt_Hours");
        first.createCell(13).setCellValue("E_Charge Tips_Dollars");
        first.createCell(14).setCellValue("E_Tipped Sales_Dollars");


        HashMap<String,String> divisionCoder = new HashMap<String,String>();

        divisionCoder.put("Manager", "02");
        divisionCoder.put("MOD", "02");
        divisionCoder.put("Event Sales", "03");
        divisionCoder.put("Kitchen", "06");
        divisionCoder.put("Host", "07");
        divisionCoder.put("Server", "08");
        divisionCoder.put("Training Server", "08");
        divisionCoder.put("Food Runner","09");
        divisionCoder.put("Busser","09");
        divisionCoder.put("Barback","09");
        divisionCoder.put("Inventory","09");
        divisionCoder.put("Dishwasher","10");
        divisionCoder.put("Manager Salary","11");
        divisionCoder.put("Bartender","12");
        divisionCoder.put("Service","12");
        divisionCoder.put("Maintenance","13");
        divisionCoder.put("Banquet Server","18");
        divisionCoder.put("Event Server","18");
        divisionCoder.put("Banquet Bartender","20");
        divisionCoder.put("Banquet Dishwasher","21");
        divisionCoder.put("Sushi","24");
        divisionCoder.put("Banquet Cook","25");
        divisionCoder.put("Ice Rink","26");
        divisionCoder.put("Basecamp","27");
        divisionCoder.put("Parking","28");

        rowNum++;

        HashMap<String, ArrayList<EmployeeHourInfo>> hourInfos = fileHandler.handleEmployeeHourInfos(state.getWhenIWorkFile());
        HashMap<String, ArrayList<EmployeePositionInfo>> positionInfos = fileHandler.handleEmployeePositionInfos(state.getWhenIWorkFile());
        ArrayList<CC> CCs = fileHandler.handleCC();
        HashMap<String, CC> CCHashmap = fileHandler.hashCC(state.getAlohaFile());
        HashMap<String, Integer> empKeys = fileHandler.getEmpKeys();
        ArrayList<String> names = new ArrayList<String>();
        HashSet<String> bartenders = getBartenders(positionInfos);
        names.addAll(empKeys.keySet());
        Collections.sort(names);


        float barTotal = 0;
        for(String key: CCHashmap.keySet()){
            CC cc = CCHashmap.get(key);

            if(cc.isBar()){
                barTotal += cc.getTips();
                System.out.println(cc.getEmpName() + " " + cc.getTips());
            }
        }

        HashMap<String, Float> barTipsByName = calculateBarTipShare(barTotal, bartenders, hourInfos);


        for(int i = 0; i < empKeys.keySet().size(); i++){
            String name = names.get(i);
            int empKey = empKeys.get(name);

            XSSFRow empRow = spreadsheet.createRow(rowNum);
            float ccTips = 0;
            float ccSales = 0;

            ArrayList<EmployeeHourInfo> empHourList = hourInfos.get(name);
            ArrayList<EmployeePositionInfo> posInfos = positionInfos.get(name);

            if(empHourList != null) {
                empHourList = sortEmpHourList(empHourList);
                for (int j = 0; j < empHourList.size(); j++){
                    if(j == 0) {
                        EmployeeHourInfo ehi = empHourList.get(0);
                        String position = ehi.getEmpPosition();
                        EmployeePositionInfo epi = new EmployeePositionInfo();

                        if(posInfos != null){
                            for(EmployeePositionInfo x: posInfos){
                                if(x.getEmpPosition() != null) {
                                    if (x.getEmpPosition().equals(position)) {
                                        epi = x;
                                    }
                                }
                            }
                        }

                        empRow.createCell(0).setCellValue("GA2295");
                        empRow.createCell(1).setCellValue("Bi-Weekly");
                        String divisionCode = divisionCoder.get(position);
                        if(divisionCode != null) {
                            empRow.createCell(2).setCellValue(divisionCode);
                        } else {
                            empRow.createCell(2).setCellValue("Potential typo/missing info: " + position);
                        }
                        empRow.createCell(3).setCellValue(empKey);
                        empRow.createCell(4).setCellValue(name);
                        if(isSubMin(position)){
                            empRow.createCell(11).setCellValue(Math.round(empHourList.get(0).getRegHours()*100.0)/100.0);
                            empRow.createCell(12).setCellValue(Math.round(empHourList.get(0).getOTHours()*100.0)/100.0);
                        } else {
                            empRow.createCell(6).setCellValue(Math.round(empHourList.get(0).getRegHours()*100.0)/100.0);
                            empRow.createCell(8).setCellValue(Math.round(empHourList.get(0).getOTHours()*100.0)/100.0);
                        }

                        empRow.createCell(7).setCellValue(Math.floor(epi.getHourlyRate() * 100) / 100);

                        if(CCHashmap.get(name) != null) {
                            ccTips = CCHashmap.get(name).getTips();
                            ccSales = CCHashmap.get(name).getSales();
                        } else {
                            ccTips = 0;
                            ccSales = 0;
                        }

                        if(position.equals("Bartender")){
                            ccTips = barTipsByName.get(name);
                            ccSales = 0;
                        }

                        empRow.createCell(13).setCellValue(Math.round(ccTips*100.0)/100.0);
                        empRow.createCell(14).setCellValue(Math.round(ccSales*100.0)/100.0);

                    } else { //if employee has multiple positions
                        rowNum++;
                        XSSFRow empRow2 = spreadsheet.createRow(rowNum);

                        EmployeeHourInfo ehi = empHourList.get(j);
                        String position = ehi.getEmpPosition();
                        EmployeePositionInfo epi = new EmployeePositionInfo();

                        if(posInfos != null){
                            for(EmployeePositionInfo x: posInfos){
                                if(x.getEmpPosition().equals(position)){
                                    epi = x;
                                }
                            }
                        }

                        if(CCHashmap.get(name) != null) {
                            ccTips = CCHashmap.get(name).getTips();
                            ccSales = CCHashmap.get(name).getSales();
                        } else {
                            ccTips = 0;
                            ccSales = 0;
                        }

                        if(position.equals("Bartender")){
                            ccTips = barTipsByName.get(name);
                            ccSales = 0;
                        }

                        empRow2.createCell(0).setCellValue("GA2295");
                        empRow2.createCell(1).setCellValue("Bi-Weekly");
                        empRow2.createCell(3).setCellValue(empKey);
                        String divisionCode = divisionCoder.get(position);

                        if(divisionCode != null) {
                            empRow2.createCell(5).setCellValue(divisionCode);
                        } else {
                            empRow2.createCell(5).setCellValue("Potential typo/missing info: " + position);
                        }

                        if(isSubMin(position)){
                            empRow2.createCell(11).setCellValue(Math.round(ehi.getRegHours()*100.0)/100.0);
                            empRow2.createCell(12).setCellValue(Math.round(ehi.getOTHours()*100.0)/100.0);
                        } else {
                            empRow2.createCell(6).setCellValue(Math.round(ehi.getRegHours()*100.0)/100.0);
                            empRow2.createCell(8).setCellValue(Math.round(ehi.getOTHours()*100.0)/100.0);
                        }

                        empRow2.createCell(7).setCellValue(Math.floor(epi.getHourlyRate() * 100) / 100);
                        empRow2.createCell(13).setCellValue(Math.round(ccTips*100.0)/100.0);
                        empRow2.createCell(14).setCellValue(Math.round(ccSales*100.0)/100.0);
                    }

                }
            } else {
                //System.out.println(":" + name);
                empRow.createCell(0).setCellValue("Missing info or typo for " + name);
            }


//            empRow.createCell(13).setCellValue(ccTips);
//            empRow.createCell(14).setCellValue(ccSales);

            rowNum++;


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

    private boolean isSubMin(String position){
        return position.equals("Server") || position.equals("Bartender");
    }

    private HashSet<String> getBartenders(HashMap<String, ArrayList<EmployeePositionInfo>> posInfo){
        HashSet<String> bartenders = new HashSet<String>();
        for (String name : posInfo.keySet()) {
            ArrayList<EmployeePositionInfo> infos = posInfo.get(name);
            for(EmployeePositionInfo info: infos) {
                if(info.getEmpPosition() != null) {
                    if (info.getEmpPosition().equals("Bartender")) {
                        bartenders.add(name);
                    }
                }
            }
        }
        return bartenders;
    }

    private HashMap<String, Float> calculateBarTipShare(float total, HashSet<String> bartenders,
                                       HashMap<String, ArrayList<EmployeeHourInfo>> hourInfos){

        float totalTime = 0;
        HashMap<String, Float> barTipsByName = new HashMap<String, Float>();
        HashMap<String, Float> bartendingHoursByName = new HashMap<String, Float>();

        for(String bt: bartenders){
            ArrayList<EmployeeHourInfo> hi = hourInfos.get(bt);

            for(EmployeeHourInfo ehi: hi){
                if(ehi.getEmpPosition().equals("Bartender")){
                    bartendingHoursByName.put(ehi.getEmpName(),(ehi.getRegHours() + ehi.getOTHours()));
                    totalTime += ehi.getRegHours() + ehi.getOTHours();
                }
            }
        }
        for(String bt: bartenders){
            float ratio = bartendingHoursByName.get(bt)/totalTime;
            barTipsByName.put(bt, total*ratio);

        }
        return barTipsByName;
    }

    private ArrayList<EmployeeHourInfo> sortEmpHourList(ArrayList<EmployeeHourInfo> list){
        ArrayList<EmployeeHourInfo> newList = new ArrayList<EmployeeHourInfo>();
        int size = list.size();
        EmployeeHourInfo most = list.get(0);
        float mostfloat = -1;
        for(int i = 0; i < size; i++){
            for(EmployeeHourInfo ehi : list){
                if(ehi.getRegHours() > mostfloat){
                    most = ehi;
                    mostfloat = ehi.getRegHours();
                }
            }
            list.remove(most);
            mostfloat = -1;
            newList.add(most);
        }
        return newList;
    }
}

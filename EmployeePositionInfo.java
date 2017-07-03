package sample;

/**
 * Created by ralfpopescu on 5/23/17.
 */
public class EmployeePositionInfo {

    String name;
    String position;
    String location;
    float hourlyRate = 0;

    public EmployeePositionInfo(){

    }

    public void setEmpName(String x){
        name = x;

    }
    public void setEmpPosition(String x){
        position = x;
    }
    public void setHourlyRate(float x){
        hourlyRate = x;
    }
    public String getEmpName(){
        return name;
    }
    public String getEmpPosition(){
        return position;
    }
    public float getHourlyRate(){
        return hourlyRate;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String x){
        location = x;
    }
}

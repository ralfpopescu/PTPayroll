package sample;

/**
 * Created by ralfpopescu on 5/23/17.
 */
public class EmployeeHourInfo {

    String name;
    String position;
    float regHours;
    float OTHours;

    public void setEmpName(String x){
        name = x;
    }

    public void setEmpPosition(String x){
        position = x;
    }

    public void setRegHours(float x){
        regHours = x;
    }

    public void setOTHours(float x){
        OTHours = x;
    }

    public String getEmpName(){
        return name;
    }

    public String getEmpPosition(){
        return position;
    }

    public float getRegHours(float x){
        return regHours;
    }

    public float getOTHours(float x){
        return OTHours;
    }

}

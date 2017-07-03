package sample;

/**
 * Created by ralfpopescu on 5/23/17.
 */
public class CC {

    String name = "";
    float sales;
    float tips;
    int empID;
    boolean bar = false;

    public CC (){

    }

    public CC (String n, float s, float t, int e){
        name = n;
        sales = s;
        tips = t;
        empID = e;
    }

    public String getEmpName(){
        return name;
    }

    public float getSales(){
        return sales;
    }

    public float getTips(){
        return tips;
    }

    public int getEmpID(){
        return empID;
    }

    public boolean isBar(){
        return bar;
    }

    public void setEmpName(String x){
        name = x;
    }

    public void setSales(float x){
        sales = x;
    }

    public void setTips(float x){
        tips = x;
    }

    public void setEmpID(int x){
        empID = x;
    }

    public void setIsBar(boolean x){
        System.out.println(x);
        bar = x;
    }

}

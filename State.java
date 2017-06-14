package sample;

import java.io.File;
import java.util.HashMap;

/**
 * Created by ralfpopescu on 6/8/17.
 */
public class State {

    File AlohaFile;
    File WhenIWorkFile;
    SheetCreator sheetCreator;
    HashMap<String, Integer> empKeys;

    public State(){
        sheetCreator = new SheetCreator(this);
    }

    public void setAlohaFile(File f){
        AlohaFile = f;
    }
    public void setWhenIWorkFile(File f){
        WhenIWorkFile = f;
    }
    public void setEmpKeys(HashMap<String, Integer> ek){
        empKeys = ek;
    }
    public File getAlohaFile(){
        return AlohaFile;
    }
    public File getWhenIWorkFile(){
        return WhenIWorkFile;
    }

    public SheetCreator getSheetCreator(){
        return sheetCreator;
    }

}

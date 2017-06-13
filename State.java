package sample;

import java.io.File;

/**
 * Created by ralfpopescu on 6/8/17.
 */
public class State {

    File AlohaFile;
    File WhenIWorkFile;
    SheetCreator sheetCreator;

    public State(){
        sheetCreator = new SheetCreator(this);
    }

    public void setAlohaFile(File f){
        AlohaFile = f;
    }
    public void setWhenIWorkFile(File f){
        WhenIWorkFile = f;
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

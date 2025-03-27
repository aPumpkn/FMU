package net.pumpkin.fmu.io;

import java.util.Map;

import net.pumpkin.fmu.io.editor.DataEditor;
import net.pumpkin.fmu.io.editor.FmuEditor;
import net.pumpkin.fmu.io.reader.FileReader;
import net.pumpkin.fmu.io.reader.FmuReader;

/*
 * Determines reader and editor formats depending
 * on the file type.
 */
final public class Formatter {
    
    /*
     * Decides what reader should be used.
     */
    public static DataEditor resolveEditor(AppFile file) {
        
        FileReader reader;
        FileType type = file.getType();
        
        switch (type) {
            
            case FMU:
                reader = new FmuReader();
                break;
                
            default:
                reader = null;
            
        } return reader.access(file.getPath());
        
    }
    
    public static void resolveStorage(String path, Map<String,String> storage) {
        
        if (path.endsWith(".fmu")) FmuEditor.store(path, storage);
        
    }
    
}

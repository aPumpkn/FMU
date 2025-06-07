package net.pumpkin.fmu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.pumpkin.fmu.io.AppFile;
import net.pumpkin.fmu.io.editor.MemoryEditor;
import net.pumpkin.fmu.utils.FileOps;

/*
 * Responsible for reading the full contents of a file.
 */
@FunctionalInterface
public interface TextFileReader {
    
    /*
     * Accesses the file and returns a new MemoryEditor with all
     * the retrievable data. 
     */
    default MemoryEditor extract(AppFile file) {
        
        final Map<String,String> map = new LinkedHashMap<>();
        
        FileOps.readTextFile(file.getPath(), reader -> {
            
            map.putAll(read(reader));
            
        });
        
        return new MemoryEditor(file, map);
        
    }
    
    /*
     * Let the Reader implementing this interface determine how 
     * the file should be read.
     */
    Map<String,String> read(BufferedReader reader) throws IOException;
    
}

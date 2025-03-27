package net.pumpkin.fmu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import net.pumpkin.fmu.io.editor.DataContainer;

/*
 * Responsible for reading the full contents of a file.
 */
public interface FileReader {
    
    /*
     * Accesses the file and returns a new DataContainer with all
     * the retrievable data. 
     */
    default DataContainer access(String path) {
        
        Map<String,String> map = null;
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            map = read(reader);
            
        } catch (IOException e) { e.printStackTrace(); }
        
        return new DataContainer(path, map);
        
    }
    
    /*
     * Let the Format class implementing this interface determine how 
     * the file should be read.
     */
    Map<String,String> read(BufferedReader reader) throws IOException;
    
}

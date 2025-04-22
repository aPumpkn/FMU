package net.pumpkin.fmu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import net.pumpkin.fmu.io.AppFile;
import net.pumpkin.fmu.io.editor.internal.MemoryEditor;

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
        
        Map<String,String> map = null;
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()))) { 
            
            map = read(reader);
            
        } catch (IOException e) { e.printStackTrace(); }
        
        return new MemoryEditor(file, map);
        
    }
    
    /*
     * Let the Reader implementing this interface determine how 
     * the file should be read.
     */
    Map<String,String> read(BufferedReader reader) throws IOException;
    
}

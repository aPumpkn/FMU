package net.pumpkin.fmu.io.memory.writer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

public interface TextFileWriter {
    
    default void store(String path, Map<String,String> bag) {
        
        try (PrintWriter writer = new PrintWriter(path)) {
            
            write(writer, bag);
            
        } catch (FileNotFoundException e) { e.printStackTrace(); }
        
    }
    
    void write(PrintWriter writer, Map<String,String> bag);
    
}

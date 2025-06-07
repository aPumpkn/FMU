package net.pumpkin.fmu.io.writer;
import java.io.PrintWriter;
import java.util.Map;

import net.pumpkin.fmu.utils.FileOps;

public interface TextFileWriter {
    
    default void store(String path, Map<String,String> bag) {
        
        FileOps.writeTextFile(path, writer -> { 
            
            write(writer, bag); 
            
        });
        
    }
    
    void write(PrintWriter writer, Map<String,String> bag);
    
}

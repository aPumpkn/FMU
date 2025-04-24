package net.pumpkin.fmu.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.pumpkin.fmu.io.reader.FunctionalBufferedReader;
import net.pumpkin.fmu.io.writer.FunctionalPrintWriter;

final public class FileOps {
    
        // -- READERS -- \\
    
    public static void readTextFile(String path, FunctionalBufferedReader readerI) {
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            readerI.read(reader);
            
        } catch (IOException e) { e.printStackTrace(); }
          catch (SecurityException e) { e.printStackTrace(); }
        
    }
    
    
    
        // -- WRITERS -- \\ 
    
    public static void writeTextFile(String path, FunctionalPrintWriter writerI) {
        
        try (PrintWriter writer = new PrintWriter(path)) {
            
            writerI.write(writer);
            
        } catch (FileNotFoundException e) { e.printStackTrace(); }
          catch (SecurityException e) { e.printStackTrace(); }
        
    }
    
}

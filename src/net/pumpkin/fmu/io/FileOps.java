package net.pumpkin.fmu.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

final public class FileOps {
    
        // -- FUNC. INTERFACES -- \\

    @FunctionalInterface
    public interface FunctionalBufferedReader { void read(BufferedReader reader) throws IOException, SecurityException; }

    @FunctionalInterface
    public interface FunctionalBufferedLineReader { void read(BufferedReader reader, String line) throws IOException, SecurityException; }

    @FunctionalInterface
    public interface FunctionalPrintWriter { void write(PrintWriter writer); }
    
    
        // -- READERS -- \\
    
    public static void readTextFile(String path, FunctionalBufferedLineReader readerI) {
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            String line;
            
            while ((line = reader.readLine()) != null)
                readerI.read(reader, line);
            
        } catch (IOException e) { e.printStackTrace(); }
          catch (SecurityException e) { e.printStackTrace(); }
        
    }

    public static void readTextFile(String path, FunctionalBufferedReader readerI) {
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            String line;
            
            while ((line = reader.readLine()) != null)
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

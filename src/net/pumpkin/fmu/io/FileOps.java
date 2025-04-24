package net.pumpkin.fmu.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

final public class FileOps {

    private FileOps() {}
    
    public static class FileOpsTask {
        
        private String result;
        private boolean isCancelled = false;
        
        public void result(String result) { this.result = result; }
        public String result() { return result; }
        public void cancel() { isCancelled = true; }
        public boolean isCancelled() { return isCancelled; }
        
    }
    
        // -- FUNC. INTERFACES -- \\

    @FunctionalInterface
    public interface FunctionalBufferedReader { void read(BufferedReader reader) throws IOException, SecurityException; }

    @FunctionalInterface
    public interface FunctionalBufferedLineReader { void read(FileOpsTask task, BufferedReader reader, String line, String search) throws IOException, SecurityException; }

    @FunctionalInterface
    public interface FunctionalPrintWriter { void write(PrintWriter writer); }
    
        // -- READERS -- \\

    public static void readTextFile(String path, FunctionalBufferedReader readerI) {
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            readerI.read(reader);
            
        } catch (IOException e) { e.printStackTrace(); }
          catch (SecurityException e) { e.printStackTrace(); }
        
    }
    
    public static String readTextFile(String path, FunctionalBufferedLineReader readerI) {
        
        FileOpsTask task = new FileOpsTask();
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            String line;
            String search = "";
            
            while (!task.isCancelled() && (line = reader.readLine()) != null)
                readerI.read(task, reader, line, search);
            
        } catch (IOException e) { e.printStackTrace(); }
          catch (SecurityException e) { e.printStackTrace(); }
          return task.result();
        
    }
    
    
    
        // -- WRITERS -- \\ 
    
    public static void writeTextFile(String path, FunctionalPrintWriter writerI) {
        
        try (PrintWriter writer = new PrintWriter(path)) {
            
            writerI.write(writer);
            
        } catch (FileNotFoundException e) { e.printStackTrace(); }
          catch (SecurityException e) { e.printStackTrace(); }
        
    }
    
}

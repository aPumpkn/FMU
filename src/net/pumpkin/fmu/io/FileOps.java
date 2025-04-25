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
        private String search = "";
        private boolean isCancelled = false;
        
        public void search(String search) { this.search = search; }
        public void result(String result) { this.result = result; }
        public void stop() { isCancelled = true; }
        public String search() { return search; }
        public String result() { return result; }
        public boolean isCancelled() { return isCancelled; }
        
    }
    
        // -- FUNC. INTERFACES -- \\

    @FunctionalInterface
    public interface FunctionalBufferedReader { void read(BufferedReader reader) throws IOException, SecurityException; }

    @FunctionalInterface
    public interface FunctionalBufferedReaderLoop { void read(FileOpsTask task, BufferedReader reader, String line) throws IOException, SecurityException; }

    @FunctionalInterface
    public interface FunctionalPrintWriter { void write(PrintWriter writer); }
    
        // -- READERS -- \\

    public static void readTextFile(String path, FunctionalBufferedReader readerI) {
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            readerI.read(reader);
            
        } catch (IOException e) { e.printStackTrace(); }
          catch (SecurityException e) { e.printStackTrace(); }
        
    }
    
    public static String readTextFile(String path, FunctionalBufferedReaderLoop readerI) {
        
        FileOpsTask task = new FileOpsTask();
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            
            String line;
            
            while (!task.isCancelled() && (line = reader.readLine()) != null)
                readerI.read(task, reader, line);
            
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

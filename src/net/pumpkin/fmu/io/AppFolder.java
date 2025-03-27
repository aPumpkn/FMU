package net.pumpkin.fmu.io;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import net.pumpkin.fmu.utils.StringUtils;

final public class AppFolder {
    
    public static boolean exists(String path) {
        
        return Files.isDirectory(Paths.get(StringUtils.stripPath(path)));
        
    }
    
    public static void create(String path) {

        try { Files.createDirectory(Paths.get(StringUtils.stripPath(path))); }
        catch (FileAlreadyExistsException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        
    }
    
    public static void delete(String path) {
        
        Path filePath = Paths.get(StringUtils.stripPath(path));
        
        try {
            
            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) throws IOException {

                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                    
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                    
                    if (e == null) {
                        
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                        
                    } else throw e;
                    
                }
                
            });
            
        } catch (IOException e) { e.printStackTrace(); }
        
    }
    
}

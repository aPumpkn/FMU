package net.pumpkin.fmu.core.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.pumpkin.fmu.exceptions.FileFoundException;

public class AppFile {

    private String path;
    private String name;
    private File file;
    private FileType type;
    
    
    
        // -- INSTANCES -- \\
    
    private AppFile(String path, String name, File file, FileType type) {
        
        this.path = path;
        this.name = name;
        this.file = file;
        this.type = type;
        
    }
    
    /* 
     * Attempts to construct a new file. Including an 
     * extension at the end of the passed string will 
     * determine the FileType, else it will be "FileType.BLANK".
     * 
     * Params: 
     * path - Relative directory of the file
     * 
     * Returns:
     * AppFile
     */
    public AppFile(String path) {
        
        path = path.replace("\\", "/");
        this.path = path;
        this.file = new File(path);
        
        try { if (!file.createNewFile()) throw new FileFoundException(); } 
        catch (FileFoundException | IOException e) { 
            
            e.printStackTrace();
            
            this.path = null;
            this.file = null;
            
        } 
        
        if (path.endsWith(".fmu")) this.type = FileType.FMU;
        else if (!path.contains(".")) this.type = FileType.BLANK;
        else {
            
            String[] pathArr = path.split("/");
            this.name = pathArr[pathArr.length - 1];
            this.type = name.contains(".") && !name.endsWith(".") ? FileType.UNKNOWN : FileType.BLANK;
            
        }
        
    }
    
    /*
     * Attempts to retrieve a file using the relative path.
     * 
     * Params:
     * path - Relative directory of the file
     * 
     * Throws:
     * FileNotFoundException if the file doesn't exist
     * 
     * Returns: 
     * AppFile if the file exists
     * null if the file does not exist
     */
    public static AppFile get(String path) {

        path = path.replace("\\", "/");
        File file = new File(path);
        FileType type = FileType.UNKNOWN;
        String name = "";
        
        try { if (!file.exists()) throw new FileNotFoundException(); } 
        catch (FileNotFoundException e) { 
            
            e.printStackTrace();
            return null;
            
        }
        
        if (path.endsWith(".fmu")) type = FileType.FMU;
        else if (!path.contains(".")) type = FileType.BLANK;
        else {
            
            String[] pathArr = path.split("/");
            name = pathArr[pathArr.length - 1];
            type = name.contains(".") && !name.endsWith(".") ? FileType.UNKNOWN : FileType.BLANK;
            
        } return new AppFile(path, name, file, type);
        
    }
    
    
    
        // -- GETTERS AND SETTERS -- \\
    
    public String getPath() { return path; }
    public String getName() { return name; }
    public FileType getType() { return type; }
    
}

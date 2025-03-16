package net.pumpkin.fmu.core.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.pumpkin.fmu.exceptions.DirectoryNotFoundException;
import net.pumpkin.fmu.exceptions.FileFoundException;

public class AppFile {

    private String path;     // Relative path of the file.
    private String name;     // The File name.
    private String fullname; // The full File name (extension included).
    private File file;       // For making changes to the file.
    private FileType type;   // The file extension.
    
    
    
        // -- INSTANCES -- \\
    
    private AppFile(String path, boolean isNew) {

        init(path, isNew);
        
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
     * AppFile instance with assigned fields if file does not
     * exist yet and no IOException is thrown; AppFile instance
     * with null fields otherwise.
     * 
     * Throws: 
     * FileFoundException if the file already exists
     * IOException if an I/O error occurred
     */
    public AppFile(String path) {
        
        init(path, true);
        
    }
    
    /*
     * Attempts to create an AppFile instance with an already
     * existing file, located using the relative path.
     * 
     * Params:
     * path - Relative path of the file
     * 
     * Returns: 
     * AppFile if the file exists
     * null if the file does not exist
     * 
     * Throws:
     * FileNotFoundException if the file doesn't exist
     */
    public static AppFile get(String path) {

        return new AppFile(path, false);
        
    }
    
    
    
        // -- STATIC -- \\
    
    /*
     * If a file with this exact name and directory exists.
     * 
     * Params:
     * path - Relative path of the file
     * 
     * Returns:
     * true if the file exists
     * false if the file does not exist
     */
    public static boolean exists(String path) {
        
        return Files.exists(Paths.get(path));
        
    }
    
    
    
        // -- LOCAL -- \\
    
    /* 
     * Creates a copy of the current file to the specified
     * location with the same content, name, and extension. If 
     * the specified location already has a file with this
     * exact name and extension, it's data will be replaced
     * with the copy's data.
     * 
     * Returns:
     * The full relative path of the copy; null if this ran
     * into an exception
     * 
     * Params:
     * path - Relative path of the new file's destination.
     */
    public String copy(String path) {

        path = strip(path);

        if (!transfer(path)) return null;
        return path + fullname;
        
    }
    
    /* 
     * Deletes the file.
     */
    public void delete() {

        file.delete();
        
    }

    /* 
     * Moves this file to the new location, updating the path
     * in the process. If the specified location already has 
     * a file with this exact name and extension, it's data 
     * will be replaced with the moved file's data.
     * 
     * Params:
     * path - Relative path of the new file's destination.
     */
    public void move(String path) {

        path = strip(path);
        
        if (!transfer(path)) return;
        delete();
        
        this.path = path + "/" + fullname;
        this.file = new File(this.path);
        
    }
    
    /*
     * Changes the name of the file. It does not change the 
     * file's extension, so the content of the passed string
     * will prepend it.
     * 
     * Params:
     * name - The new name of the file.
     */
    public void rename(String name) {
        
        if (!strip(name).replaceAll("/+", "").equals(name))
            throw new IllegalArgumentException("Name cannot include a slash.");
        
        if (type.equals(FileType.BLANK) && name.contains("."))
            throw new IllegalArgumentException("Name cannot include a period because the FileType is BLANK.");

        this.name = name;
        String[] nameArr = fullname.split("[.]");
        fullname = name + "." + nameArr[nameArr.length - 1];
        String[] pathArr = path.split("/");
        String newPath = "";
        
        for (int i = 0; i < pathArr.length - 1; i++)
            newPath += pathArr[i] + "/";
        
        newPath += fullname;
        
        Path source = Paths.get(path);
        
        try { Files.move(source, source.resolveSibling(fullname)); }
        catch (IOException e) { e.printStackTrace(); }
        
        path = newPath;
        file = new File(path);
        
    }
    
    /* 
     * Initializes this instance of the class.
     */
    private AppFile init(String path, boolean isNew) {

        path = strip(path);
        this.path = path;
        this.file = new File(path);
        
        if (isNew) {
            
            try { if (!file.createNewFile()) throw new FileFoundException(); } 
            catch (FileFoundException | IOException e) { 
                
                e.printStackTrace();
                
                this.path = null;
                this.file = null;
                
            } 
            
        }

        String[] pathArr = path.split("/");
        String[] nameArr = pathArr[pathArr.length - 1].split("[.]");
        String name = "";
        
        for (int i = 0; i < nameArr.length - 1; i++) 
            name += nameArr[i] + ".";
        
        this.fullname = pathArr[pathArr.length - 1];
        this.name = name.isEmpty() ? fullname : name.substring(0, name.length() - 1);
        
        if (path.endsWith(".fmu")) this.type = FileType.FMU;
        else if (fullname.contains(".") && !fullname.endsWith(".")) this.type = FileType.UNKNOWN;
        else this.type = FileType.BLANK;
        return this;
        
    }
    
    /* 
     * Sends data from the current file to the given path. If
     * the file exists, it will be overwritten.
     * 
     * returns true if no exceptions occurred; false otherwise.
     */
    private boolean transfer(String path) {
        
        path = strip(path);
        
        try {
            
            if (!Files.isDirectory(Paths.get(path))) 
                throw new DirectoryNotFoundException();
        
        } catch (DirectoryNotFoundException e) { 
            
            e.printStackTrace();
            return false;
            
        }
        
        path = path.isEmpty() ? "" : path + "/";
        File newFile = new File(path + fullname);
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path));
             PrintWriter writer = new PrintWriter(newFile)) {
            
            String line;
            
            while ((line = reader.readLine()) != null) 
                writer.println(line);
            
        } catch (IOException e) { e.printStackTrace(); return false; }

        return true;
        
    }
    
    /*
     * Removes trailing slashes and converts backslashes to
     * forward slashes.
     */
    private String strip(String path) {
        
        path = path.replace("\\", "/");
        
        if (path.startsWith("/")) path = path.substring(1);
        if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        return path;
        
    }
    
        // -- GETTERS AND SETTERS -- \\
    
    public String getPath() { return path; }
    public String getName() { return name; }
    public String getFullName() { return fullname; }
    public FileType getType() { return type; }
    
}

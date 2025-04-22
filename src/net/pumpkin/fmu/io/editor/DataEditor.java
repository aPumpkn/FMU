package net.pumpkin.fmu.io.editor;

import java.util.Collection;
import java.util.Map;

/*
 * Manages the given data which is accessed using a
 * relative pathing system. Receiving values only 
 * come in the form of a String; any parsing must
 * be done externally. All edits to the file are
 * only applied on complete(), therefore the order
 * in which edits are called do not matter; however,
 * if an edit uses a path that does not exist and 
 * is not created before calling complete(), it 
 * will run into an exception. 
 */
public interface DataEditor {
    
    <T> T get(String path, DataType type);
    Map<String,String> getEntries(String path);
    Collection<String> getFields(String path);
    boolean has(String path);
    boolean hasField(String path);
    
    DataEditor add(String path, Object value);
    DataEditor rename(String path, String name);
    DataEditor edit(String path, Object value);
    DataEditor remove(String path);
    DataEditor addField(String path);
    DataEditor addFields(String path);
    DataEditor renameField(String path, String name);
    DataEditor removeField(String path);
    
    void complete();
    
}

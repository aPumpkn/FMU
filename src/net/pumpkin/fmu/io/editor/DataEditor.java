package net.pumpkin.fmu.io.editor;

import java.util.Collection;
import java.util.Map;

/*
 * Manages the given data which is accessed using a
 * relative pathing system. Receiving values only 
 * comes in the form of a String; any parsing must
 * be done externally.
 */
public interface DataEditor {
    
    String getEntry(String path);
    Map<String,String> getEntries(String path);
    Collection<String> getFields(String path);
    boolean hasEntry(String path);
    boolean hasField(String path);
    
    DataEditor addEntry(String path, Object value);
    DataEditor editEntry(String path, Object value);
    DataEditor removeEntry(String path);
    DataEditor addField(String path);
    DataEditor renameField(String path, String name);
    DataEditor removeField(String path);
    
}

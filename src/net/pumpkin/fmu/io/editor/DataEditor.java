package net.pumpkin.fmu.io.editor;

import java.util.Collection;

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
    
    public enum DataField {
        
        FIELD,
        RECORD,
        ENTRY;
        
        public boolean isEntry() {
            
            if (this == FIELD || this == ENTRY) return true;
            return false;
            
        }

        public boolean isRecord() {
            
            if (this == FIELD || this == RECORD) return true;
            return false;
            
        }
        
    }
    
    <T> T get(String path, EntryType type);
    Collection<String> collect(String path, DataField field);
    boolean has(String path, DataField field);
    
    DataEditor add(String path, Object value);
    DataEditor add(String path);
    DataEditor set(String path, Object value);
    DataEditor set(String path);
    DataEditor edit(String path, Object value);
    DataEditor rename(String path, String name, DataField field);
    DataEditor remove(String path, DataField field);
    
    void complete();
    
}

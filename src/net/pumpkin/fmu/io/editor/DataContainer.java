package net.pumpkin.fmu.io.editor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.pumpkin.fmu.exceptions.EntryFoundException;
import net.pumpkin.fmu.exceptions.EntryNotFoundException;
import net.pumpkin.fmu.exceptions.FieldFoundException;
import net.pumpkin.fmu.exceptions.FieldNotFoundException;
import net.pumpkin.fmu.io.Formatter;
import net.pumpkin.fmu.utils.StringUtils;

/*
 * A container implementing the DataEditor. As opposed to any 
 * specific Format class, this class receives a Map of all 
 * readable data from a file to which information can be edited 
 * and read from memory. This is a lot more efficient to use 
 * when bulk-editing, however it can be counter-productive if 
 * only a simple entry or edit is needed in a large file.
 */
public class DataContainer implements DataEditor {
    
    private Map<String,String> storage;
    private String filepath;
    private boolean isChanged;
    
    public DataContainer(String filepath, Map<String,String> storage) {
        
        this.filepath = filepath;
        this.storage = storage;
        isChanged = false;
        
    }

    @Override
    public String getEntry(String path) {
        
        return storage.get(StringUtils.stripPath(path));
        
    }

    @Override
    public Map<String,String> getEntries(String path) {
        
        Map<String,String> entries = new LinkedHashMap<>(); 
        path = StringUtils.stripPath(path);
        boolean found = false;
        int pathCount = path.split("/").length;
        
        for (String key : storage.keySet()) {
            
            int currentPathCount = key.split("/").length;
            
            if (!found) {
                
                if (key.equals(path)) found = true;
                
            } else if (pathCount == currentPathCount + 1) {
                
                String value = storage.get(key);
                  
                if (value != null) entries.put(key, value);
                
            } else if (pathCount <= currentPathCount) break;  
            
        }
        
        return entries;
        
    }

    @Override
    public List<String> getFields(String path) {
        
        List<String> fields = new LinkedList<>();
        boolean found = false;
        int pathCount = path.split("/").length;
        
        for (String key : storage.keySet()) {
            
            int currentPathCount = key.split("/").length;
            
            if (!found) {
                
                if (key.equals(path)) found = true;
                
            } else if (pathCount == currentPathCount + 1) {
                
                if (storage.get(key) == null) fields.add(key);
                
            } else if (pathCount <= currentPathCount) break;
            
        }
        
        return null;
        
    }

    @Override
    public boolean hasEntry(String path) {
        
        String value = storage.get(StringUtils.stripPath(path));
        
        if (value == null) return false;
        return true;
        
    }

    @Override
    public boolean hasField(String path) {
        
        String key = StringUtils.stripPath(path);
        String value = storage.get(key);
        
        if (storage.containsKey(key) && value == null) return true;
        return false;
        
    }

    @Override
    public DataEditor addEntry(String path, Object value) {
        
        try {
            
            path = StringUtils.stripPath(path);
            
            if (storage.get(path) != null)
                throw new EntryFoundException();
            
            storage.put(path, String.valueOf(value));
            
            if (!isChanged) isChanged = true;
            
        } catch (EntryFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor renameEntry(String path, String name) {

        try {
            
            path = StringUtils.stripPath(path);
            String value = storage.get(path);
            
            if (value != null)
                throw new FieldNotFoundException();

            storage.remove(path);
            
            String[] pathArr = path.split("/");
            path = "";
            
            for (int i = 0; i < pathArr.length - 1; i++)
                path += pathArr[i] + "/";
            
            storage.put(path + name, value);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor editEntry(String path, Object value) {
        
        try {
            
            path = StringUtils.stripPath(path);
            
            if (storage.get(path) == null)
                throw new EntryNotFoundException();
            
            storage.put(path, String.valueOf(value));
            
            if (!isChanged) isChanged = true;
            
        } catch (EntryNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor removeEntry(String path) {

        try {
            
            path = StringUtils.stripPath(path);
            
            if (storage.get(path) == null)
                throw new EntryNotFoundException();
            
            storage.remove(path);
            
            if (!isChanged) isChanged = true;
            
        } catch (EntryNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor addField(String path) {
        
        try {
            
            path = StringUtils.stripPath(path);
            
            if (storage.containsKey(path) && storage.get(path) == null)
                throw new FieldFoundException();
            
            storage.put(path, null);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor renameField(String path, String name) {

        try {
            
            path = StringUtils.stripPath(path);
            
            if (!storage.containsKey(path) || storage.get(path) != null)
                throw new FieldNotFoundException();

            storage.remove(path);
            
            String[] pathArr = path.split("/");
            path = "";
            
            for (int i = 0; i < pathArr.length - 1; i++)
                path += pathArr[i] + "/";

            storage.put(path + name, null);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor removeField(String path) {
        
        try {
            
            path = StringUtils.stripPath(path);
            
            if (!storage.containsKey(path) || storage.get(path) != null)
                throw new FieldNotFoundException();
            
            storage.remove(path);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    /*
     * Calls on FileFormat to overwrite the file with 
     */
    @Override
    public void complete() {
        
        try {
        
            if (!isChanged) throw new Exception("There are no changes in the file to be made.");
            
            Formatter.resolveStorage(filepath, storage);
        
        } catch (Exception e) { e.printStackTrace(); }
        
    }
    
}

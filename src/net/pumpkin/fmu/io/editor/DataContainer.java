package net.pumpkin.fmu.io.editor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    
    public DataContainer(Map<String,String> storage) {
        
        this.storage = storage;
        
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
        
        for (String key : storage.keySet()) {
            
            if (!found) {
                
                if (key.equals(path)) found = true;
                
            } else if (!key.contains(path)) break;  
              else {
                
                if (storage.get(key) != null);
                
            }
            
        }
        
        return null;
        
    }

    @Override
    public List<String> getFields(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasEntry(String path) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasField(String path) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DataEditor addEntry(String path, Object value) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor editEntry(String path, Object value) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor removeEntry(String path) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor addField(String path) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor renameField(String path, String name) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor removeField(String path) {
        // TODO Auto-generated method stub
        return this;
    }

    /*
     * Calls on FileFormat to overwrite the file with 
     */
    public void complete() {
        // TODO Auto-generated method stub
        
    }
    
}

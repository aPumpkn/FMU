package net.pumpkin.fmu.io.memory;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.pumpkin.fmu.exceptions.EntryFoundException;
import net.pumpkin.fmu.exceptions.EntryNotFoundException;
import net.pumpkin.fmu.exceptions.FieldFoundException;
import net.pumpkin.fmu.exceptions.FieldNotFoundException;
import net.pumpkin.fmu.io.AppFile;
import net.pumpkin.fmu.io.DataEditor;
import net.pumpkin.fmu.io.memory.writer.FmuWriter;
import net.pumpkin.fmu.io.memory.writer.TextFileWriter;
import net.pumpkin.fmu.utils.StringUtils;

/*
 * A container implementing the DataEditor. As opposed to any 
 * specific Format class, this class receives a Map of all 
 * readable data from a file to which information can be edited 
 * and read from memory. This is a lot more efficient to use 
 * when bulk-editing, however it can be counter-productive if 
 * only a simple entry or edit is needed in a large file.
 */
public class MemoryEditor implements DataEditor {
    
    private Map<String,String> storage;
    private AppFile file;
    private boolean isChanged;
    
    public MemoryEditor(AppFile file, Map<String,String> storage) {
        
        this.file = file;
        this.storage = storage;
        isChanged = false;
        
    }

    @Override
    public String get(String path) {
        
        return storage.get(StringUtils.stripPath(path));
        
    }

    @Override
    public Map<String,String> getEntries(String path) {

        path = StringUtils.stripPath(path);
        Map<String,String> entries = new LinkedHashMap<>(); 
        int pathLength = path.isEmpty() ? 0 : path.split("/").length;
        
        for (String key : storage.keySet()) {
            
            String value = storage.get(key);
            String[] split = key.split("/");
            int keyLength = split.length;
            String name = split[keyLength - 1];
            String parent = "";
            
            for (int i = 0; i < keyLength - 1; i++)
                parent += split[i] + "/";
            
            if (!parent.isEmpty()) parent.substring(0, parent.length() - 1);
            if (value == null) continue;
            if (keyLength == pathLength + 1 && key.contains(path)) 
                entries.put(name, value);
            
        }
        
        return entries;
        
    }

    @Override
    public List<String> getFields(String path) {

        path = StringUtils.stripPath(path);
        List<String> fields = new LinkedList<>();
        int pathLength = path.isEmpty() ? 0 : path.split("/").length; 
        
        for (String key : storage.keySet()) {
            
            String value = storage.get(key);
            String[] split = key.split("/");
            int keyLength = split.length;
            String name = split[keyLength - 1];
            String parent = "";
            
            for (int i = 0; i < keyLength - 1; i++)
                parent += split[i] + "/";
            
            if (!parent.isEmpty()) parent.substring(0, parent.length() - 1);
            if (value != null) continue;
            if (keyLength == pathLength + 1 && key.contains(path)) 
                fields.add(name);
            
        }
        
        return fields;
        
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
            
            if (!path.isEmpty()) {
                
                String[] pathing = path.split("/");
                String parent = "";
                
                for (int i = 0; i < pathing.length - 1; i++)
                    parent += pathing[i] + "/";
                
                if (!parent.isEmpty()) parent = parent.substring(0, parent.length() - 1);
                if (!hasField(parent)) throw new Exception("The parent pathing does not exist.");
            
            }
            
            storage.put(path, String.valueOf(value));
            
            if (!isChanged) isChanged = true;
            
        } catch (EntryFoundException e) { e.printStackTrace(); } 
          catch (Exception e) { e.printStackTrace(); }
        
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
            
            if (!path.isEmpty()) {
                
                String[] pathing = path.split("/");
                String parent = "";
                
                for (int i = 0; i < pathing.length - 1; i++) 
                    parent += pathing[i] + "/";
                
                if (!hasField(parent)) throw new FieldNotFoundException();
                
            }
            
            storage.put(path, null);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldFoundException e) { e.printStackTrace(); } 
          catch (FieldNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor addFields(String path) {
        
        try {
            
            path = StringUtils.stripPath(path);
            
            if (storage.containsKey(path) && storage.get(path) == null)
                throw new FieldFoundException();
            
            if (!path.isEmpty()) {
                
                String[] pathing = path.split("/");
                String parent = "";
                
                for (int i = 0; i < pathing.length - 1; i++) {
                    
                    parent += pathing[i] + "/";
                    
                    if (!hasField(parent)) storage.put(parent.substring(0, parent.length() - 1), null);
                    
                }
            
            }
            
            storage.put(path, null);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldFoundException e) { e.printStackTrace(); } 
          catch (Exception e) { e.printStackTrace(); }
        
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

            if (!path.isEmpty()) path = path.substring(0, path.length() - 1);
            
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
            
            TextFileWriter writer;
            
            switch (file.getType()) {
                
                case FMU:
                    writer = new FmuWriter();
                    break;
                    
                default:
                    writer = null;
                
            }
            
            writer.store(file.getPath(), storage);
            
        } catch (Exception e) { e.printStackTrace(); }
        
    }
    
}

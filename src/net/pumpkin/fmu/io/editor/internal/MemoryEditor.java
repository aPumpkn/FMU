package net.pumpkin.fmu.io.editor.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.pumpkin.fmu.exceptions.EntryFoundException;
import net.pumpkin.fmu.exceptions.EntryNotFoundException;
import net.pumpkin.fmu.exceptions.FieldFoundException;
import net.pumpkin.fmu.exceptions.FieldNotFoundException;
import net.pumpkin.fmu.io.AppFile;
import net.pumpkin.fmu.io.editor.DataEditor;
import net.pumpkin.fmu.io.editor.EntryType;
import net.pumpkin.fmu.io.writer.FmuWriter;
import net.pumpkin.fmu.io.writer.TextFileWriter;
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
    public <T> T get(String path, EntryType type) {
        
        String data = storage.get(StringUtils.stripPath(path));
        String value = "";
        
        try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
            
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                
                if (firstLine) {
                    
                    if (type != EntryType.ofString(line)) 
                        throw new Exception("The passed parameter 'type' does not match the entry's DataType.");
                    
                    firstLine = false;
                    
                } else value += line + "\n";
                
            }
            
        } catch (IOException e) { e.printStackTrace(); }
          catch (Exception e) { e.printStackTrace(); }

        if (!value.isEmpty()) return type.parse(value.trim());
        return null;
        
    }

    @Override
    public Collection<String> collect(String path, DataField fieldType) {
        
        path = StringUtils.stripPath(path);
        List<String> fields = new LinkedList<>(); 
        int pathLength = path.isEmpty() ? 0 : path.split("/").length;
        boolean wantsEntry = fieldType.isEntry();
        boolean wantsRecord = fieldType.isRecord();
        
        for (String key : storage.keySet()) {
            
            String value = storage.get(key);
            
            if ((wantsEntry && value == null) || 
                (wantsRecord && value != null)) continue;
            
            String[] split = key.split("/");
            int keyLength = split.length;
            String name = split[keyLength - 1];
            String parent = "";
            
            for (int i = 0; i < keyLength - 1; i++)
                parent += split[i] + "/";
            
            if (!parent.isEmpty()) parent.substring(0, parent.length() - 1);
            if (keyLength == pathLength + 1 && key.contains(path)) 
                fields.add(name);
            
        }
        
        return fields;
        
    }

    @Override
    public boolean has(String path, DataField fieldType) {
        
        String key = StringUtils.stripPath(path);
        String value = storage.get(key);
        
        if (fieldType.isEntry() && value != null) return true;
        if (fieldType.isRecord() && value == null && storage.containsKey(key)) return true;
        return false;
        
    }

    @Override
    public DataEditor add(String path, Object value) {
        
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
                if (!has(parent, DataField.RECORD)) throw new Exception("The parent pathing does not exist.");
            
            }
            
            storage.put(path, EntryType.asString(value));
            
            if (!isChanged) isChanged = true;
            
        } catch (EntryFoundException e) { e.printStackTrace(); } 
          catch (Exception e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor add(String path) {
        
        try {
            
            path = StringUtils.stripPath(path);
            
            if (storage.containsKey(path) && storage.get(path) == null)
                throw new FieldFoundException();
            
            if (!path.isEmpty()) {
                
                String[] pathing = path.split("/");
                String parent = "";
                
                for (int i = 0; i < pathing.length - 1; i++) 
                    parent += pathing[i] + "/";
                
                if (!parent.isEmpty() && !has(parent, DataField.RECORD)) 
                    throw new FieldNotFoundException();
                
            } else throw new Exception("Cannot provide an empty path name.");
            
            storage.put(path, null);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldFoundException e) { e.printStackTrace(); } 
          catch (FieldNotFoundException e) { e.printStackTrace(); } 
          catch (Exception e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor set(String entry, Object value) {
        
        try {
            
            entry = StringUtils.stripPath(entry);
            
            if (storage.get(entry) != null) throw new EntryFoundException();
            if (entry.isEmpty()) throw new IllegalArgumentException("The passed argument 'entry' cannot be a blank string.");
                
            String[] pathing = entry.split("/");
            
            if (pathing.length > 1) {
            
                String parent = "";
                
                for (int i = 0; i < pathing.length - 1; i++) {
                    
                    parent += pathing[i] + "/";
                    
                    if (!has(parent, DataField.RECORD)) 
                        storage.put(parent.substring(0, parent.length() - 1), null);
                    
                }
            
            }
            
            storage.put(entry, EntryType.asString(value));
            
            if (!isChanged) isChanged = true;
            
        } catch (EntryFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor set(String entry) {
        
        try {
            
            entry = StringUtils.stripPath(entry);

            if (entry.isEmpty()) throw new IllegalArgumentException("The passed argument 'entry' cannot be a blank string.");
            if (storage.containsKey(entry) && storage.get(entry) == null)
                throw new FieldFoundException();
            
            if (!entry.isEmpty()) {
                
                String[] pathing = entry.split("/");
                String parent = "";
                
                for (int i = 0; i < pathing.length - 1; i++) {
                    
                    parent += pathing[i] + "/";
                    
                    if (!has(parent, DataField.RECORD)) 
                        storage.put(parent.substring(0, parent.length() - 1), null);
                    
                }
            
            }
            
            storage.put(entry, null);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldFoundException e) { e.printStackTrace(); } 
          catch (Exception e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor rename(String field, String name, DataField fieldType) {

        try {
            
            field = StringUtils.stripPath(field);
            String value = storage.get(field);
            
            if (fieldType.isEntry() && value == null)
                throw new EntryNotFoundException();
            
            if (fieldType.isRecord() && value != null && storage.containsKey(field))
                throw new FieldNotFoundException();

            storage.remove(field);
            
            String[] pathArr = field.split("/");
            field = "";
            
            for (int i = 0; i < pathArr.length - 1; i++)
                field += pathArr[i] + "/";
            
            storage.put(field + name, value);
            
            if (!isChanged) isChanged = true;
            
        } catch (FieldNotFoundException e) { e.printStackTrace(); } 
          catch (EntryNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor edit(String path, Object value) {
        
        try {
            
            path = StringUtils.stripPath(path);
            
            if (storage.get(path) == null)
                throw new EntryNotFoundException();
            
            storage.put(path, EntryType.asString(value));
            
            if (!isChanged) isChanged = true;
            
        } catch (EntryNotFoundException e) { e.printStackTrace(); }
        
        return this;
        
    }

    @Override
    public DataEditor remove(String field, DataField fieldType) {

        try {
            
            field = StringUtils.stripPath(field);
            
            if (fieldType.isEntry() && storage.get(field) == null)
                throw new EntryNotFoundException();
            
            if (!storage.containsKey(field) || 
                (storage.containsKey(field) && storage.get(field) != null))
                throw new FieldNotFoundException();
            
            Map<String,String> queue = new LinkedHashMap<>(storage); 
            
            for (String key : queue.keySet()) 
                if (key.contains(field)) storage.remove(key);
                
            if (!isChanged) isChanged = true;
            
        } catch (EntryNotFoundException e) { e.printStackTrace(); } 
          catch (FieldNotFoundException e) { e.printStackTrace(); }
        
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

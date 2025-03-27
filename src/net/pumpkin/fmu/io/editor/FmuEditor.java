package net.pumpkin.fmu.io.editor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.pumpkin.fmu.utils.StringUtils;

public class FmuEditor implements DataEditor {

    @Override
    public String getEntry(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getEntries(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getFields(String path) {
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
        return null;
    }

    @Override
    public DataEditor renameEntry(String path, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataEditor editEntry(String path, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataEditor removeEntry(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataEditor addField(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataEditor renameField(String path, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataEditor removeField(String path) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static void store(String path, Map<String,String> storage) {
        
        try (PrintWriter writer = new PrintWriter(path)) {
            
            /*
             * TODO: Add support for arrays
             */
            
            Map<String,String> queue = new LinkedHashMap<>();
            String indent = "";
            String search = "";
            boolean done = false;
            
            while (!storage.isEmpty()) {
                
                // get all elements in the corresponding path
                for (Entry<String,String> entry : storage.entrySet()) {
                    
                    String[] split = entry.getKey().split("/");
                    String key = split[split.length - 1];
                    String parent = "";
                    
                    for (int i = 0; i < split.length - 1; i++)
                        parent += split[i] + "/";
                    
                    if (!parent.isEmpty()) parent = parent.substring(0, parent.length() - 1);                    
                    if (search.equals(parent)) queue.put(entry.getKey(), entry.getValue());
                    
                } if (!queue.isEmpty()) {
                    
                    for (Entry<String,String> entry : queue.entrySet()) { // set down all entries first

                        String[] split = entry.getKey().split("/");
                        String key = split[split.length - 1];
                        String value = entry.getValue();
                        
                        if (value != null) {
                            
                            writer.println(indent + key + ": " + value);
                            storage.remove(entry.getKey());

                            if (storage.isEmpty()) done = true;
                            
                        }
                        
                    } for (String i : queue.keySet()) { // then get the first field

                        String value = queue.get(i);
                        String[] split = i.split("/");
                        String key = split[split.length - 1];
                        
                        if (value == null) {
                            
                            writer.println(indent + key + " {");
                            storage.remove(i);
                            
                            if (storage.isEmpty()) done = true;
                            
                            search = search.isEmpty() ? key : search + "/" + key;
                            indent += StringUtils.TAB;
                            break;
                            
                        }
                        
                    }
                    
                    queue.clear();
                    
                } else if (!done && !indent.isEmpty()) { // Go back one step in the file's pathing.
                    
                    indent = indent.substring(4);
                    String[] split = search.split("/");
                    search = "";
                    
                    for (int i = 0; i < split.length - 1; i++)
                        search += split[i] + "/";
                    
                    if (!search.isEmpty()) search = search.substring(0, search.length() - 1);
                    
                    writer.println(indent + "}");
                    
                } if (done) while (!indent.isEmpty()) {
                    
                    // There's nothing left, wrap it up.
                    indent = indent.substring(4);
                    writer.println(indent + "}");
                        
                }
                
            }
            
        } catch (FileNotFoundException e) { e.printStackTrace(); }
        
    }

    @Override
    public void complete() {
        
    }
    
}

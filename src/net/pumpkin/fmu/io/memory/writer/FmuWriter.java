package net.pumpkin.fmu.io.memory.writer;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.pumpkin.fmu.utils.StringUtils;

public class FmuWriter implements TextFileWriter {

    @Override
    public void write(PrintWriter writer, Map<String,String> bag) {
        
        /*
         * TODO: Add support for arrays
         */
        
        Map<String,String> queue = new LinkedHashMap<>(); // Elements to store for a given path
        String indent = ""; // tab for readability
        String search = ""; // the current navigational path
        
        while (!bag.isEmpty()) {
            
            for (Entry<String,String> entry : bag.entrySet()) { // Setup the queue for this path
                
                String[] split = entry.getKey().split("/");
                String key = split[split.length - 1];
                String parent = "";
                
                for (int i = 0; i < split.length - 1; i++)
                    parent += split[i] + "/";
                
                if (!parent.isEmpty()) parent = parent.substring(0, parent.length() - 1);
                if (search.equals(parent)) queue.put(entry.getKey(), entry.getValue());
                
            } if (!queue.isEmpty()) { // if we have elements to write
                
                for (Entry<String,String> entry : queue.entrySet()) { // set down all entries first, if any
                    
                    String[] split = entry.getKey().split("/");
                    String key = split[split.length - 1];
                    String value = entry.getValue();
                    
                    if (value != null) {
                        
                        writer.println(indent + key + ": " + value);
                        bag.remove(entry.getKey());
                        
                    }
                    
                } for (String i : queue.keySet()) { // if there are any fields in queue, write the first one and update the search query
                    
                    String value = queue.get(i);
                    String[] split = i.split("/");
                    String key = split[split.length - 1];
                    
                    if (value == null) {
                        
                        writer.println(indent + key + " {");
                        bag.remove(i);
                        
                        search = search.isEmpty() ? key : search + "/" + key;
                        indent += StringUtils.TAB;
                        break;
                        
                    }
                    
                }
                
                queue.clear(); // queue is complete
                
            } else if (!bag.isEmpty() && !indent.isEmpty()) { // Nothing left to write here, so go back one navigational step if possible.
                
                indent = indent.substring(4);
                String[] split = search.split("/");
                search = "";
                
                for (int i = 0; i < split.length - 1; i++)
                    search += split[i] + "/";
                
                if (!search.isEmpty()) search = search.substring(0, search.length() - 1);
                
                writer.println(indent + "}");
                
            } if (bag.isEmpty()) while (!indent.isEmpty()) {
                
                // There's nothing left to write, so wrap it up.
                indent = indent.substring(4);
                writer.println(indent + "}");
                    
            }
            
        }
        
    }
    
}

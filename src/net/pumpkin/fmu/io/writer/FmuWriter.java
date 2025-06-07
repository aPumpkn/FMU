package net.pumpkin.fmu.io.writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.pumpkin.fmu.io.editor.EntryType;
import net.pumpkin.fmu.utils.StringUtils;

public class FmuWriter implements TextFileWriter {

    @Override
    public void write(PrintWriter writer, Map<String,String> bag) {
        
        Map<String,String> queue = new LinkedHashMap<>(); // Elements to store for a given path
        String indent = ""; // tab for readability
        String search = ""; // the current navigable path
        
        while (!bag.isEmpty()) {
            
            for (Entry<String,String> entry : bag.entrySet()) { // Setup the queue for this path
                
                String[] split = entry.getKey().split("/");
                String parent = "";
                
                for (int i = 0; i < split.length - 1; i++)
                    parent += split[i] + "/";
                
                if (!parent.isEmpty()) parent = parent.substring(0, parent.length() - 1);
                if (search.equals(parent)) queue.put(entry.getKey(), entry.getValue());
                
            } if (!queue.isEmpty()) { // if we have elements to write
                
                for (Entry<String,String> entry : queue.entrySet()) { 
                	
                	// set down all entries first, if any
                    String[] split = entry.getKey().split("/");
                    String key = split[split.length - 1];
                    String value = entry.getValue();
                    
                    if (value != null) {

                        String literal = "";
                        EntryType type = null;

                        try (BufferedReader reader = new BufferedReader(new StringReader(value))) {
                            
                            String line;
                            boolean isArray = true;
                            boolean firstLine = true;
                            
                            while ((line = reader.readLine()) != null) {
                                
                                if (firstLine) {
                                    
                                    type = EntryType.ofString(line);
                                    firstLine = false;
                                    
                                    if (!type.isArray()) {
                                        
                                        isArray = false;
                                        line = reader.readLine();
                                        
                                        if (type.isWrapped()) literal = type.getSymbol() + line + type.getSymbol();
                                        else literal = line + type.getSymbol();
                                        break;
                                        
                                    } else literal = type.getSymbol() + "[\n";
                                    
                                } else literal += indent + StringUtils.TAB + line + "\n";
                                
                            } if (isArray) literal += indent + "]";
                              else literal = literal.trim();
                            
                        } catch (IOException e) { e.printStackTrace(); }
                          catch (Exception e) { e.printStackTrace(); }
                        
                        writer.println(indent + key + ": " + literal);
                        bag.remove(entry.getKey());
                        
                    }
                    
                } for (String i : queue.keySet()) { 
                	
                	// if there are any fields in queue, write the first one and update the search query
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
                
            } else if (!bag.isEmpty() && !indent.isEmpty()) { // Nothing left to write here, so go back one navigable step if possible.
                
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

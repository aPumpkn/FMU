package net.pumpkin.fmu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.pumpkin.fmu.io.editor.DataType;

public class FmuReader implements TextFileReader {
    
    @Override
    public Map<String,String> read(BufferedReader reader) throws IOException {
        
        Map<String,String> storage = new LinkedHashMap<>(); 
        
        String line = "";
        String search = "";
        
        while ((line = reader.readLine()) != null) {
            
            String content = line.trim();
            
            if (content.isEmpty()) continue;
            if (content.endsWith("{")) {
                
                String key = content.substring(0, content.length() - 2);
                search = search.isEmpty() ? key : search + "/" + key;
                
                storage.put(search, null);
                
            } else if (content.equals("}")) {
                
                String[] index = search.split("/");
                search = "";
                
                for (int i = 0; i < index.length - 1; i++) 
                    search += index[i] + "/";
                
                if (!search.isEmpty()) 
                    search = search.substring(0, search.length() - 1);
                
            } else if (content.contains(":")) {
                
                String[] split = content.split(":");
                String key = search.isEmpty() ? split[0].trim() : search + "/" + split[0].trim();
                String value = split.length == 1 ? "" : split[1].trim();
                
                if (value.endsWith("[")) {
                    
                    String arr = value.split("(\\[)")[0] + " array\n";
                    
                    while ((line = reader.readLine()) != null) {
                        
                        line = line.trim();
                        
                        if (!line.equals("]")) arr += line + "\n";
                        else break;
                        
                    }
                    
                    storage.put(key, arr.trim());
                    
                } else {
                    
                    String symbol = value.charAt(value.length() - 1) + "";
                    DataType dataType = DataType.ofSymbol(symbol);
                    value = dataType.toString() + "\n" + (dataType.isWrapped() 
                            ? value.substring(1, value.length() - 1)
                            : value.substring(0, value.length() - 1));
                    
                    storage.put(key, value);
                    
                }
                
            }
            
        }
        
        return storage;
        
    }
    
}

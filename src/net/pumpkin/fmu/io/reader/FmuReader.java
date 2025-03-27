package net.pumpkin.fmu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * The reader for ".fmu" files.
 */
public class FmuReader implements FileReader {
    
    @Override
    public Map<String,String> read(BufferedReader reader) throws IOException {
        
        Map<String,String> storage = new LinkedHashMap<>();
        
        String line = "";
        String path = "";
        
        while ((line = reader.readLine()) != null) {
            
            String content = line.trim();
            
            if (content.isEmpty()) continue;
            if (content.endsWith("{")) {
                
                String key = content.substring(0, content.length() - 2);
                path = path.isEmpty() ? key : path + "/" + key;
                
                storage.put(path, null);
                
            } else if (content.equals("}")) {
                
                String[] index = path.split("/");
                path = "";
                
                for (int i = 0; i < index.length - 1; i++) 
                    path += index[i] + "/";
                
                if (!path.isEmpty()) 
                    path = path.substring(0, path.length() - 1);
                
            } else if (content.contains(":")) {
                
                String[] split = content.split(":");
                String key = path.isEmpty() ? split[0].trim() : path + "/" + split[0].trim();
                String value = split.length == 1 ? "" : split[1].trim();
                
                if (value.equals("[")) {
                    
                    String arr = "";
                    
                    while ((line = reader.readLine()) != null) {
                        
                        line = line.trim();
                        
                        if (!line.equals("]")) arr += line + "\n";
                        else break;
                        
                    }
                    
                    storage.put(key, arr.trim());
                    
                } else storage.put(key, value);
                
            }
            
        }
        
        return storage;
        
    }
    
}

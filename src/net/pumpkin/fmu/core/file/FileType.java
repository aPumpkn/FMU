package net.pumpkin.fmu.core.file;

// Representation of a file's extension.
public enum FileType {
    
    FMU,      // Specific for this API
    BLANK,    // Has no extension
    UNKNOWN;  // Is not a recognizeable extension
 
    @Override
    public String toString() {
        
        switch (this) {
            
            case FMU:
                return ".fmu";
                
            case BLANK:
                return "";
                
            case UNKNOWN:
                return "Unknown";
                
            default:
                return null;
            
        }
        
    }
}

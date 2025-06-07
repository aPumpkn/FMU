package net.pumpkin.fmu.io;

// Representation of a file's extension.
public enum FileType {
    
    FMU,      // Specific for this API
    BLANK,    // Has no extension
    UNKNOWN;  // Is not a recognizable extension
 
    @Override
    public String toString() {
        
        switch (this) {
            
            case FMU:
                return ".fmu";
                
            case BLANK:
                return "Blank";
                
            case UNKNOWN:
                return "Unknown";
                
            default:
                return null;
            
        }
        
    }
}

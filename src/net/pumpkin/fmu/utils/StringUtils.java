package net.pumpkin.fmu.utils;

/*
 * Static class that reformats strings.
 */
final public class StringUtils {
    
    final public static String TAB = "    "; // Whitespace for file indentation.
    
    private StringUtils() {}
    
    /*
     * Removes trailing and leading slashes and converts
     * backslashes to forward slashes. Also removes
     * trailing and leading whitespace. This is mainly
     * used for operating on relative paths.
     * 
     * Params:
     * path - The given path to format
     * 
     * Returns: 
     * Newly formatted string
     */
    public static String stripPath(String path) {
        
        path = path.replace("\\", "/");
        
        if (path.startsWith("/")) path = path.substring(1);
        if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        return path.trim();
        
    }
    
    /*
     * Convenience method to capitalize the first letter
     * and set the rest to lowercase.
     * 
     * Params:
     * str - The given string to format
     * 
     * Returns:
     * Newly formatted string
     */
    public static String capitalize(String str) {
        
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        
    }
    
}

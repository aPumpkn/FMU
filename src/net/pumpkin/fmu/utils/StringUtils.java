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
    
    /* 
     * If the string can be parsed as a byte.
     */
    public static boolean isByte(String str) {
        
        try { Byte.parseByte(str); } 
        catch (NumberFormatException e) { return false; }
        return true;
        
    }
    
    /* 
     * If the string can be parsed as a short.
     */
    public static boolean isShort(String str) {
        
        try { Short.parseShort(str); } 
        catch (NumberFormatException e) { return false; }
        return true;
        
    }
    
    /* 
     * If the string can be parsed as an int.
     */
    public static boolean isInt(String str) {
        
        try { Integer.parseInt(str); } 
        catch (NumberFormatException e) { return false; }
        return true;
        
    }
    
    /* 
     * If the string can be parsed as a long.
     */
    public static boolean isLong(String str) {
        
        try { Long.parseLong(str); } 
        catch (NumberFormatException e) { return false; }
        return true;
        
    }
    
    /* 
     * If the string can be parsed as a float.
     */
    public static boolean isFloat(String str) {
        
        try { Float.parseFloat(str); } 
        catch (NumberFormatException e) { return false; }
        return true;
        
    }
    
    /* 
     * If the string can be parsed as a double.
     */
    public static boolean isDouble(String str) {
        
        try { Double.parseDouble(str); } 
        catch (NumberFormatException e) { return false; }
        return true;
        
    }
    
    /* 
     * If the string can be parsed as a boolean.
     */
    public static boolean isBoolean(String str) {
        
        try { Boolean.parseBoolean(str); } 
        catch (NumberFormatException e) { return false; }
        return true;
        
    }
    
    /* 
     * If the string can be parsed as a char.
     */
    public static boolean isChar(String str) {
        
        if (str.length() == 1) return true;
        return false;
        
    }
    
}

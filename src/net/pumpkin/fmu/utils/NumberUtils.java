package net.pumpkin.fmu.utils;

/*
 * Static class involving generic numeral functions.
 */
final public class NumberUtils {
    
    private NumberUtils() {}
    
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

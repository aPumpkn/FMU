package net.pumpkin.fmu.io.editor;

public enum DataType {
    
    BYTE          ("b",       "byte"),
    SHORT         ("s",       "short"),
    INTEGER       ("i",       "integer"),
    LONG          ("L",       "long"),
    FLOAT         ("f",       "float"),
    DOUBLE        ("d",       "double"),
    CHAR          ("'",       "char"),
    STRING        ("\"",      "string"),
    BOOLEAN       ("bool",    "boolean"),
    BYTE_ARRAY    ("byte",    "byte array"),
    SHORT_ARRAY   ("short",   "short array"),
    INTEGER_ARRAY ("integer", "integer array"),
    LONG_ARRAY    ("long",    "long array"),
    FLOAT_ARRAY   ("float",   "float array"),
    DOUBLE_ARRAY  ("double",  "double array"),
    CHAR_ARRAY    ("char",    "char array"),
    STRING_ARRAY  ("string",  "string array"),
    BOOLEAN_ARRAY ("boolean", "boolean_array");
    
    private String symbol;
    private String name;
    
    private DataType(String symbol, String name) {
        
        this.symbol = symbol;
        this.name = name;
        
    }
    
    public static DataType ofString(String str) {
        
        switch (str) {
            
            case "byte":
                return BYTE;

            case "short":
                return SHORT;

            case "integer":
                return INTEGER;

            case "long":
                return LONG;

            case "float":
                return FLOAT;

            case "double":
                return DOUBLE;

            case "char":
                return CHAR;

            case "string":
                return STRING;

            case "boolean":
                return BOOLEAN;

            case "byte array":
                return BYTE_ARRAY;

            case "short array":
                return SHORT_ARRAY;

            case "integer array":
                return INTEGER_ARRAY;

            case "long array":
                return LONG_ARRAY;

            case "float array":
                return FLOAT_ARRAY;

            case "double array":
                return DOUBLE_ARRAY;

            case "char array":
                return CHAR_ARRAY;

            case "string array":
                return STRING_ARRAY;

            case "boolean array":
                return BOOLEAN_ARRAY;
                
            default:
                return null;
            
        }
        
    }
    
    public static DataType ofSymbol(String str) {
        
        switch (str) {
            
            case "b":
                return BYTE;

            case "s":
                return SHORT;

            case "i":
                return INTEGER;

            case "L":
                return LONG;

            case "f":
                return FLOAT;

            case "d":
                return DOUBLE;

            case "'":
                return CHAR;

            case "\"":
                return STRING;

            case "bool":
                return BOOLEAN;

            case "byte":
                return BYTE_ARRAY;

            case "short":
                return SHORT_ARRAY;

            case "integer":
                return INTEGER_ARRAY;

            case "long":
                return LONG_ARRAY;

            case "float":
                return FLOAT_ARRAY;

            case "double":
                return DOUBLE_ARRAY;

            case "char":
                return CHAR_ARRAY;

            case "string":
                return STRING_ARRAY;

            case "boolean":
                return BOOLEAN_ARRAY;
                
            default:
                return null;
            
        }
        
    }
    
    public static <T> String asString(T value) {
        
        String literal;
        
        if (value.getClass().isArray()) {
            
            if (value instanceof Boolean[]) {
                
                literal = "boolean array\n";
                
                for (boolean i : (Boolean[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof Byte[]) {
                
                literal = "byte array\n";
                
                for (byte i : (Byte[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof Short[]) {
                
                literal = "short array\n";
                
                for (short i : (Short[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof Integer[]) {
                
                literal = "integer array\n";
                
                for (int i : (Integer[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof Long[]) {
                
                literal = "long array\n";
                
                for (long i : (Long[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof Float[]) {
                
                literal = "float array\n";
                
                for (float i : (Float[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof Double[]) {
                
                literal = "double array\n";
                
                for (double i : (Double[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof Character[]) {
                
                literal = "char array\n";
                
                for (char i : (Character[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof String[]) {
                
                literal = "string array\n";
                
                for (String i : (String[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof boolean[]) {
                
                literal = "boolean array\n";
                
                for (boolean i : (boolean[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof byte[]) {
                
                literal = "byte array\n";
                
                for (byte i : (byte[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof short[]) {
                
                literal = "short array\n";
                
                for (short i : (short[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof int[]) {
                
                literal = "integer array\n";
                
                for (int i : (int[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof long[]) {
                
                literal = "long array\n";
                
                for (long i : (long[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof float[]) {
                
                literal = "float array\n";
                
                for (float i : (float[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof double[]) {
                
                literal = "double array\n";
                
                for (double i : (double[])value) 
                    literal += i + "\n";
                
            } else if (value instanceof char[]) {
                
                literal = "char array\n";
                
                for (char i : (char[])value) 
                    literal += i + "\n";
                
            } else literal = null;
            
        } else {
            
            if (value instanceof Boolean) literal = "boolean\n" + value;
            else if (value instanceof Byte) literal = "byte\n" + value;
            else if (value instanceof Short) literal = "short\n" + value;
            else if (value instanceof Integer) literal = "integer\n" + value;
            else if (value instanceof Long) literal = "long\n" + value;
            else if (value instanceof Float) literal = "float\n" + value;
            else if (value instanceof Double) literal = "double\n" + value;
            else if (value instanceof Character) literal = "character\n" + value;
            else if (value instanceof String) literal = "string\n" + value;
            else literal = null;
            
        } return literal.trim();
        
    }
    
    public <T> T parse(String value) {
        
        switch (this) {
            
            case BOOLEAN:
                return (T)((Boolean)Boolean.parseBoolean(value));
            
            case BYTE:
                return (T)((Byte)Byte.parseByte(value));
                
            case SHORT:
                return (T)((Short)Short.parseShort(value));
                
            case INTEGER:
                return (T)((Integer)Integer.parseInt(value));
                
            case LONG:
                return (T)((Long)Long.parseLong(value));
                
            case FLOAT:
                return (T)((Float)Float.parseFloat(value));
                
            case DOUBLE:
                return (T)((Double)Double.parseDouble(value));
                
            case CHAR:
                return (T)(value.length() == 1 ? value.charAt(0) : null);
                
            case STRING:
                return (T)value;
                
            case BOOLEAN_ARRAY:
                String[] boolean_elements = value.split("\n");
                int boolean_length = boolean_elements.length;
                boolean[] boolean_arr = new boolean[boolean_length];
                
                for (int i = 0; i < boolean_length; i++) 
                    boolean_arr[i] = Boolean.parseBoolean(boolean_elements[i]);
                
                return (T)boolean_arr;
                
            case BYTE_ARRAY:
                String[] byte_elements = value.split("\n");
                int byte_length = byte_elements.length;
                byte[] byte_arr = new byte[byte_length];
                
                for (int i = 0; i < byte_length; i++) 
                    byte_arr[i] = Byte.parseByte(byte_elements[i]);
                
                return (T)byte_arr;
                
            case SHORT_ARRAY:
                String[] short_elements = value.split("\n");
                int short_length = short_elements.length;
                short[] short_arr = new short[short_length];
                
                for (int i = 0; i < short_length; i++) 
                    short_arr[i] = Short.parseShort(short_elements[i]);
                
                return (T)short_arr;
                
            case INTEGER_ARRAY:
                String[] int_elements = value.split("\n");
                int int_length = int_elements.length;
                int[] int_arr = new int[int_length];
                
                for (int i = 0; i < int_length; i++) 
                    int_arr[i] = Integer.parseInt(int_elements[i]);
                
                return (T)int_arr;
                
            case LONG_ARRAY:
                String[] long_elements = value.split("\n");
                int long_length = long_elements.length;
                long[] long_arr = new long[long_length];
                
                for (int i = 0; i < long_length; i++) 
                    long_arr[i] = Long.parseLong(long_elements[i]);
                
                return (T)long_arr;
                
            case FLOAT_ARRAY:
                String[] float_elements = value.split("\n");
                int float_length = float_elements.length;
                float[] float_arr = new float[float_length];
                
                for (int i = 0; i < float_length; i++) 
                    float_arr[i] = Float.parseFloat(float_elements[i]);
                
                return (T)float_arr;
                
            case DOUBLE_ARRAY:
                String[] double_elements = value.split("\n");
                int double_length = double_elements.length;
                double[] double_arr = new double[double_length];
                
                for (int i = 0; i < double_length; i++) 
                    double_arr[i] = Double.parseDouble(double_elements[i]);
                
                return (T)double_arr;
                
            case CHAR_ARRAY:
                String[] char_elements = value.split("\n");
                int char_length = char_elements.length;
                char[] char_arr = new char[char_length];
                
                for (int i = 0; i < char_length; i++)
                    char_arr[i] = char_elements[i].length() == 1 
                        ? char_elements[i].charAt(0) 
                        : null; 
                
                return (T)char_arr;
                
            case STRING_ARRAY:
                return (T)value.split("\n");
                
            default:
                return null;
            
        }
        
    }
    
    public boolean isWrapped() {
        
        if (this == CHAR || this == STRING) return true;
        return false;
    }
    
    public boolean isArray() {
        
        if (name.endsWith("array")) return true;
        return false;
        
    }
    
    @Override
    public String toString() {
        
        return name;
        
    }
    
    public String getSymbol() {
        
        return symbol;
        
    }
    
}

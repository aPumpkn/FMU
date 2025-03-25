package net.pumpkin.fmu.math;

public enum ByteUnit {
    
    BYTE     ("Byte", 1.0),
    KILOBYTE ("Kilobyte", 1000.0),
    MEGABYTE ("Megabyte", 1000000.0),
    GIGABYTE ("Gigabyte", 1000000000.0);

    private final String name;
    private final double factor;
    
    private ByteUnit(String name, double unit) {
        
        this.name = name;
        this.factor = unit;
        
    }
    
    public double convert(long num) {
        
        return num / factor;
        
    }
    
    public String abbrev() {
        
        if (this == BYTE) return "B";
        return name.substring(0, 1) + "B";
        
    }
    
    @Override
    public String toString() {
        
        return name;
        
    }
    
}

package net.pumpkin.fmu.exceptions;

public class FieldNotFoundException extends Exception {

    private static final long serialVersionUID = 14693582477595152L;
    
    public FieldNotFoundException() { super("Could not find a field using the given path."); }
    
}

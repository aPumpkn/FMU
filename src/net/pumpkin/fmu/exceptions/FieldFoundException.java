package net.pumpkin.fmu.exceptions;

public class FieldFoundException extends Exception {
    
    private static final long serialVersionUID = -7933353858652825605L;
    
    public FieldFoundException() { super("This field already exists."); }
    
}

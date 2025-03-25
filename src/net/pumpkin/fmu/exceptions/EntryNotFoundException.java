package net.pumpkin.fmu.exceptions;

public class EntryNotFoundException extends Exception {
    
    private static final long serialVersionUID = 8114461490581976287L;
    
    public EntryNotFoundException() { super("Could not find an entry using the given path."); }
    
}

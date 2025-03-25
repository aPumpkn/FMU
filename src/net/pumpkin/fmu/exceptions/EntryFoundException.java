package net.pumpkin.fmu.exceptions;

public class EntryFoundException extends Exception {

    private static final long serialVersionUID = 1447793410727418417L;
    
    public EntryFoundException() { super("This entry already exists."); }
    
}

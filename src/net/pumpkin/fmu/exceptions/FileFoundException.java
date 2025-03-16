package net.pumpkin.fmu.exceptions;

public class FileFoundException extends Exception {

    private static final long serialVersionUID = 4713828959795745107L;

    public FileFoundException() { super("Cannot use an existing file's path."); }
    
}

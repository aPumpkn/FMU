package net.pumpkin.fmu.exceptions;

public class FolderFoundException extends Exception {
    
    private static final long serialVersionUID = -6216299353654355841L;
    
    public FolderFoundException() { super("This folder already exists."); }
    
}

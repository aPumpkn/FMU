package net.pumpkin.fmu.exceptions;

public class FolderNotFoundException extends Exception {
    
    private static final long serialVersionUID = -7744560212034290599L;
    
    public FolderNotFoundException() { super("This folder does not exist."); }
    
}

package net.pumpkin.fmu.exceptions;

public class DirectoryNotFoundException extends Exception {

    private static final long serialVersionUID = -3221885401326591786L;
    
    public DirectoryNotFoundException() { super("Given path is not a directory or does not exist."); }
    
}

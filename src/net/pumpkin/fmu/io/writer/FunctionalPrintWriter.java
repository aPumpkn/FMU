package net.pumpkin.fmu.io.writer;

import java.io.PrintWriter;

@FunctionalInterface
public interface FunctionalPrintWriter {
    
    void write(PrintWriter writer);
    
}

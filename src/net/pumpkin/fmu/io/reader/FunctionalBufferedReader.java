package net.pumpkin.fmu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface FunctionalBufferedReader {
    
    void read(BufferedReader reader) throws IOException, SecurityException;
    
}

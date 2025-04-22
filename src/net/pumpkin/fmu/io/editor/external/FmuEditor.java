package net.pumpkin.fmu.io.editor.external;

import java.util.List;
import java.util.Map;

import net.pumpkin.fmu.io.editor.DataEditor;
import net.pumpkin.fmu.io.editor.DataType;

public class FmuEditor implements DataEditor {

    @Override
    public <T> T get(String path, DataType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getEntries(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getFields(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean has(String path) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasField(String path) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DataEditor add(String path, Object value) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor rename(String path, String name) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor edit(String path, Object value) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor remove(String path) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor addField(String path) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor addFields(String path) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor renameField(String path, String name) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public DataEditor removeField(String path) {
        // TODO Auto-generated method stub
        return this;
    }
    
    @Override
    public void complete() {
        // TODO Auto-generated method stub
        return;
    }
    
}

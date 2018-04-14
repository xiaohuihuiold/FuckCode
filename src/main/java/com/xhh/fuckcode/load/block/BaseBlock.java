package com.xhh.fuckcode.load.block;

import com.xhh.fuckcode.load.line.BaseLine;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseBlock extends BaseLine {

    private ArrayList<BaseLine> baseLines = new ArrayList<>();
    private HashMap<String, Object> fields = new HashMap<>();

    public BaseBlock() {

    }

    public BaseBlock(int type) {
        super(type);
    }

    public void addLine(BaseLine baseLine) {
        baseLines.add(baseLine);
    }

    public BaseLine getLine(int index) {
        return baseLines.get(index);
    }

    public Object getField(String name) {
        return fields.get(name);
    }

    public void addField(String name, Object field) {
        fields.put(name, field);
    }

    public void removeField(String name) {
        fields.remove(name);
    }

    /****/

    public ArrayList<BaseLine> getBaseLines() {
        return baseLines;
    }

    public void setBaseLines(ArrayList<BaseLine> baseLines) {
        this.baseLines = baseLines;
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
    }
}

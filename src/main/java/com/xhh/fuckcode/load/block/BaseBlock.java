package com.xhh.fuckcode.load.block;

import com.xhh.fuckcode.load.line.BaseLine;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseBlock extends BaseLine {

    private int endSP;
    private int currSP;

    private ArrayList<BaseLine> baseLines = new ArrayList<>();
    private HashMap<String, Object> fields = new HashMap<>();

    public BaseBlock() {

    }

    public BaseBlock(int type) {
        super(type);
    }

    @Override
    public int run() {
        return 0;
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

    public int getEndSP() {
        return endSP;
    }

    public void setEndSP(int endSP) {
        this.endSP = endSP;
    }

    public int getCurrSP() {
        return currSP;
    }

    public void setCurrSP(int currSP) {
        this.currSP = currSP;
    }

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

    @Override
    public void clear() {
        super.clear();
        setCurrSP(0);
        fields.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BaseBlock bb = (BaseBlock) super.clone();
        bb.clear();
        ArrayList<BaseLine> bls = new ArrayList<>();
        for (BaseLine baseLine : baseLines) {
            bls.add((BaseLine) baseLine.clone());
        }
        bb.setBaseLines(bls);
        return bb;
    }
}

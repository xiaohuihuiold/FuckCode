package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;
import com.xhh.fuckcode.load.block.BaseBlock;

public class BaseLine implements Cloneable {

    private int type;
    private BaseBlock parent;
    private int startSP;

    public BaseLine() {

    }

    public BaseLine(int type) {
        this.type = type;
    }

    public int run() {

        return 0;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BaseBlock getParent() {
        return parent;
    }

    public void setParent(BaseBlock parent) {
        this.parent = parent;
    }

    public int getStartSP() {
        return startSP;
    }

    public void setStartSP(int startSP) {
        this.startSP = startSP;
    }

    public Object callMethod(String name, Object[] params) {
        return Runtime.getInstance().callMethod(name, params);
    }

    public Object exec(String name, Object[] objects) {
        return Runtime.getInstance().exec(name, objects);
    }

    public boolean addValue(String key, Object value) {
        if (getParent() != null) {
            getParent().addField(key, value);
            return true;
        }
        return false;
    }

    public boolean updateValue(String key, Object value) {
        BaseBlock parent = getParent();
        while (parent != null) {
            Object result = parent.getField(key);
            if (result == null) {
                parent = parent.getParent();
                continue;
            }
            break;
        }
        if (parent == null) {
            addValue(key, value);
            return false;
        } else {
            parent.addField(key, value);
            return true;
        }
    }

    public Object getValue(String key) {
        Object result = null;
        BaseBlock parent = getParent();
        while (parent != null) {
            result = parent.getField(key);
            if (result == null) {
                parent = parent.getParent();
                continue;
            }
            break;
        }
        return result;
    }

    public void clear() {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BaseLine baseLine = (BaseLine) super.clone();
        baseLine.clear();
        return baseLine;
    }
}

package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;
import com.xhh.fuckcode.load.block.BaseBlock;
import com.xhh.fuckcode.load.block.FunBlock;

public class BaseLine implements Cloneable {

    private int type;
    private int line;
    private int startSP;
    private BaseBlock parent;

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

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
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
        return Runtime.exec(name, objects);
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

    public Object getValue(Object key) {
        if (key == null) return null;
        if (!key.toString().startsWith("v")) {
            return key;
        }
        Object result = null;
        BaseBlock parent = getParent();
        while (parent != null) {
            result = parent.getField(key.toString());
            if (result == null) {
                parent = parent.getParent();
                continue;
            }
            break;
        }
        if (result == null) {
            return key;
        }
        return result;
    }

}

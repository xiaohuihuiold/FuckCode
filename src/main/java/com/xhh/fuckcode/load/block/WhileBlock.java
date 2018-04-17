package com.xhh.fuckcode.load.block;

import java.util.HashMap;

public class WhileBlock extends BaseBlock  implements Cloneable{

    private Object key;
    private Boolean logic = false;

    public WhileBlock() {

    }

    public WhileBlock(int type) {
        super(type);
    }

    public WhileBlock(Object key) {
        this.key = key;
    }

    @Override
    public int run() {
        int resu = loadObject();
        if (resu != 0) {
            return resu;
        }
        while (logic) {
            for (int i = 0; i < lineSize(); i++) {
                getLine(i).setParent(this);
                resu = getLine(i).run();
                if (resu == 2) break;
                if (resu != 0) {
                    return resu;
                }
            }
            if (resu == 2) break;
            resu = loadObject();
            if (resu != 0) {
                return resu;
            }
        }
        return 0;
    }

    public int loadObject() {
        if (key == null) {
            return 0;
        }
        Object object = getValue(key);
        if (object instanceof Boolean) {
            logic = (Boolean) object;
            return 0;
        }
        if (object.toString().startsWith("v")) {
            System.out.println("找不到参数:" + object + "(line:" + getLine() + ")");
            return 1;
        }
        return 0;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        BaseBlock baseBlock= (BaseBlock) super.clone();
        baseBlock.setFields(new HashMap<>());
        return baseBlock;
    }
}

package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.block.BaseBlock;

public class BaseLine {

    private int type;
    private BaseBlock parent;

    public BaseLine(){

    }

    public BaseLine(int type){
        this.type=type;
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
}

package com.xhh.fuckcode.load.block;

public class FunBlock extends BaseBlock {

    private String name;
    private Object[] params;
    private Object ret;

    public FunBlock() {

    }

    public FunBlock(int type) {
        super(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }
}

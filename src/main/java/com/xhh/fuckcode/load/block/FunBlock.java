package com.xhh.fuckcode.load.block;

import com.xhh.fuckcode.load.line.BaseLine;

import java.util.ArrayList;
import java.util.Arrays;

public class FunBlock extends BaseBlock {

    private String name;
    private Object[] params;
    private Object ret;

    public FunBlock() {

    }

    public FunBlock(int type) {
        super(type);
    }

    public FunBlock(FunBlock funBlock) {
        setParams(funBlock.getParams());
        setName(funBlock.getName());
        setRet(funBlock.getRet());
        setBaseLines(funBlock.getBaseLines());
        setEndSP(funBlock.getEndSP());
        setLine(funBlock.getLine());
        setStartSP(funBlock.getStartSP());
        setType(funBlock.getType());
    }

    @Override
    public int run() {
        for (int i = 0; i < lineSize(); i++) {
            getLine(i).setParent(this);
            int resu = getLine(i).run();
            if (resu != 0) {
                return resu;
            }
        }
        return 0;
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
        if (params == null) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            addField("v" + i, params[i]);
        }
    }

    public Object getReturn() {
        if (ret == null) return null;
        return getField(ret.toString());
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    public boolean isFun(String name, Object[] objects) {
        if (!this.name.equals(name)) {
            return false;
        }
        if ((objects == null && params != null) || (objects != null && params == null)) {
            return false;
        }

        if (objects == null && params == null) {
            return true;
        }
        if (objects.length != params.length) {
            return false;
        }/*
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].getClass() != params[i].getClass()) {
                return false;
            }
        }*/
        return true;
    }

}

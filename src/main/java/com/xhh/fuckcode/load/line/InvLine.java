package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

import java.util.Arrays;

public class InvLine extends BaseLine {

    private String name;
    private String retval;
    private Object[] objects;

    public InvLine() {

    }

    public InvLine(int type) {
        super(type);
    }

    public InvLine(String name, Object[] objects) {
        this.name = name;
        this.objects = objects;
    }

    public InvLine(String name, String retval, Object[] objects) {
        this.name = name;
        this.retval = retval;
        this.objects = objects;
    }

    @Override
    public int run() {
        int resu = loadObjects();
        if (resu != 0) {
            return resu;
        }
        Object ret = null;
        //System.out.println("执行:" + name + "方法");
        if (Runtime.getInstance().findMethod(name, objects) != null) {
            ret = Runtime.getInstance().callMethod(name, objects);
        } else {
            ret = exec(name, objects);
        }
        if (retval != null) {
            Object result = getValue(ret);
            updateValue(retval, result);
        }
        return 0;
    }

    private int loadObjects() {
        if (objects == null) {
            return 0;
        }
        for (int i = 0; i < objects.length; i++) {
            Object temp = objects[i];
            objects[i] = getValue(objects[i]);
            if (temp == objects[i]&&temp.toString().startsWith("v")) {
                System.out.println("找不到参数:"+temp);
                return 1;
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

    public String getRetval() {
        return retval;
    }

    public void setRetval(String retval) {
        this.retval = retval;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}

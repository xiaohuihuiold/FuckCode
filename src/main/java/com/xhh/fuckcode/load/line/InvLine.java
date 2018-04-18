package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InvLine extends BaseLine {

    private String name;
    private String retval;
    private Object obj;
    private Object[] objects;
    private Object[] values;

    public InvLine() {

    }

    public InvLine(int type) {
        super(type);
    }

    public InvLine(String name, Object[] objects) {
        this.name = name;
        this.objects = objects;
        values = new Object[objects.length];
    }

    public InvLine(String name, String retval, Object[] objects) {
        this.name = name;
        this.retval = retval;
        this.objects = objects;
        values = new Object[objects.length];
    }

    public InvLine(Object obj, String name, Object[] objects,boolean temp) {
        this.obj = obj;
        this.name = name;
        this.objects = objects;
        if(objects!=null)values = new Object[objects.length];
    }

    public InvLine(Object obj, String name, String retval, Object[] objects,boolean temp) {
        this.obj = obj;
        this.name = name;
        this.retval = retval;
        this.objects = objects;
        if(objects!=null)values = new Object[objects.length];
    }

    @Override
    public int run() {
        Object result = null;
        if (obj != null) {
            result = getValue(obj);
            if (result.toString().startsWith("v") || result == null) {
                System.out.println("找不到参数:" + result + "(line:" + getLine() + ")");
                return 1;
            }
        }
        int resu = loadObjects();
        if (resu != 0) {
            return resu;
        }
        Object ret = null;
        //System.out.println("执行:" + name + "方法");
        if (result != null) {
            try {
                Method method = null;
                if (values == null) {
                    method = result.getClass().getMethod(name);
                } else {
                    Class[] classes = new Class[values.length];
                    for (int i = 0; i < classes.length; i++) {
                        classes[i] = values[i].getClass();
                    }
                    method = result.getClass().getMethod(name, classes);
                }
                ret = method.invoke(result, values);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.out.println("执行方法出错:" + e.getMessage());
                return 1;
            }
        } else if (Runtime.getInstance().findMethod(name, values) != null) {
            ret = Runtime.getInstance().callMethod(name, values);
        } else {
            ret = exec(name, values);
        }
        if (retval != null) {
//            Object res = getValue(ret);
            updateValue(retval, ret);
        }
        return 0;
    }

    private int loadObjects() {
        if (objects == null) {
            return 0;
        }
        for (int i = 0; i < objects.length; i++) {
            Object temp = objects[i];
            values[i] = getValue(objects[i]);
            if (temp == values[i] && temp.toString().startsWith("v")) {
                System.out.println("找不到参数:" + temp);
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

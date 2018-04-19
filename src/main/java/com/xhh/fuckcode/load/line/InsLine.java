package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InsLine extends BaseLine {

    private String field;
    private Object value;
    private Object[] objects;
    private Object[] values;

    public InsLine() {

    }

    public InsLine(int type) {
        super(type);
    }

    public InsLine(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public InsLine(String field, Object value, Object[] objects) {
        this.field = field;
        this.value = value;
        this.objects = objects;
        if(objects!=null)values=new Object[objects.length];
    }

    @Override
    public int run() {
        Object result = getValue(value);
        if (!result.toString().startsWith("$") || result == null) {
            System.out.println("找不到参数:" + result + "(line:" + getLine() + ")");
            return 1;
        }
        String cls = result.toString();
        int resu = loadObjects();
        if (resu != 0) {
            return resu;
        }

        try {
            Class clazz = Class.forName(cls.substring(1, cls.length()));
            Constructor constructor = null;
            if (values == null) {
                constructor = clazz.getConstructor();
            } else {
                Class[] classes = new Class[values.length];
                for (int i = 0; i < classes.length; i++) {
                    classes[i] = values[i].getClass();
                }
                constructor = clazz.getConstructor(classes);
            }
            Object obj = constructor.newInstance(values);
            updateValue(field, obj);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("无法实例化:" + e.getMessage()+":(line:"+getLine()+")");
            return 1;
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
                System.out.println("找不到参数:" + temp+":(line:"+getLine()+")");
                return 1;
            }
        }
        return 0;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public Object[] getObjects() {
        return objects;
    }
}

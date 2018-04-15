package com.xhh.fuckcode.load;

import com.xhh.fuckcode.load.api.SystemApi;
import com.xhh.fuckcode.load.block.BaseBlock;
import com.xhh.fuckcode.load.block.FunBlock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Runtime {

    private static Runtime INSTANCE;

    private ArrayList<FunBlock> funBlocks = new ArrayList<>();

    private Runtime() {

    }

    public static Runtime getInstance() {
        if (INSTANCE == null) {
            synchronized (Runtime.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Runtime();
                }
            }
        }
        return INSTANCE;
    }

    public void run() {
        callMethod("main", null);
    }

    public Object callMethod(String name, Object[] params) {
        FunBlock fb = null;
        for (FunBlock funBlock : funBlocks) {
            if (funBlock.isFun(name, params)) {
                fb = funBlock;
                break;
            }
        }
        if (fb == null) {
            System.out.println("找不到方法:" + name);
            return null;
        }
        return callMethod(fb, params);
    }

    public Object callMethod(FunBlock funBlock, Object[] params) {
        try {
            FunBlock fb = (FunBlock) funBlock.clone();
            fb.setParams(params);
            fb.run();
            return fb.getRet();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("调用函数失败:" + e.getMessage());
            return null;
        }
    }

    public void setFunBlocks(ArrayList<FunBlock> funBlocks) {
        this.funBlocks = funBlocks;
    }

    public static Object exec(String methodName, Object[] objects) {
        Object result = null;
        Class[] classes = new Class[objects.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = objects[i].getClass();
        }
        try {
            Method method = SystemApi.class.getMethod(methodName, classes);
            method.invoke(null, objects);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("系统方法调用出错:" + e.getMessage());
        }
        return result;
    }

}

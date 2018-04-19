package com.xhh.fuckcode.load;

import com.xhh.fuckcode.load.api.SystemApi;
import com.xhh.fuckcode.load.block.BaseBlock;
import com.xhh.fuckcode.load.block.FunBlock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Runtime {

    private static Runtime INSTANCE;
    public static boolean DEBUG=false;

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
        long startTime = System.nanoTime();
        callMethod("main", null);
        long allTime = System.nanoTime() - startTime;
        System.out.println("程序关闭，耗时:" + (allTime / 1000000.0) + "ms(" + allTime + "ns)");
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
        FunBlock fb = new FunBlock(funBlock);
        fb.setParams(params);
        int resu = fb.run();
        if (resu != 0) {
            System.out.println("方法执行出错:" + funBlock.getName() + "(code:" + resu + ")");
            return null;
        }
        return fb.getReturn();
    }

    public FunBlock findMethod(String name, Object[] params) {
        for (FunBlock funBlock : funBlocks) {
            if (funBlock.isFun(name, params)) {
                return funBlock;
            }
        }
        return null;
    }

    public void setFunBlocks(ArrayList<FunBlock> funBlocks) {
        this.funBlocks = funBlocks;
    }

    public static Object exec(String methodName, Object[] objects) {
        Object result = null;
        Class[] classes = new Class[]{};
        if (objects != null) {
            classes = new Class[objects.length];
            for (int i = 0; i < classes.length; i++) {
                classes[i] = objects[i].getClass();
            }
        }
        try {
            Method method = SystemApi.class.getMethod(methodName, classes);
            result = method.invoke(null, objects);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("系统方法调用出错:" + e.getMessage());
        }
        return result;
    }

}

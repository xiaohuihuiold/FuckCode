package com.xhh.fuckcode.load;

import com.xhh.fuckcode.load.api.SystemApi;
import com.xhh.fuckcode.load.block.BaseBlock;
import com.xhh.fuckcode.load.block.FunBlock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Runtime {

    private static Runtime INSTANCE;
    public static boolean DEBUG = false;

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
        Method method = null;

        try {
            if (objects != null) {
                classes = getClasses(objects);
                method = getMethod(methodName, SystemApi.class, classes);
                result = method.invoke(null, objects);
            } else {
                method = SystemApi.class.getMethod(methodName);
                result = method.invoke(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("系统方法调用出错:" + e.getMessage());
        }
        return result;
    }

    public static Class[] getClasses(Object[] values) {
        Class[] classes = new Class[values.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = values[i].getClass();
            if (values[i] instanceof String) {
                if (((String) values[i]).startsWith("@")) {
                    String str = (String) values[i];
                    values[i] = str.substring(1, str.length());
                }
            }
        }
        return classes;
    }

    public static Method getMethod(String name, Class src, Class[] val) {
        Method method = null;
        for (Method met : src.getMethods()) {
            if (met.getName().equals(name) && met.getParameterCount() == val.length) {
                Class[] metclass = met.getParameterTypes();
                boolean isAss = true;
                for (int i = 0; i < metclass.length; i++) {
                    if (!metclass[i].isAssignableFrom(val[i])) {
                        if ((metclass[i] == long.class || metclass[i] == Long.class) && (val[i] == Integer.class || val[i] == Long.class)) {
                            continue;
                        } else if ((metclass[i] == Float.class || metclass[i] == float.class) && (val[i] == Float.class || val[i] == Integer.class || val[i] == Long.class)) {
                            continue;
                        } else if ((metclass[i] == Double.class || metclass[i] == double.class) && (val[i] == Double.class || val[i] == Integer.class || val[i] == Long.class)) {
                            continue;
                        }
                        isAss = false;
                        break;
                    }
                }
                if (!isAss) {
                    continue;
                }
                method = met;
                break;
            }
        }
        return method;
    }

}

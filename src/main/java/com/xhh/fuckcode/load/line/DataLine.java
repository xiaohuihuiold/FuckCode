package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

public class DataLine extends BaseLine {

    private DataLine.TYPE type;
    private String save;
    private Object left;
    private Object right;
    private Object[] objects = new Object[2];

    public enum TYPE {
        ADD,
        SUB,
        MUL,
        DIV,
        MOD
    }

    public DataLine() {

    }

    public DataLine(int type) {

    }

    public DataLine(String save, Object left, Object right) {
        this.save = save;
        this.left = left;
        this.right = right;
    }

    public DataLine(DataLine.TYPE type, String save, Object left, Object right) {
        this.save = save;
        this.left = left;
        this.right = right;
        this.type = type;
    }

    @Override
    public int run() {
        int resu = loadObjects();
        if (resu != 0) {
            return resu;
        }
        if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Integer) {
            int2int(((Integer) getObjects()[0]), ((Integer) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("int和int计算");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Double) {
            dou2dou(((Double) getObjects()[0]), ((Double) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("double和double计算");
            return 0;
        } else if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Double) {
            dou2dou(((Integer) getObjects()[0]), ((Double) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("int和double计算");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Integer) {
            dou2dou(((Double) getObjects()[0]), ((Integer) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("double和int计算");
            return 0;
        } else {
            String a = getObjects()[0].toString();
            String b = getObjects()[1].toString();
            if (a.startsWith("@")) {
                a = a.substring(1, a.length());
            }
            if (b.startsWith("@")) {
                b = b.substring(1, b.length());
            }
            updateValue(getSave(), "@" + a + b);
            if (Runtime.DEBUG) System.out.println("字符相加");
            return 0;
        }
    }

    public void int2int(int left, int right) {
        int temp = 0;
        switch (type) {
            case ADD:
                temp = left + right;
                break;
            case DIV:
                temp = left / right;
                break;
            case MUL:
                temp = left * right;
                break;
            case SUB:
                temp = left - right;
                break;
            case MOD:
                temp = left % right;
                break;
        }
        updateValue(getSave(), temp);
    }

    public void dou2dou(double left, double right) {
        double temp = 0;
        switch (type) {
            case ADD:
                temp = left + right;
                break;
            case DIV:
                temp = left / right;
                break;
            case MUL:
                temp = left * right;
                break;
            case SUB:
                temp = left - right;
                break;
            case MOD:
                temp = left % right;
                break;
        }
        updateValue(getSave(), temp);
    }

    public int loadObjects() {
        if (objects == null) {
            return 0;
        }
        Object result = getValue(left);
        if (result.toString().startsWith("v")) {
            System.out.println("找不到参数:" + result + "(line:" + getLine() + ")");
            return 1;
        }
        //System.out.println(result);
        objects[0] = result;
        result = getValue(right);
        if (result.toString().startsWith("v")) {
            System.out.println("找不到参数:" + result + "(line:" + getLine() + ")");
            return 1;
        }
        //System.out.println(result);
        objects[1] = result;
        return 0;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }

    public Object[] getObjects() {
        return objects;
    }
}

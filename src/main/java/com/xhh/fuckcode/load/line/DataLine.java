package com.xhh.fuckcode.load.line;

public class DataLine extends BaseLine {

    private String save;
    private Object left;
    private Object right;
    private Object[] objects = new Object[2];

    public DataLine() {

    }

    public DataLine(int type) {

    }

    public DataLine(String save, Object left, Object right) {
        this.save = save;
        this.left = left;
        this.right = right;
    }

    @Override
    public int run() {
        int resu = loadObjects();
        if (resu != 0) {
            return resu;
        }
        return 0;
    }

    private int loadObjects() {
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

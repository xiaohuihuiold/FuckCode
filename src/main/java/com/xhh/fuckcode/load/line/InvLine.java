package com.xhh.fuckcode.load.line;

public class InvLine extends BaseLine {

    private String name;
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

    @Override
    public int run() {
        loadObjects();
        System.out.println("执行:"+name+"方法");
        exec(name, objects);
        return 0;
    }

    private void loadObjects() {
        for (int i = 0; i < objects.length; i++) {
            Object result = getValue(objects[i].toString());
            if (result != null) {
                objects[i] = result;
            }
        }
    }
}

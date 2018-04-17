package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

public class MovLine extends BaseLine {

    private String field;
    private Object value;

    public MovLine() {

    }

    public MovLine(int type) {
        super(type);
    }

    public MovLine(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public int run() {
        Object result = getValue(value);
        if (result.toString().startsWith("v")) {
            System.out.println("找不到参数:" + result + "(line:" + getLine() + ")");
            return 1;
        }
        if(Runtime.DEBUG)System.out.println("复制" + value + "(" + result + ")" + "到" + field);
        updateValue(field, result);

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
}

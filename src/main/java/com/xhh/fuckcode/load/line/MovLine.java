package com.xhh.fuckcode.load.line;

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
        Object result = getValue(value.toString());
        if (result == null) {
            System.out.println("存放'" + value + "'到" + field);
            updateValue(field, value);
        } else {
            System.out.println("复制寄存器" + value + "(" + result + ")" + "到" + field);
            updateValue(field, result);
        }
        return 0;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

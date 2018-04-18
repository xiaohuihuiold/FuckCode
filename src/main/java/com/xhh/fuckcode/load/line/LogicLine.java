package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

public class LogicLine extends DataLine {

    private TYPE type;
    private boolean no;

    public enum TYPE {
        GREATER,
        LESS,
        EQUAL,
        GREATER_EQUAL,
        LESS_EQUAL,
        AND,
        OR
    }

    public LogicLine() {

    }

    public LogicLine(int type) {
        super(type);
    }

    public LogicLine(TYPE type, String save, Object left, Object right) {
        super(save, left, right);
        this.type = type;
    }

    public LogicLine(TYPE type, String save, Object left, Object right, boolean no) {
        super(save, left, right);
        this.type = type;
        this.no = no;
    }

    @Override
    public int run() {
        int resu = loadObjects();
        if (resu != 0) {
            return resu;
        }
        if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Integer) {
            double2double(((Integer) getObjects()[0]), ((Integer) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("int和int比较");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Double) {
            double2double(((Double) getObjects()[0]), ((Double) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("double和double比较");
            return 0;
        } else if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Double) {
            double2double(((Integer) getObjects()[0]), ((Double) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("int和double比较");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Integer) {
            double2double(((Double) getObjects()[0]), ((Integer) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("double和int比较");
            return 0;
        } else if (getObjects()[0] instanceof Boolean && getObjects()[1] instanceof Boolean) {
            boolean2boolean(((Boolean) getObjects()[0]), ((Boolean) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("double和int比较");
            return 0;
        } else {
            str2str(getObjects()[0].toString(), getObjects()[1].toString());
            if (Runtime.DEBUG) System.out.println("字符比较");
            return 0;
        }
    }

    public void double2double(double left, double right) {
        boolean temp = false;
        switch (type) {
            case LESS:
                temp = left < right;
                break;
            case EQUAL:
                temp = left == right;
                break;
            case GREATER:
                temp = left > right;
                break;
            case LESS_EQUAL:
                temp = left <= right;
                break;
            case GREATER_EQUAL:
                temp = left >= right;
                break;
        }
        updateValue(getSave(), no ? !temp : temp);
    }

    public void str2str(String left, String right) {
        updateValue(getSave(), no ? !left.equals(right) : left.equals(right));
    }

    public void boolean2boolean(boolean left, boolean right) {
        boolean temp = false;
        switch (type) {
            case AND:
                temp = left && right;
                break;
            case OR:
                temp = left || right;
                break;
        }
        updateValue(getSave(), no ? !temp : temp);
    }

}

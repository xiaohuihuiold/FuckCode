package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

public class MulLine extends DataLine {
    public MulLine() {

    }

    public MulLine(int type) {
        super(type);
    }

    public MulLine(String save, Object left, Object right) {
        super(save, left, right);
    }

    @Override
    public int run() {
        super.run();
        if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Integer) {
            updateValue(getSave(), ((Integer) getObjects()[0]) * ((Integer) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("int和int相乘");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Double) {
            updateValue(getSave(), ((Double) getObjects()[0]) * ((Double) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("double和double相乘");
            return 0;
        } else if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Double) {
            updateValue(getSave(), ((Integer) getObjects()[0]) * ((Double) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("int和double相乘");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Integer) {
            updateValue(getSave(), ((Double) getObjects()[0]) * ((Integer) getObjects()[1]));
            if (Runtime.DEBUG) System.out.println("double和int相乘");
            return 0;
        } else {
            if (Runtime.DEBUG) System.out.println("不是数字相乘");
            return 1;
        }
    }
}

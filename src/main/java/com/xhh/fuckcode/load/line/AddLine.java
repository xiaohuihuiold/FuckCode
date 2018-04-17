package com.xhh.fuckcode.load.line;

import com.xhh.fuckcode.load.Runtime;

public class AddLine extends DataLine {
    public AddLine() {

    }

    public AddLine(int type) {
        super(type);
    }

    public AddLine(String save, Object left, Object right) {
        super(save, left, right);
    }

    @Override
    public int run() {
        super.run();
        if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Integer) {
            updateValue(getSave(), ((Integer) getObjects()[0]) + ((Integer) getObjects()[1]));
            if(Runtime.DEBUG)System.out.println("int和int相加");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Double) {
            updateValue(getSave(), ((Double) getObjects()[0]) + ((Double) getObjects()[1]));
            if(Runtime.DEBUG)System.out.println("double和double相加");
            return 0;
        } else if (getObjects()[0] instanceof Integer && getObjects()[1] instanceof Double) {
            updateValue(getSave(), ((Integer) getObjects()[0]) + ((Double) getObjects()[1]));
            if(Runtime.DEBUG)System.out.println("int和double相加");
            return 0;
        } else if (getObjects()[0] instanceof Double && getObjects()[1] instanceof Integer) {
            updateValue(getSave(), ((Double) getObjects()[0]) + ((Integer) getObjects()[1]));
            if(Runtime.DEBUG)System.out.println("double和int相加");
            return 0;
        } else {
            updateValue(getSave(), getObjects()[0].toString() + getObjects()[1].toString());
            if(Runtime.DEBUG)System.out.println("字符相加");
            return 0;
        }
    }
}

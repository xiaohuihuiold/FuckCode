package com.xhh.fuckcode.load.util;

public class RunUtil {

    public static final int LINE = 0x01;
    public static final int BASE_VOID = 0x02;

    public static final int FUN = 0x11;
    public static final int FUN_END = 0x12;

    public static final int LOGIC = 0x21;
    public static final int LOGIC_END = 0x22;

    public static final int WHILE = 0x31;
    public static final int WHILE_END = 0x32;

    public static final int CTRL_MOV = 0x41;
    public static final int CTRL_LOGIC = 0x42;
    public static final int CTRL_DATA = 0x43;

    public static final int CTRL_INV = 0x51;
    public static final int CTRL_INS = 0x52;

    public static int getType(String type) {
        switch (type) {
            case KeyUtil.LINE:
                return LINE;
            case KeyUtil.BASE_VOID:
                return BASE_VOID;
            case KeyUtil.FUN:
                return FUN;
            case KeyUtil.FUN_END:
                return FUN_END;
            case KeyUtil.LOGIC:
                return LOGIC;
            case KeyUtil.LOGIC_END:
                return LOGIC_END;
            case KeyUtil.WHILE:
                return WHILE;
            case KeyUtil.WHILE_END:
                return WHILE_END;
            case KeyUtil.CTRL_MOV:
                return CTRL_MOV;
            case KeyUtil.CTRL_LOGIC:
                return CTRL_LOGIC;
            case KeyUtil.CTRL_DATA:
                return CTRL_DATA;
            case KeyUtil.CTRL_INV:
                return CTRL_INV;
            case KeyUtil.CTRL_INS:
                return CTRL_INS;
        }
        return 0;
    }
}

package com.xhh.fuckcode.load.util;

public class LoaderUtil {

    public static int getLineType(String[] strs) {
        if (strs == null || strs.length == 0) {
            return -1;
        }
        switch (strs[0]) {
            case KeyUtil.BASE_VOID:
                return RunUtil.BASE_VOID;
            case KeyUtil.FUN:
                return RunUtil.FUN;
            case KeyUtil.FUN_END:
                return RunUtil.FUN_END;
            case KeyUtil.FUN_RET:
                return RunUtil.FUN_RET;
            case KeyUtil.FUN_CALL:
                return RunUtil.FUN_CALL;
            case KeyUtil.CTL_MOV:
                return RunUtil.CTL_MOV;
            case KeyUtil.INV_:
                return RunUtil.INV;
            case KeyUtil.INV_STATIC:
                return RunUtil.INV_STATIC;
            default:
                return -1;
        }
    }

}

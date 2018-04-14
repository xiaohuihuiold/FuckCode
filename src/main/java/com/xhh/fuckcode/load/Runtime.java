package com.xhh.fuckcode.load;

import com.xhh.fuckcode.load.block.BaseBlock;
import com.xhh.fuckcode.load.block.FunBlock;

import java.util.ArrayList;

public class Runtime {

    private static Runtime INSTANCE;

    private ArrayList<FunBlock> funBlocks = new ArrayList<>();

    private Runtime() {

    }

    public static Runtime getInstance() {
        if (INSTANCE == null) {
            synchronized (Runtime.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Runtime();
                }
            }
        }
        return INSTANCE;
    }

}

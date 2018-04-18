package com.xhh.fuckcode;

import com.xhh.fuckcode.load.FuckedLoader;
import com.xhh.fuckcode.load.Runtime;
import com.xhh.fuckcode.load.block.FunBlock;
import com.xhh.fuckcode.load.block.LogicBlock;
import com.xhh.fuckcode.load.block.WhileBlock;
import com.xhh.fuckcode.load.line.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        FuckedLoader fuckedLoader = FuckedLoader.getInstance();
        Runtime runtime = Runtime.getInstance();
        try {
            fuckedLoader.load(Main.class.getResourceAsStream("/test.js"));
            runtime.setFunBlocks(test());
            runtime.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取资源失败:" + e.getMessage());
        }
    }

    public static ArrayList<FunBlock> test() {
        ArrayList<FunBlock> funBlocks = new ArrayList<>();

        FunBlock main = new FunBlock();
        main.setName("main");
        main.addLine(new MovLine("v0", "@小灰灰"));
        main.addLine(new MovLine("v1", "v0"));
        main.addLine(new InvLine("println", new Object[]{"v1"}));
        main.addLine(new InvLine("test", new Object[]{"v0"}));
        funBlocks.add(main);

        FunBlock test = new FunBlock();
        test.setParams(new Object[]{"v0"});
        test.setName("test");
        test.addLine(new InvLine("println", new Object[]{"v0"}));
        test.addLine(new MovLine("v3", 200));
        test.addLine(new MovLine("v4", 70.33));
        test.addLine(new DataLine(DataLine.TYPE.MUL, "v2", "v3", "v4"));
        test.addLine(new DataLine(DataLine.TYPE.ADD, "v1", "v3", "*"));
        test.addLine(new DataLine(DataLine.TYPE.ADD, "v1", "v1", "v4"));
        test.addLine(new DataLine(DataLine.TYPE.ADD, "v1", "v1", "@结果"));
        test.addLine(new DataLine(DataLine.TYPE.ADD, "v2", "v1", "v2"));
        test.addLine(new InvLine("println", new Object[]{"v2"}));

        test.addLine(new MovLine("v6", true));
        test.addLine(new MovLine("v5", 0));

        WhileBlock whileBlock = new WhileBlock("v6");
        whileBlock.addLine(new DataLine(DataLine.TYPE.ADD, "v2", "@v5的值:", "v5"));

        whileBlock.addLine(new LogicLine(LogicLine.TYPE.LESS_EQUAL, "v9", "v5", 50));
        LogicBlock logicBlock = new LogicBlock("v9");
        logicBlock.addLine(new InvLine("println", new Object[]{"v2"}));
        whileBlock.addLine(logicBlock);

        //whileBlock.addLine(new InvLine("println", new Object[]{"v2"}));
        whileBlock.addLine(new DataLine(DataLine.TYPE.ADD, "v5", "v5", 1));
        whileBlock.addLine(new LogicLine(LogicLine.TYPE.GREATER_EQUAL, "v6", 10, "v5"));

        test.addLine(whileBlock);

        funBlocks.add(test);

        return funBlocks;
    }
}

package com.xhh.fuckcode;

import com.xhh.fuckcode.load.FuckedLoader;
import com.xhh.fuckcode.load.Runtime;
import com.xhh.fuckcode.load.block.FunBlock;
import com.xhh.fuckcode.load.line.InvLine;
import com.xhh.fuckcode.load.line.MovLine;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FuckedLoader fuckedLoader = FuckedLoader.getInstance();
        Runtime runtime=Runtime.getInstance();
        try {
            fuckedLoader.load(Main.class.getResourceAsStream("/test.js"));
            runtime.setFunBlocks(test());
            runtime.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取资源失败:" + e.getMessage());
        }
    }

    public static ArrayList<FunBlock> test(){
        ArrayList<FunBlock> funBlocks=new ArrayList<>();

        FunBlock main=new FunBlock();
        main.setName("main");
        main.addLine(new MovLine("v0","小灰灰"));
        main.addLine(new MovLine("v1","v0"));
        main.addLine(new MovLine("v5","v1"));
        main.addLine(new MovLine("v2","v5"));
        main.addLine(new MovLine("v8","v2"));
        main.addLine(new InvLine("print",new Object[]{"v5"}));
        main.addLine(new InvLine("println",new Object[]{"v8"}));
        funBlocks.add(main);

        return funBlocks;
    }
}

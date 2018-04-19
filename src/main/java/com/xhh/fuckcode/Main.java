package com.xhh.fuckcode;

import com.xhh.fuckcode.load.FuckedLoader;
import com.xhh.fuckcode.load.Runtime;
import com.xhh.fuckcode.load.block.FunBlock;
import com.xhh.fuckcode.load.block.LogicBlock;
import com.xhh.fuckcode.load.block.WhileBlock;
import com.xhh.fuckcode.load.line.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FuckedLoader fuckedLoader = FuckedLoader.getInstance();
        Runtime runtime = Runtime.getInstance();
        try {
            fuckedLoader.load(Main.class.getResourceAsStream("/Fibonacci.js"));
            runtime.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取资源失败:" + e.getMessage());
        }
    }
}

package com.xhh.fuckcode;

import com.xhh.fuckcode.load.FuckedLoader;
import com.xhh.fuckcode.load.Runtime;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FuckedLoader fuckedLoader = FuckedLoader.getInstance();
        Runtime runtime=Runtime.getInstance();
        try {
            fuckedLoader.load(Main.class.getResourceAsStream("/test.js"));
            runtime.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取资源失败:" + e.getMessage());
        }
    }
}

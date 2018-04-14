package com.xhh.fuckcode;

import com.xhh.fuckcode.load.FuckedLoader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FuckedLoader fuckedLoader = FuckedLoader.getInstance();
        try {
            fuckedLoader.load(Main.class.getResourceAsStream("/test.js"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取资源失败:" + e.getMessage());
        }
    }
}

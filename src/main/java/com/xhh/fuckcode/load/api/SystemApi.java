package com.xhh.fuckcode.load.api;

public class SystemApi {
    public static void print(String text) {
        if (text.startsWith("@")) {
            text = text.substring(1, text.length());
        }
        System.out.print(text);
    }

    public static void print(Long text) {
        System.out.print(text);
    }

    public static void print(Double text) {
        System.out.print(text);
    }

    public static void print(Boolean text) {
        System.out.print(text);
    }

    public static void println(String text) {
        if (text.startsWith("@")) {
            text = text.substring(1, text.length());
        }
        System.out.println(text);
    }

    public static void println(Long text) {
        System.out.println(text);
    }

    public static void println(Double text) {
        System.out.println(text);
    }

    public static void println(Boolean text) {
        System.out.println(text);
    }
}

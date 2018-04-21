package com.xhh.fuckcode.load.api;

public class SystemApi {
    public static void print(String text) {
        if (text.startsWith("@")) {
            text = text.substring(1, text.length());
        }
        System.out.print(text);
    }

    public static void print(long text) {
        System.out.print(text);
    }

    public static void print(double text) {
        System.out.print(text);
    }

    public static void print(boolean text) {
        System.out.print(text);
    }

    public static void println(String text) {
        if (text.startsWith("@")) {
            text = text.substring(1, text.length());
        }
        System.out.println(text);
    }

    public static void println(long text) {
        System.out.println(text);
    }

    public static void println(double text) {
        System.out.println(text);
    }

    public static void println(boolean text) {
        System.out.println(text);
    }

    public static void sleep(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}

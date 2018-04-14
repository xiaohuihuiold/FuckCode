package com.xhh.fuckcode.load;


import java.io.*;
import java.util.ArrayList;

public class FuckedLoader {

    private static FuckedLoader INSTANCE;

    private ArrayList<String[]> lines = new ArrayList<>();

    private FuckedLoader() {

    }

    public static FuckedLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (FuckedLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FuckedLoader();
                }
            }
        }
        return INSTANCE;
    }

    public void load(String path) {
        File file = new File(path);
        if (!file.exists() && file.isDirectory()) {
            System.out.println("没有找到文件:" + path);
            return;
        }
        load(file);
    }

    public void load(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            System.out.println("没有找到文件:" + file.getPath());
            return;
        }
        try {
            load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("加载文件错误:" + e.getMessage());
        }
    }

    public void load(InputStream fileInputStream) throws IOException {
        long startTime = System.currentTimeMillis();
        if (fileInputStream == null) {
            System.out.println("输入流为空");
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String line = null;
        lines.clear();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            lines.add(line.trim().split(" "));
        }
        for(String[] strings:lines){
            for(String string:strings){
                System.out.print(string+" ");
            }
            System.out.println();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("解析完成，耗时:" + (endTime - startTime) + "ms");
    }

}

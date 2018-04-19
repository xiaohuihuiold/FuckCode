package com.xhh.fuckcode.load;


import com.xhh.fuckcode.load.block.BaseBlock;
import com.xhh.fuckcode.load.block.FunBlock;
import com.xhh.fuckcode.load.line.BaseLine;
import com.xhh.fuckcode.load.line.LogicLine;
import com.xhh.fuckcode.load.line.MovLine;
import com.xhh.fuckcode.load.util.KeyUtil;
import com.xhh.fuckcode.load.util.LoaderUtil;
import com.xhh.fuckcode.load.util.RunUtil;
import org.omg.SendingContext.RunTime;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FuckedLoader {

    private static FuckedLoader INSTANCE;

    private ArrayList<String[]> lines = new ArrayList<>();
    private ArrayList<FunBlock> funBlocks = new ArrayList<>();

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
        long startTime = System.nanoTime();
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
        for (String[] strings : lines) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        Runtime.getInstance().setFunBlocks(loadBlocks(0));
        long allTime = System.nanoTime() - startTime;
        System.out.println("解析完成，耗时:" + (allTime / 1000000.0) + "ms(" + allTime + "ns)");
    }

    public ArrayList<FunBlock> loadBlocks(int sp) {
        int lineIndex = 0;
        int deep = 0;
        FunBlock funBlock = null;
        for (int i = sp; i < lines.size(); i++) {
            String[] strs = lines.get(i);
            int type = RunUtil.getType(strs[0]);
            switch (type) {
                case RunUtil.LINE:
                    lineIndex = Integer.parseInt(strs[1]);
                    break;
                case RunUtil.BASE_VOID:
                    break;
                case RunUtil.FUN:
                    funBlock = new FunBlock();
                    funBlock.setLine(lineIndex);
                    funBlock.setName(strs[2]);
                    if (!strs[1].equals(KeyUtil.BASE_VOID)) {
                        funBlock.setRet(strs[1]);
                    }
                    if (!strs[3].equals(KeyUtil.BASE_VOID)) {
                        funBlock.setParams(strs[3].split(","));
                    }
                    break;
                case RunUtil.FUN_END:
                    funBlocks.add(funBlock);
                    break;
                case RunUtil.LOGIC:
                    deep++;
                    break;
                case RunUtil.LOGIC_END:
                    deep--;
                    break;
                case RunUtil.WHILE:
                    deep++;
                    break;
                case RunUtil.WHILE_END:
                    deep--;
                    break;
                case RunUtil.CTRL_MOV:
                    Object movRight = loadObject(strs[2]);
                    MovLine movLine = new MovLine(strs[1], movRight);
                    funBlock.addLine(movLine);
                    break;
                case RunUtil.CTRL_LOGIC:
                    LogicLine logicLine = null;
                    Object logicLeft = loadObject(strs[2]);
                    Object logicRight = loadObject(strs[4]);
                    LogicLine.TYPE logicType = LogicLine.TYPE.EQUAL;
                    String logicTemp = strs[3];
                    if (logicTemp.startsWith("!")) {
                        logicTemp = logicTemp.substring(1, logicTemp.length());
                    }
                    switch (logicTemp) {
                        case ">":
                            logicType = LogicLine.TYPE.GREATER;
                            break;
                        case "<":
                            logicType = LogicLine.TYPE.LESS;
                            break;
                        case "==":
                            logicType = LogicLine.TYPE.EQUAL;
                            break;
                        case ">=":
                            logicType = LogicLine.TYPE.GREATER_EQUAL;
                            break;
                        case "<=":
                            logicType = LogicLine.TYPE.LESS_EQUAL;
                            break;
                        case "&&":
                            logicType = LogicLine.TYPE.AND;
                            break;
                        case "||":
                            logicType = LogicLine.TYPE.OR;
                            break;
                    }
                    if (strs[3].startsWith("!")) {
                        logicLine = new LogicLine(logicType, strs[1], logicLeft, logicRight, true);
                    } else {
                        logicLine = new LogicLine(logicType, strs[1], logicLeft, logicRight);
                    }
                    funBlock.addLine(logicLine);
                    break;
                case RunUtil.CTRL_DATA:
                    break;
                case RunUtil.CTRL_INV:
                    break;
                case RunUtil.CTRL_INS:
                    break;
            }
        }
        System.out.println("Deep:" + deep);
        return funBlocks;
    }

    public Object loadObject(String value) {
        if (value.startsWith("v")) {
            return value;
        }
        if (value.startsWith("\"")) {
            return "@" + value.substring(1, value.length() - 1);
        }
        if (value.startsWith("$")) {
            return value;
        }
        try {
            int te = Integer.parseInt(value);
            return te;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

}

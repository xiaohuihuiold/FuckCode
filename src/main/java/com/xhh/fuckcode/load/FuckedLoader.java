package com.xhh.fuckcode.load;


import com.xhh.fuckcode.load.block.BaseBlock;
import com.xhh.fuckcode.load.block.FunBlock;
import com.xhh.fuckcode.load.block.LogicBlock;
import com.xhh.fuckcode.load.block.WhileBlock;
import com.xhh.fuckcode.load.line.*;
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
        /*for (String[] strings : lines) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }*/
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
                    String logTemp = strs[1];
                    LogicBlock logicBlock = null;
                    if (logTemp.startsWith("!")) {
                        logTemp=logTemp.substring(1,logTemp.length());
                        logicBlock=new LogicBlock(loadObject(logTemp),true);
                    } else {
                        logicBlock = new LogicBlock(logTemp);
                    }
                    logicBlock.setLine(lineIndex);
                    i=loadBlock(logicBlock, i + 1);
                    funBlock.addLine(logicBlock);
                    break;
                case RunUtil.LOGIC_END:
                    break;
                case RunUtil.WHILE:
                    String whileTemp = strs[1];
                    WhileBlock whileBlock = null;
                    if (whileTemp.startsWith("!")) {
                        whileTemp=whileTemp.substring(1,whileTemp.length());
                        whileBlock=new WhileBlock(loadObject(whileTemp),true);
                    } else {
                        whileBlock = new WhileBlock(whileTemp);
                    }
                    whileBlock.setLine(lineIndex);
                    i=loadBlock(whileBlock, i + 1);
                    funBlock.addLine(whileBlock);
                    break;
                case RunUtil.WHILE_END:
                    break;
                case RunUtil.CTRL_MOV:
                    Object movRight = loadObject(strs[2]);
                    MovLine movLine = new MovLine(strs[1], movRight);
                    movLine.setLine(lineIndex);
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
                    logicLine.setLine(lineIndex);
                    funBlock.addLine(logicLine);
                    break;
                case RunUtil.CTRL_DATA:
                    Object dataLeft = loadObject(strs[2]);
                    Object dataRight = loadObject(strs[4]);
                    DataLine.TYPE dataType = DataLine.TYPE.ADD;
                    String dataTemp = strs[3];
                    switch (dataTemp) {
                        case "+":
                            dataType = DataLine.TYPE.ADD;
                            break;
                        case "-":
                            dataType = DataLine.TYPE.SUB;
                            break;
                        case "*":
                            dataType = DataLine.TYPE.MUL;
                            break;
                        case "/":
                            dataType = DataLine.TYPE.DIV;
                            break;
                        case "%":
                            dataType = DataLine.TYPE.MOD;
                            break;
                    }
                    DataLine dataLine = new DataLine(dataType, strs[1], dataLeft, dataRight);
                    dataLine.setLine(lineIndex);
                    funBlock.addLine(dataLine);
                    break;
                case RunUtil.CTRL_INV:
                    if (strs.length == 2) {
                        InvLine invLine = new InvLine(strs[1], null);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 3) {
                        Object[] insRight = null;
                        if (!strs[2].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[2]);
                        }
                        InvLine invLine = new InvLine(strs[1], insRight);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 4) {
                        Object[] insRight = null;
                        if (!strs[3].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[3]);
                        }
                        InvLine invLine = new InvLine(strs[1], strs[2], insRight);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 5) {
                        Object[] insRight = null;
                        if (!strs[3].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[3]);
                        }
                        InvLine invLine = new InvLine(loadObject(strs[1]), strs[2], insRight, true);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 6) {
                        Object[] insRight = null;
                        if (!strs[4].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[4]);
                        }
                        InvLine invLine = new InvLine(loadObject(strs[1]), strs[2], strs[3], insRight, true);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    }
                    break;
                case RunUtil.CTRL_INS:
                    Object insLeft = loadObject(strs[2]);
                    Object[] insRight = null;
                    if (strs.length >= 4) {
                        insRight = loadObjects(strs[3]);
                    }
                    InsLine insLine = new InsLine(strs[1], insLeft, insRight);
                    insLine.setLine(lineIndex);
                    funBlock.addLine(insLine);
                    break;
            }
        }
        return funBlocks;
    }

    public int loadBlock(BaseBlock funBlock, int index) {
        int lineIndex = 0;
        int deep = 0;
        for (int i = index; i < lines.size(); i++) {
            String[] strs = lines.get(i);
            int type = RunUtil.getType(strs[0]);
            switch (type) {
                case RunUtil.LINE:
                    lineIndex = Integer.parseInt(strs[1]);
                    break;
                case RunUtil.BASE_VOID:
                    break;
                case RunUtil.LOGIC:
                    String logTemp = strs[1];
                    LogicBlock logicBlock = null;
                    if (logTemp.startsWith("!")) {
                        logTemp=logTemp.substring(1,logTemp.length());
                        logicBlock=new LogicBlock(loadObject(logTemp),true);
                    } else {
                        logicBlock = new LogicBlock(logTemp);
                    }
                    logicBlock.setLine(lineIndex);
                    i=loadBlock(logicBlock, i + 1);
                    funBlock.addLine(logicBlock);
                    break;
                case RunUtil.LOGIC_END:
                    return i;
                case RunUtil.WHILE:
                    String whileTemp = strs[1];
                    WhileBlock whileBlock = null;
                    if (whileTemp.startsWith("!")) {
                        whileTemp=whileTemp.substring(1,whileTemp.length());
                        whileBlock=new WhileBlock(loadObject(whileTemp),true);
                    } else {
                        whileBlock = new WhileBlock(whileTemp);
                    }
                    whileBlock.setLine(lineIndex);
                    i=loadBlock(whileBlock, i + 1);
                    funBlock.addLine(whileBlock);
                    break;
                case RunUtil.WHILE_END:
                    return i;
                case RunUtil.CTRL_MOV:
                    Object movRight = loadObject(strs[2]);
                    MovLine movLine = new MovLine(strs[1], movRight);
                    movLine.setLine(lineIndex);
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
                    logicLine.setLine(lineIndex);
                    funBlock.addLine(logicLine);
                    break;
                case RunUtil.CTRL_DATA:
                    Object dataLeft = loadObject(strs[2]);
                    Object dataRight = loadObject(strs[4]);
                    DataLine.TYPE dataType = DataLine.TYPE.ADD;
                    String dataTemp = strs[3];
                    switch (dataTemp) {
                        case "+":
                            dataType = DataLine.TYPE.ADD;
                            break;
                        case "-":
                            dataType = DataLine.TYPE.SUB;
                            break;
                        case "*":
                            dataType = DataLine.TYPE.MUL;
                            break;
                        case "/":
                            dataType = DataLine.TYPE.DIV;
                            break;
                        case "%":
                            dataType = DataLine.TYPE.MOD;
                            break;
                    }
                    DataLine dataLine = new DataLine(dataType, strs[1], dataLeft, dataRight);
                    dataLine.setLine(lineIndex);
                    funBlock.addLine(dataLine);
                    break;
                case RunUtil.CTRL_INV:
                    if (strs.length == 2) {
                        InvLine invLine = new InvLine(strs[1], null);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 3) {
                        Object[] insRight = null;
                        if (!strs[2].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[2]);
                        }
                        InvLine invLine = new InvLine(strs[1], insRight);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 4) {
                        Object[] insRight = null;
                        if (!strs[3].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[3]);
                        }
                        InvLine invLine = new InvLine(strs[1], strs[2], insRight);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 5) {
                        Object[] insRight = null;
                        if (!strs[3].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[3]);
                        }
                        InvLine invLine = new InvLine(loadObject(strs[1]), strs[2], insRight, true);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    } else if (strs.length == 6) {
                        Object[] insRight = null;
                        if (!strs[4].equals(KeyUtil.BASE_VOID)) {
                            insRight = loadObjects(strs[4]);
                        }
                        InvLine invLine = new InvLine(loadObject(strs[1]), strs[2], strs[3], insRight, true);
                        invLine.setLine(lineIndex);
                        funBlock.addLine(invLine);
                    }
                    break;
                case RunUtil.CTRL_INS:
                    Object insLeft = loadObject(strs[2]);
                    Object[] insRight = null;
                    if (strs.length >= 4) {
                        insRight = loadObjects(strs[3]);
                    }
                    InsLine insLine = new InsLine(strs[1], insLeft, insRight);
                    insLine.setLine(lineIndex);
                    funBlock.addLine(insLine);
                    break;
            }
        }
        return index;
    }

    public Object[] loadObjects(String params) {
        Object[] insRight = null;
        String insTemp = params;
        String[] objs = params.split(",");
        insRight = new Object[objs.length];
        for (int j = 0; j < objs.length; j++) {
            insRight[j] = loadObject(objs[j]);
        }
        return insRight;
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
            if (!value.contains(".")) {
                int te = Integer.parseInt(value);
                return te;
            } else {
                return Double.parseDouble(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

}

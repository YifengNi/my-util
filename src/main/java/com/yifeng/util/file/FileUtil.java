package com.yifeng.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author niyf
 * @Date 2021-8-11 11:22
 * @Description
 **/
public class FileUtil {

    public static List<String> readToStringList(String fileName) {
        return readToStringList(fileName, false);
    }

    public static List<String> readToStringList(String fileName, boolean isTrimmed) {
        BufferedReader br = null;
        List<String> list = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String str = null;
            while ((str = br.readLine()) != null) {
                if (isTrimmed) {
                    str = str.trim();
                }
                list.add(str);
            }
        } catch (Exception e) {
            System.out.println("读取文件[" + fileName + "]报错");
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void writeToFile(List<String> stringList, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String s : stringList) {
                bw.write(s);
                bw.newLine();
            }
            bw.flush();
            System.out.println("写入文件[" + fileName + "]成功");
        } catch (Exception e) {
            System.out.println("写入文件[" + fileName + "]报错");
            e.printStackTrace();
        }
    }

    public static String getWriteFileName(String fileName) {
        String[] strs = fileName.split("\\.");
        return strs[0] + "-new." + strs[1];
    }

}

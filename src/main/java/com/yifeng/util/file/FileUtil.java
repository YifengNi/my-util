package com.yifeng.util.file;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return getWriteFileName(fileName, null);
    }

    public static String getWriteFileName(String fileName, String suffix) {
        String[] strs = fileName.split("\\.");
        suffix = Objects.isNull(suffix) ? strs[1] : suffix;
        return strs[0] + "-new." + suffix;
    }

    /**
     * 写入Excel文件，只写入一个sheet
     * @param fileName
     * @param sheetName
     * @param modelClass
     * @param dataList
     * @param <T>
     */
    public static <T> void writeExcel4OneSheet(String fileName, String sheetName, Class<T> modelClass, List<T> dataList) {
        try (OutputStream out = new FileOutputStream(fileName)) {
            EasyExcel.write(out, modelClass).sheet(sheetName)
                    // 设置字段宽度为自动调整，不太精确
                    // .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(dataList);
            System.out.println("Excel文件[" + fileName + "]写入完成");
        } catch (Exception e) {
            System.out.println("写入Excel文件[" + fileName + "]报错");
            e.printStackTrace();
        }
    }

    public static <T> List<T> readExcel(String fileName, Class<T> modelClass) {
        List<T> resultList = new ArrayList<>();
        EasyExcel.read(fileName, modelClass, new PageReadListener<T>(resultList::addAll)).sheet().doRead();
        return resultList;
    }

}

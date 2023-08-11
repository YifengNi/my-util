package com.yifeng.study.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.fastjson.JSONObject;
import com.yifeng.study.dto.DynamicClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
            throw new RuntimeException(e);
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

    public static Set<String> readToStringSet(String fileName, boolean isTrimmed) {
        Set<String> set = new HashSet<>(128);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
            String str = null;
            while ((str = br.readLine()) != null) {
                if (isTrimmed) {
                    str = str.trim();
                }
                set.add(str);
            }
        } catch (Exception e) {
            System.out.println("读取文件[" + fileName + "]报错");
            e.printStackTrace();
        }
        return set;
    }

    public static String readToString(String fileName) {
        BufferedReader br = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
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
        return stringBuilder.toString();
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
        try (OutputStream out = Files.newOutputStream(Paths.get(fileName))) {
            if (null == modelClass) {
                EasyExcel.write(out).sheet(sheetName)
                        // 设置字段宽度为自动调整，不太精确
                        // .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .doWrite(dataList);
            } else {
                EasyExcel.write(out, modelClass).sheet(sheetName)
                        // 设置字段宽度为自动调整，不太精确
                        // .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .doWrite(dataList);
            }
            System.out.println("Excel文件[" + fileName + "]写入完成");
        } catch (Exception e) {
            System.out.println("写入Excel文件[" + fileName + "]报错");
            e.printStackTrace();
        }
    }

    /**
     * 写入Excel文件（动态表头），只写入一个sheet
     * @param fileName
     * @param sheetName
     * @param headNameList
     * @param dataList
     * @param <T>
     */
    public static <T> void writeExcel4OneSheetDynamically(String fileName, String sheetName, List<List<String>> headNameList
            , List<?> dataList) {
        try (OutputStream out = new FileOutputStream(fileName)) {
            ExcelWriter writer = EasyExcelFactory.write(out).build();
            // 动态添加表头，适用一些表头动态变化的场景
            WriteSheet sheet1 = new WriteSheet();
            sheet1.setSheetName(sheetName);
            sheet1.setSheetNo(0);
            // 创建一个表格，用于 Sheet 中使用
            WriteTable table = new WriteTable( );
            table.setTableNo(1);
            table.setHead(headNameList);
            // 写数据
            writer.write(dataList, sheet1, table);
            writer.finish();
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

    public static void main(String[] args) {
        // String fileName = "E:\\文档\\工作\\文件处理\\L1NSPGHB3NA910000修改后信息.xlsx";
        // String fileName = "E:\\文档\\工作\\文件处理\\0-6岁儿童健康管理花名册2023.xls";
        // String fileName = "E:\\文档\\工作\\文件处理\\0-6岁儿童健康管理花名册2023 - 副本.xlsx";
        // String fileName = "E:\\文档\\工作\\文件处理\\0-6岁儿童健康管理花名册2023 - 副本-1.xls";
        String fileName = "E:\\文档\\工作\\文件处理\\0-6岁儿童健康管理花名册2023 - 副本-1.xlsx";
        int sheetSize = 3;
        List<DynamicClass> resultList = new ArrayList<>();

        // EasyExcel.read(fileName, new DataReadListener<>(new PageReadListener<>(resultList::addAll))).headRowNumber(6).doReadAll();
        EasyExcel.read(fileName, new DataReadListener(resultList::addAll, sheetSize)).headRowNumber(6).doReadAll();
        writeExcel4OneSheet(FileUtil.getWriteFileName(fileName, "xlsx"), "合并", DynamicClass.class, resultList);

        // System.out.println(JSONObject.toJSON(resultList));
        System.out.println(JSONObject.toJSON(resultList.stream().limit(10).collect(Collectors.toList())));
        System.out.println("读取数量：" + resultList.size());
    }

}

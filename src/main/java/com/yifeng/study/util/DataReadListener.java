package com.yifeng.study.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.AbstractCell;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.yifeng.study.dto.DynamicClass;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author niyf
 * @Date 2023-08-10 14:37
 * @Description
 **/
@Slf4j
public class DataReadListener implements ReadListener<Object> {

    // private PageReadListener<Object> pageReadListener;

    private Consumer<List<DynamicClass>> consumer;

    private int maxColumnIndex;

    private int sheetSize;

    private List<Object> dataList;

    private List<List<Object>> allDataList = new ArrayList<>(8);

    // public DataReadListener(PageReadListener<Object> pageReadListener) {
    //     this.pageReadListener = pageReadListener;
    // }

    public DataReadListener(Consumer<List<DynamicClass>> consumer, int sheetSize) {
        this.consumer = consumer;
        this.sheetSize = sheetSize;
    }

    public int getMaxColumnIndex() {
        return maxColumnIndex;
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        // List<String> collect = headMap.values().stream()
        //         .map(item -> item.getStringValue() + "-" + item.getDataFormatData().getFormat() + "-" + item.getType())
        //         .collect(Collectors.toList());
        // log.info("collect：{}", JSONObject.toJSONString(collect));
        // log.info("invokeHead：{}", JSONObject.toJSONString(headMap));
        // log.info("invokeHead：{}", context.readSheetList());

        headMap.values().stream().map(AbstractCell::getColumnIndex).max(Integer::compareTo).ifPresent(item -> {
            // log.info("invokeHead-max：{}", item);
            maxColumnIndex = Math.max(maxColumnIndex, item);
        });
        // log.info("invokeHead：{}", headMap.size());

        dataList = new ArrayList<>(1024);
        ReadListener.super.invokeHead(headMap, context);
    }

    @Override
    public void invoke(Object t, AnalysisContext analysisContext) {
        dataList.add(t);

        // if (0 == analysisContext.readSheetHolder().getSheetNo()) {
        //     pageReadListener.invoke(Object, analysisContext);
        // }

    }

    /*@Override
    public void invoke(DynamicClass t, AnalysisContext analysisContext) {
        dataList.add(t);

        // if (0 == analysisContext.readSheetHolder().getSheetNo()) {
        //     pageReadListener.invoke(Object, analysisContext);
        // }

    }*/

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("doAfterAllAnalysed：数据解析完成，数量：{}", analysisContext.readRowHolder().getRowIndex() - 1);
        log.info("doAfterAllAnalysed：数据解析完成，sheet：{}", analysisContext.readSheetHolder().getSheetName());
        allDataList.add(dataList);
        // pageReadListener.doAfterAllAnalysed(analysisContext);

        if ((sheetSize - 1) == analysisContext.readSheetHolder().getSheetNo()) {
            this.doAfterAllFinished();
        }
    }

    private void printClassText() {
        String formatStr = "package com.yifeng.study.dto;\n" +
                "\n" +
                "import com.alibaba.excel.annotation.ExcelProperty;\n" +
                "import com.alibaba.fastjson.annotation.JSONField;\n" +
                "import lombok.Data;\n" +
                "\n" +
                "import java.util.Date;\n" +
                "\n" +
                "@Data\n" +
                "// @Accessors(chain = true)\n" +
                "public class DynamicClass { \n     %s \n }";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxColumnIndex; i++) {
            sb.append("@ExcelProperty(value = \"param_").append(i)
                    .append("\")\n    private String").append(" param_").append(i).append(";\n\n");
        }

        //groovy提供了一种将字符串文本代码直接转换成Java Class对象的功能
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        //里面的文本是Java代码,但是我们可以看到这是一个字符串我们可以直接生成对应的Class<?>对象,而不需要我们写一个.java文件
        String format = String.format(formatStr, sb.toString());
        System.out.println(format);
    }

    private void doAfterAllFinished() {
        this.printClassText();

        Map<String, JSONObject> baseDataMap = allDataList.get(0).stream().map(item -> {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(item));
            for (int i = 0; i < maxColumnIndex; i++) {
                jsonObject.putIfAbsent(String.valueOf(i), "");
            }
            return jsonObject;
        }).collect(Collectors.toMap(item -> String.valueOf(item.get("0")), Function.identity(), (v1, v2) -> v1));

        List<List<JSONObject>> handleDataList = allDataList.stream()
                .skip(1)
                .map(item -> item.stream()
                        .map(inItem -> {
                            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(inItem));
                            for (int i = 0; i < maxColumnIndex; i++) {
                                jsonObject.putIfAbsent(String.valueOf(i), "");
                            }
                            return jsonObject;
                        })
                        .collect(Collectors.toList())).collect(Collectors.toList());

        handleDataList.forEach(item -> {
            item.forEach(inItem -> {
                String key = String.valueOf(inItem.get("0"));
                JSONObject data = baseDataMap.get(key);
                if (null == data) {
                    baseDataMap.put(key, inItem);
                } else {
                    System.out.println("key = " + key);
                    for (String s : inItem.keySet()) {
                        Object o = inItem.get(s);
                        if (null != o) {
                            if ((o instanceof String) && StringUtils.isBlank((String) o)) {
                                continue;
                            }
                            data.put(s, o);
                        }
                    }
                }
            });
        });

        List<DynamicClass> resultList = baseDataMap.values().stream()
                .map(item -> {
                    try {
                        Class<DynamicClass> dynamicClassClass = DynamicClass.class;
                        DynamicClass obj = new DynamicClass();
                        for (String s : item.keySet()) {
                            Object o = item.get(s);
                            if (null != o) {
                                Field field = dynamicClassClass.getDeclaredField("param_" + s);
                                field.setAccessible(true);
                                field.set(obj, o);
                            }
                        }
                        return obj;
                    } catch (Exception e) {
                        log.error("doAfterAllFinished-dynamicClassClass err：{}", e.getMessage(), e);
                        return null;
                    }
                }).collect(Collectors.toList());
        this.consumer.accept(resultList);
    }

}

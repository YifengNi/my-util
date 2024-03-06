package com.yifeng.study.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author niyf
 * @date 2024-03-06 11:01
 * @description
 **/
public class GenerateExportFieldFromFrontEndCodeListener extends AnalysisEventListener<Object> {

    private Consumer<Map<String, Integer>> consumer;

    public GenerateExportFieldFromFrontEndCodeListener(Consumer<Map<String, Integer>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("headMap = " + JSONObject.toJSON(headMap));
        Map<String, Integer> headFiledMap = new HashMap<>();
        headMap.forEach((k, v) -> {
            headFiledMap.put(v, k);
        });
        this.consumer.accept(headFiledMap);
        super.invokeHeadMap(headMap, context);
    }
}

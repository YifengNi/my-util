package com.yifeng.study.util;

import org.junit.Test;

/**
 * @author niyf
 * @date 2024-03-06 10:37
 * @description
 **/

public class StringFormatUtilTest {


    @Test
    public void testGenerateExportFieldFromFrontEndCode() {
        String exportExcelFileName = "E:\\文档\\工作\\文件处理\\车辆库存列表导出.xlsx";
        String frontEndFieldFileName = "E:\\文档\\工作\\文件处理\\前端页面字段列表代码.json";
        String backEndExistedFieldFileName = "E:\\文档\\工作\\文件处理\\后端接口响应字段代码.txt";

        StringFormatUtil.generateExportFieldFromFrontEndCode(exportExcelFileName, frontEndFieldFileName, backEndExistedFieldFileName);
    }
}

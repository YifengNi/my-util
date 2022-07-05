package com.yifeng.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.converters.string.StringStringConverter;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author niyf
 * @Date 2022-7-5 18:23
 * @Description
 **/
@Data
@Accessors(chain = true)
public class Data2ExcelDTO {

    @ExcelProperty(value = "类型编码", converter = StringStringConverter.class)
    @ColumnWidth(20)
    private String lookupTypeCode;

    @ExcelProperty(value = "值编码（给开发用）", converter = StringStringConverter.class)
    @ColumnWidth(30)
    private String lookupValueCode;

    @ExcelProperty(value = "值名称（给机器、用户看）", converter = StringStringConverter.class)
    @ColumnWidth(40)
    private String lookupValueName;

    @ExcelProperty(value = "备注", converter = StringStringConverter.class)
    @ColumnWidth(50)
    private String remark;

}

package com.yifeng.util.string;

import com.yifeng.util.file.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @Author niyf
 * @Date 2021-8-11 11:18
 * @Description
 **/
public class StringFormatUtil {

    // private static final Map<String, String> PARAM_TYPE_MAP = new HashMap<>();
    //
    // static {
    //     PARAM_TYPE_MAP.put("string", "String");
    // }

    public static void main(String[] args) {
        // String fileName = "E:\\文档\\工作\\查询订单分配列表.txt";
        // trimString(fileName);

        String fileName = "E:\\文档\\工作\\订单信息主动推送接口.txt";
        generateCodeFromApiTxt(fileName);

    }

    public static void trimString(String fileName) {
        List<String> stringList = new ArrayList<>();
        for (String s : FileUtil.readToStringList(fileName)) {
            if (s.endsWith("\");")) {
                s = s.substring(0, s.length() - 3);
            }
            stringList.add(s);
        }

        FileUtil.writeToFile(stringList, FileUtil.getWriteFileName(fileName));
    }

    public static void generateCodeFromApiTxt(String fileName) {
        List<String> stringList = new ArrayList<>();
        for (String s : FileUtil.readToStringList(fileName)) {
            String[] ss = s.split("\\t");
            try {
                String param = StringUtil.underscoreToCamel(ss[0]);
                String remark = ss[1];
                String paramType = ss[2];

                // @ApiModelProperty("收车价格")
                // @JsonProperty("SECOND_RECEIVE_PRICE")
                // private String secondReceivePrice;

                StringBuilder sb = new StringBuilder();
                sb.append("\t@ApiModelProperty(\"").append(remark).append("\")\n\t")
                        .append("@JsonProperty(\"").append(ss[0]).append("\")\n\t")
                        .append("private ").append(paramType).append(" ").append(param).append(";\n");

                stringList.add(sb.toString());
            } catch (Exception e) {
                System.out.println(Arrays.asList(ss));
                System.out.println("报错：--> " + s + " --> " + e.getMessage());
            }
        }

        FileUtil.writeToFile(stringList, FileUtil.getWriteFileName(fileName));
    }

}

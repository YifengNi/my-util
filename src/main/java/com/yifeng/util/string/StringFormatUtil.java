package com.yifeng.util.string;

import com.yifeng.util.file.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String fileName = "E:\\文档\\工作\\数据库select语句字段.txt";
        generateCodeFromDbFieldTxt(fileName);

        // String fileName = "E:\\文档\\工作\\订单信息主动推送接口.txt";
        // generateCodeFromApiTxt(fileName);


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

    public static void generateCodeFromDbFieldTxt(String fileName) {
        List<String> stringList = new ArrayList<>();
        String regex = "^\\w+$";
        Pattern pattern = Pattern.compile(regex);// 匹配的模式

        for (String s : FileUtil.readToStringList(fileName, true)) {
            String global = s;
            boolean isSuccessful = false;

            try {
                String[] splitByComma = s.split(",");
                for (int i = splitByComma.length - 1; i >= 0; i--) {
                    s = splitByComma[i].trim();
                    String[] splitBySpace = s.split("\\s");

                    for (int j = splitBySpace.length - 1; j >= 0; j--) {
                        s = splitBySpace[j].trim();
                        String[] splitByPoint = s.split("\\.");

                        for (int k = splitByPoint.length - 1; k >= 0; k--) {
                            s = splitByPoint[k].trim();
                            s = StringUtil.underscoreToCamel(s);

                            Matcher matcher = pattern.matcher(s);
                            if (matcher.matches()) {

                                // @ApiModelProperty("收车价格")
                                // @JsonProperty("SECOND_RECEIVE_PRICE")
                                // private String secondReceivePrice;
                                StringBuilder sb = new StringBuilder();
                                sb.append("\t@ApiModelProperty(\"").append("请添加备注").append("\")\n\t")
                                        // .append("@JsonProperty(\"").append(ss[0]).append("\")\n\t")
                                        .append("private ").append("String").append(" ").append(s).append(";\n");

                                stringList.add(sb.toString());
                                isSuccessful = true;
                                break;
                            }
                        }

                        if (isSuccessful) {
                            break;
                        }
                    }

                    if (isSuccessful) {
                        break;
                    }
                }

                if (!isSuccessful) {
                    throw new RuntimeException("字符串不包含合法字段名");
                }

            } catch (Exception e) {
                System.out.println("报错：-->" + global + "<--> " + e.getMessage());
            }
        }

        FileUtil.writeToFile(stringList, FileUtil.getWriteFileName(fileName));
    }

}

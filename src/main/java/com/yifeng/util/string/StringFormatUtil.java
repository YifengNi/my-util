package com.yifeng.util.string;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrJoiner;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.yifeng.dto.Data2ExcelDTO;
import com.yifeng.dto.Excel2DataDTO;
import com.yifeng.util.file.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author niyf
 * @Date 2021-8-11 11:18
 * @Description
 **/
public class StringFormatUtil {

    public static final String FULL_DATE_FORMAT_STRING = "yyyyMMddHHmmssSSS";

    public static void main(String[] args) {
        StopWatch watch = StopWatch.createStarted();
        // String str = "SALE_ORDER_CODE\n" +
        //         "REPLACE_ORDER_CODE\n" +
        //         "CUST_NAME\n" +
        //         "CRED_CODE\n" +
        //         "CUST_CARD\n" +
        //         "CUST_PHONE\n" +
        //         "AUDIT_STATUS\n" +
        //         "CUST_TYPE";
        // System.out.println(batchUnderscoreToCamel(str));

        // String fileName = "D:\\tool\\i18n\\待插入SQL数据.txt";
        // String excludeWordFileName = "D:\\tool\\i18n\\数据库已存在的中文.txt";
        // translationSqlFilter(fileName, excludeWordFileName);

        // String fileName = "D:\\tool\\i18n\\待插入SQL数据.txt";
        // getChinese(fileName);

        // String fileName = "E:\\文档\\工作\\文件处理\\拉普达查询响应数据.txt";
        // generateExcelFromApiResp(fileName);

        String fileName = "E:\\文档\\工作\\文件处理\\车辆信息1228.xlsx";
        generateMesInsertSql(fileName);

        // String fileName = "E:\\文档\\工作\\文件处理\\lookup表插入sql语句字段.txt";
        // generateExcelFromSql(fileName);

        // String fileName = "E:\\文档\\工作\\文件处理\\数据库select语句字段.txt";
        // generateCodeFromDbFieldTxtV2(fileName, false);

        // String fileName = "E:\\文档\\工作\\文件处理\\数据库select语句字段.txt";
        // generateCodeFromDbFieldTxtV2(fileName, true);


        // String fileName = "E:\\文档\\工作\\文件处理\\数据库select语句字段.txt";
        // generateCodeFromDbFieldTxt(fileName);

        // String fileName = "E:\\文档\\工作\\文件处理\\订单信息主动推送接口.txt";
        // generateCodeFromApiTxt(fileName);

        // List<String> carSourceTypeList = Arrays.asList("1", "2", "3");
        // System.out.println("carSourceTypeList = " + carSourceTypeList);

        // String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FULL_DATE_FORMAT_STRING));
        // System.out.println("format = " + format);

        // Map<String, Object> map = new HashMap<>();
        // map.put("aa", 123);
        // map.put("bb", 34L);
        // map.put("cc", true);
        // map.put("dd", "56");
        //
        // String aa = MapUtils.getString(map, "aa");
        // String bb = MapUtils.getString(map, "bb");
        // String cc = MapUtils.getString(map, "cc");
        // Integer dd = MapUtils.getInteger(map, "dd");
        // Long aa2 = MapUtils.getLong(map, "aa");
        // Integer bb2 = MapUtils.getInteger(map, "bb");
        //
        // System.out.println("aa = " + aa);
        // System.out.println("bb = " + bb);
        // System.out.println("cc = " + cc);
        // System.out.println("dd = " + dd);
        // System.out.println("aa2 = " + aa2);
        // System.out.println("bb2 = " + bb2);
        System.out.printf("处理总耗时：%sms%n", watch.getTime());
    }

    /**
     * 排除已存在的中文翻译内容
     * @param fileName
     * @param excludeWordFileName
     */
    public static void translationSqlFilter(String fileName, String excludeWordFileName) {
        Set<String> excludeWordSet = FileUtil.readToStringSet(excludeWordFileName, true);
        List<String> list = FileUtil.readToStringList(fileName);
        List<String> resultList = new ArrayList<>(list.size());
        for (String item : list) {
            if (StringUtils.isBlank(item)) {
                resultList.add(item);
                continue;
            }
            String[] splitArr = item.split("'");
            if (splitArr.length < 2) {
                resultList.add(item);
                continue;
            }

            // 排除已存在的数据
            if (excludeWordSet.contains(splitArr[1])) {
                continue;
            }

            resultList.add(item);
        }
        System.out.println(String.join("\r\n", resultList));
    }

    /**
     * 获取待插入翻译SQL中的中文内容
     * @param fileName
     */
    public static void getChinese(String fileName) {
        List<String> list = FileUtil.readToStringList(fileName);
        List<String> resultList = new ArrayList<>(list.size());
        for (String item : list) {
            if (StringUtils.isBlank(item)) {
                continue;
            }
            String[] splitArr = item.split("'");
            if (splitArr.length < 2) {
                continue;
            }
            resultList.add(splitArr[1]);
        }
        System.out.println("'" + String.join("', '", resultList) + "'");
    }

    /**
     * 根据api文档（从Excel复制出来，字段间使用制表符/空格分隔）生成dto字段代码
     * @param fileName
     */
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

    /**
     * 根据数据库sql查询字段列表生成dto字段代码
     * @param fileName
     */
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

    /**
     * 根据数据库sql查询字段列表生成dto字段代码（包含注释）
     * generateCodeFromDbFieldTxt的升级版
     * @param fileName
     * @param showPositionFlag 是否展示ApiModelProperty注解的position属性
     */
    public static void generateCodeFromDbFieldTxtV2(String fileName, boolean showPositionFlag) {
        List<String> stringList = new ArrayList<>();
        // 字段
        String fieldRegex = "^\\w+$";
        Pattern fieldPattern = Pattern.compile(fieldRegex);// 匹配的模式
        // 注释
        String remarkRegex = "^/\\*+\\s*(.+)\\s*\\*+/$";
        Pattern remarkPattern = Pattern.compile(remarkRegex);// 匹配的模式

        String defaultRemark = "请添加备注";

        int idx = -1;
        // 设置position属性
        int fieldIndex = 1;
        List<String> fieldList = FileUtil.readToStringList(fileName, true);
        for (int x = 0; x < fieldList.size(); x++) {
            String s = fieldList.get(x);
            String global = s;
            boolean isSuccessful = false;

            try {
                // 根据逗号切割
                String[] splitByComma = s.split(",");

                // 当前行只是注释行，则有可能是上一个字段的注释，比如下面的情况
                // sto.FIRSTSHIPPING_ORDER_TIME,
                // /*发运指令时间*/
                if (splitByComma.length == 1 && idx >= 0) {
                    s = splitByComma[0].trim();
                    Matcher remarkMatcher = remarkPattern.matcher(s);
                    if (remarkMatcher.find()) {
                        String lastStr = stringList.get(idx).replaceFirst(defaultRemark, remarkMatcher.group(1).trim());
                        stringList.set(idx, lastStr);
                        continue;
                    }
                }

                boolean isRemarkNotFound = true;
                String remark = defaultRemark;
                // 从后往前遍历
                for (int i = splitByComma.length - 1; i >= 0; i--) {
                    s = splitByComma[i].trim();
                    // 判断是否是备注，是就获取该值，然后遍历下一个
                    if (isRemarkNotFound) {
                        Matcher remarkMatcher = remarkPattern.matcher(s);
                        if (remarkMatcher.find()) {
                            remark = remarkMatcher.group(1).trim();
                            isRemarkNotFound = false;
                            continue;
                        }
                    }

                    // 根据空格切割
                    String[] splitBySpace = s.split("\\s");

                    for (int j = splitBySpace.length - 1; j >= 0; j--) {
                        s = splitBySpace[j].trim();

                        // 最后一行要做特殊处理，因为字段名跟备注之间没有逗号分隔，比如：d.LICENSE_LOCATION_CITY  /*上牌城市*/
                        // 判断是否是备注，是就获取该值，然后遍历下一个
                        if (x == fieldList.size() - 1 && isRemarkNotFound) {
                            Matcher remarkMatcher = remarkPattern.matcher(s);
                            if (remarkMatcher.find()) {
                                remark = remarkMatcher.group(1).trim();
                                isRemarkNotFound = false;
                                continue;
                            }
                        }

                        // 根据点号切割
                        String[] splitByPoint = s.split("\\.");

                        for (int k = splitByPoint.length - 1; k >= 0; k--) {
                            s = splitByPoint[k].trim();
                            s = StringUtil.underscoreToCamel(s);

                            Matcher fieldMatcher = fieldPattern.matcher(s);
                            if (fieldMatcher.matches()) {

                                // @ApiModelProperty("收车价格")
                                // @JsonProperty("SECOND_RECEIVE_PRICE")
                                // private String secondReceivePrice;
                                StringBuilder sb = new StringBuilder();
                                sb.append("\t@ApiModelProperty(value = \"").append(remark);

                                // 按需输出position属性
                                if (showPositionFlag) {
                                    sb.append("\", position = ").append(fieldIndex);
                                }

                                sb.append("\")\n\t")
                                        // .append("@JsonProperty(\"").append(ss[0]).append("\")\n\t")
                                        .append("private ").append("String").append(" ").append(s).append(";\n");

                                stringList.add(sb.toString());
                                isSuccessful = true;
                                fieldIndex++;
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

                idx++;
            } catch (Exception e) {
                System.out.println("报错：-->" + global + "<--> " + e.getMessage());
            }
        }

        FileUtil.writeToFile(stringList, FileUtil.getWriteFileName(fileName));
    }

    /**
     * 根据api文档（从Excel复制出来，字段间使用制表符/空格分隔）生成dto字段代码
     * @param inTxt
     */
    public static String batchUnderscoreToCamel(String inTxt) {
        if (StringUtils.isBlank(inTxt)) {
            return inTxt;
        }
        StrJoiner joiner = new StrJoiner("\n");
        for (String s : inTxt.split("\\n")) {
            joiner.append(StringUtil.underscoreToCamel(s));
        }

        return joiner.toString();
    }

    /**
     * 根据lookup表插入sql语句字段生成Excel文档，以供产品根据文档去生产配置数据
     * @param fileName
     */
    public static void generateExcelFromSql(String fileName) {
        List<String> dataList = FileUtil.readToStringList(fileName, true);
        List<Data2ExcelDTO> resultList = dataList.stream()
                .filter(item -> item.startsWith("VALUES"))
                .map(item -> item.replaceFirst("VALUES\\(", "")
                        .replaceFirst("\\);", "").replaceAll("'", ""))
                .map(item -> item.split(", "))
                .map(item -> {
                    // 可以修改这里进行业务变更
                    Data2ExcelDTO data = new Data2ExcelDTO();
                    data.setLookupTypeCode(item[0]).setLookupValueCode(item[1]).setLookupValueName(item[2])
                            .setRemark(item[10]);
                    return data;
                }).collect(Collectors.toList());
        String[] strs = fileName.split("\\.");
        String newFileName = strs[0] + "-new.xlsx";
        FileUtil.writeExcel4OneSheet(newFileName, "待配置参数", Data2ExcelDTO.class, resultList);
    }

    /**
     * 根据api接口响应结果（拉普达查询响应数据）生成Excel文档
     * @param fileName
     */
    public static void generateExcelFromApiResp(String fileName) {
        String data = FileUtil.readToString(fileName);
        if (StringUtils.isBlank(data)) {
            throw new RuntimeException("接口返回无数据，不进行处理");
        }
        JSONObject fileJson = JSONObject.parseObject(data);
        // 字段列表
        List<String> columnList = fileJson.getJSONArray("columns").toJavaList(JSONObject.class).stream()
                .map(item -> item.getString("title"))
                .collect(Collectors.toList());
        // 数据列表
        List<List<String>> resultList = fileJson.getJSONArray("results").toJavaList(JSONObject.class).stream()
                .map(item -> columnList.stream().map(item::getString).collect(Collectors.toList()))
                .collect(Collectors.toList());
        // Excel文件表头列表，内层list有多少个元素就表示表头有几层，此处只需要一层表头所以只有单个元素
        List<List<String>> headNameList = columnList.stream().map(Collections::singletonList).collect(Collectors.toList());
        String[] strs = fileName.split("\\.");
        String newFileName = strs[0] + "-new.xlsx";
        FileUtil.writeExcel4OneSheetDynamically(newFileName, "导出查询数据", headNameList, resultList);
    }

    /**
     * 根据Excel文档生成肇庆MES车辆信息的消息表插入语句
     * @param fileName
     */
    public static void generateMesInsertSql(String fileName) {
        List<Excel2DataDTO> dataList = FileUtil.readExcel(fileName, Excel2DataDTO.class);
        if (CollectionUtil.isEmpty(dataList)) {
            throw new RuntimeException("Excel文档无数据，不进行处理");
        }

        List<String> stringList = new ArrayList<>(dataList.size());
        String rex = "insert\n" +
                "\tinto\n" +
                "\tbiz_message_record\n" +
                "(\n" +
                "\t\tapi_key,\n" +
                "\t\tmessage,\n" +
                "\t\tout_message,\n" +
                "\t\tsource,\n" +
                "\t\t`type`,\n" +
                "\t\tstatus,\n" +
                "\t\tis_async,\n" +
                "\t\tis_retry,\n" +
                "\t\tretry_count,\n" +
                "\t\tbiz_code\n" +
                "\t)\n" +
                "values(\n" +
                "\t'%s',\n" +
                "\t'%s',\n" +
                "\t'',\n" +
                "\t'inner',\n" +
                "\t'ZQMES_OAS_ZQMES_OAS_R_001',\n" +
                "\t0,\n" +
                "\t1,\n" +
                "\t1,\n" +
                "\t0,\n" +
                "\t'CarInfo-%s'\n" +
                ");\n\n";

        for (Excel2DataDTO item : dataList) {
            Map<String, Object> msgMap = new HashMap<>(8);
            String key = IdUtil.randomUUID();
            msgMap.put("key", key);
            msgMap.put("event", "ZQMES_OAS_ZQMES_OAS_R_001");

            Map<String, Object> HEADER = new HashMap<>(8);
            HEADER.put("BUSID", "ZQMES0300");
            HEADER.put("SENDER", "ZQMES-MANUAL");
            HEADER.put("RECID", IdUtil.randomUUID());
            HEADER.put("DTSEND", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            HEADER.put("RECEIVER", "OAS");

            String batchNo = item.getBatchNo();
            if (StringUtils.isNotBlank(batchNo)) {
                // 批次号通常为10为数字，前面补0
                batchNo = StringUtils.leftPad(batchNo, 10, "0");
                item.setBatchNo(batchNo);
            }
            String vin = item.getVin();

            Map<String, Object> bodyMap = new HashMap<>(8);
            // bodyMap.put("HEADER", JSONObject.toJSONString(HEADER));
            // bodyMap.put("OASMODEL", JSONObject.toJSONString(OASMODEL));

            bodyMap.put("HEADER", HEADER);
            bodyMap.put("OASMODEL", JSONObject.toJSON(item));

            // msgMap.put("body", JSONObject.toJSONString(bodyMap));
            msgMap.put("body", bodyMap);

            String sql = String.format(rex, key, JSONObject.toJSONString(msgMap), vin);
            stringList.add(sql);
        }

        FileUtil.writeToFile(stringList, FileUtil.getWriteFileName(fileName, "sql"));
    }
}

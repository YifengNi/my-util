package com.yifeng.util.string;

import com.google.common.base.CaseFormat;
import com.yifeng.dto.Data2ExcelDTO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @Author niyf
 * @Date 2021-9-8 14:31
 * @Description
 **/
public class StringUtil {

    /**
     * logger名称长度限制
     */
    private static final int LOGGER_NAME_LEN_LIMIT = 40;

    /**
     * 驼峰转大写下划线
     * @param source
     * @return
     */
    public static String camelToUnderscore(String source) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, source);
    }

    /**
     * 大写下划线转驼峰
     * @param source
     * @return
     */
    public static String underscoreToCamel(String source) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, source);
    }

    /**
     * 填充对象的属性
     * @param obj
     * @param defaultStringValue
     */
    public static void fillObjField(Object obj, String defaultStringValue) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                String typeName = field.getType().getName();
                if ("java.lang.String".equals(typeName)) {
                    if (Objects.isNull(field.get(obj))) {
                        field.set(obj, defaultStringValue);
                    }
                }
            } catch (Exception e) {
                System.out.println("fillObjField err: " + e.getMessage());
            }
        }
    }

    /**
     * 获取日志打印的logger名称
     * @param fullLoggerName
     * @return
     */
    public static String getFormatLoggerName(String fullLoggerName) {
        if (StringUtils.isBlank(fullLoggerName)) {
            return fullLoggerName;
        }

        fullLoggerName = fullLoggerName.replaceAll("#", ".");

        if (fullLoggerName.length() <= LOGGER_NAME_LEN_LIMIT) {
            return fullLoggerName;
        }

        if (!fullLoggerName.contains(".")) {
            return fullLoggerName.substring(fullLoggerName.length() - LOGGER_NAME_LEN_LIMIT);
        }

        String[] itemArr = fullLoggerName.split("\\.");
        int idx = 0;
        String replaced = "";
        for (int i = 0; i < itemArr.length; i++) {
            String item = itemArr[i];
            if (fullLoggerName.length() >= LOGGER_NAME_LEN_LIMIT) {
                replaced = item.substring(0, 1);
                fullLoggerName = fullLoggerName.replaceFirst(item, replaced);
                idx++;
            } else {
                break;
            }
        }

        if (idx == itemArr.length) {
            StringBuilder builder = new StringBuilder(fullLoggerName);
            int lastIdx = fullLoggerName.lastIndexOf(replaced);
            builder.replace(lastIdx, lastIdx + 1, itemArr[idx - 1]);
            if (builder.length() > LOGGER_NAME_LEN_LIMIT) {
                builder.delete(0, builder.length() - LOGGER_NAME_LEN_LIMIT);
                if (builder.charAt(0) == '.') {
                    builder.delete(0, 1);
                }
            }
            fullLoggerName = builder.toString();
        }

        return fullLoggerName;
    }

    public static void main(String[] args) {
        // String s1 = "testData";
        // String s2 = "TEST__DATA";
        //
        // System.out.println(camelToUnderscore(s1));
        // System.out.println(camelToUnderscore(s2));
        // System.out.println(underscoreToCamel(s1));
        // System.out.println(underscoreToCamel(s2));

        // String name = "com.xiaopeng.imperial.oms.mapper.oms.OmsNoticeMapper#saveNotice";
        // System.out.println("getFormatLoggerName(name) = " + getFormatLoggerName(name));

        Data2ExcelDTO obj = new Data2ExcelDTO();
        fillObjField(obj, "N/A");
        System.out.println("obj = " + obj);

        // DateTime dateTime = DateUtil.beginOfWeek(new Date());
        // System.out.println("dateTime = " + dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        // DateTime dateTime1 = DateUtil.endOfWeek(new Date());
        // System.out.println("dateTime1 = " + dateTime1.toString("yyyy-MM-dd HH:mm:ss"));
    }

}

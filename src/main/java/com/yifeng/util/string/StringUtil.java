package com.yifeng.util.string;

import com.google.common.base.CaseFormat;

/**
 * @Author niyf
 * @Date 2021-9-8 14:31
 * @Description
 **/
public class StringUtil {

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

    public static void main(String[] args) {
        String s1 = "testData";
        String s2 = "TEST__DATA";

        System.out.println(camelToUnderscore(s1));
        System.out.println(camelToUnderscore(s2));
        System.out.println(underscoreToCamel(s1));
        System.out.println(underscoreToCamel(s2));
    }

}

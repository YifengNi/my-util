package com.yifeng.util.controller;

import com.google.common.base.CaseFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author niyf
 * @Date 2021-9-16 18:37
 * @Description 在线工具类
 **/
@RestController
@RequestMapping("/admin/util")
@Api(value = "在线工具类的相关接口", tags = "在线工具类的相关接口")
public class UtilController {

    @GetMapping("/stringConvert")
    @ApiOperation("字符串转换")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "camelToUnderscore", value = "驼峰转大写下划线", required = false, dataType = "String"
                    , paramType = "query", defaultValue = "testWord"),
            @ApiImplicitParam(name = "underscoreToCamel", value = "大写下划线转驼峰", required = false, dataType = "String"
                    , paramType = "query", defaultValue = "TEST_WORD"),
    })
    public Map<String, Object> queryCarConfigSeries(@RequestParam(required = false) String camelToUnderscore
            , @RequestParam(required = false) String underscoreToCamel) {
        Map<String, Object> data = new HashMap<>();

        if (StringUtils.isNotBlank(camelToUnderscore)) {
            data.put(camelToUnderscore, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelToUnderscore));
        }
        if (StringUtils.isNotBlank(underscoreToCamel)) {
            data.put(underscoreToCamel, CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underscoreToCamel));
        }

        return data;
    }
}

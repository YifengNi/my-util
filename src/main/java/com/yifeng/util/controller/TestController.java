package com.yifeng.util.controller;

import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author niyf
 * @Date 2021-10-14 14:58
 * @Description
 **/
@RestController
@RequestMapping("/test")
@Api(value = "JMeter测试的相关接口", tags = "JMeter测试的相关接口")
public class TestController {

    private static String globalToken = "";

    @GetMapping("/getCsrfToken")
    @ApiOperation("获取CSRF token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数", required = false, dataType = "String"
                    , paramType = "query", defaultValue = "testWord"),
    })
    public Map<String, Object> getCsrfToken(@RequestParam(required = false, defaultValue = "testWord") String param
            , HttpServletResponse response) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("入参", param);
        data.put("响应数据", getDateTimeStr());

        String token = IdUtil.objectId();
        globalToken = token;
        data.put("nextInputParam", param + "<->" + token);
        response.addHeader("x-csrf-token", token);


        System.out.println("出参data = " + data);
        return data;
    }

    @PostMapping("/getData")
    @ApiOperation("获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reqParamMap", value = "body参数", required = false, dataType = "Map"
                    , paramType = "body", defaultValue = "{}"),
    })
    public Map<String, Object> getData(@RequestBody Map<String, Object> reqParamMap
            , @RequestHeader(value = "x-csrf-token") String csrfToken) {
        Map<String, Object> data = new HashMap<>(4);

        data.put("body入参", reqParamMap);
        data.put("csrfToken", csrfToken);
        data.put("isAuth", globalToken.equals(csrfToken));
        data.put("响应数据", getDateTimeStr());

        System.out.println("出参data = " + data);
        return data;
    }

    private String getDateTimeStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}

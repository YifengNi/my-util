package com.yifeng.study.controller;


import com.yifeng.study.dao.TStudentInfoDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author mybatisplus自动生成
 * @since 2023-05-08
 */
@RestController
@RequestMapping("/tStudentInfo")
@Api(value = "学生相关接口", tags = "学生相关接口")
public class TStudentInfoController {

    @Autowired
    private TStudentInfoDao tStudentInfoDao;


    @GetMapping("/getStudentList")
    @ApiOperation("获取学生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名字", required = false, defaultValue = "张三", dataType = "String"
                    , paramType = "query"),
    })
    public Object getStudentList(@RequestParam(required = false) String name) {
    // public Object getStudentList() {
        return tStudentInfoDao.getStudentList(name);
    }
}


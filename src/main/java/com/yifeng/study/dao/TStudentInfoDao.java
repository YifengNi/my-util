package com.yifeng.study.dao;

import com.baomidou.mybatisplus.service.IService;
import com.yifeng.study.entity.TStudentInfo;

import java.util.List;

/**
 * <p>
 * 学生信息表 服务类
 * </p>
 *
 * @author mybatisplus自动生成
 * @since 2023-05-08
 */
public interface TStudentInfoDao extends IService<TStudentInfo> {

    List<TStudentInfo> getStudentList(String name);
}

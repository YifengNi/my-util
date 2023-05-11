package com.yifeng.study.dao.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yifeng.study.dao.TStudentInfoDao;
import com.yifeng.study.entity.TStudentInfo;
import com.yifeng.study.mapper.TStudentInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author mybatisplus自动生成
 * @since 2023-05-08
 */
@Service
public class TStudentInfoDaoImpl extends ServiceImpl<TStudentInfoMapper, TStudentInfo> implements TStudentInfoDao {

    @Override
    public List<TStudentInfo> getStudentList(String name) {
        Wrapper<TStudentInfo> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        return this.selectList(wrapper);
    }

    @Override
    // @Transactional(value = "transactionManager", rollbackFor = Exception.class, noRollbackFor = RuntimeException.class)
    @Async
    public boolean insertTestNoRollbackFor() {
        TStudentInfo info = new TStudentInfo();
        info.setName("lisi");
        info.setAddress("广州市天河区新塘街道");
        this.insert(info);
        throw new RuntimeException();
    }

    @Override
    @Transactional(value = "transactionManager", rollbackFor = RuntimeException.class, noRollbackFor = Exception.class)
    public boolean insertTestRollbackFor() {
        TStudentInfo info = new TStudentInfo();
        info.setName("wangwu");
        info.setAddress("广州市天河区长兴街道");
        this.insert(info);
        throw new RuntimeException();
    }
}

package com.yifeng.study.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学生信息表
 * </p>
 *
 * @author mybatisplus自动生成
 * @since 2023-05-08
 */
@Data
@Accessors(chain = true)
@TableName("t_student_info")
public class TStudentInfo extends Model<TStudentInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 是否删除：0：未删除；1：删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

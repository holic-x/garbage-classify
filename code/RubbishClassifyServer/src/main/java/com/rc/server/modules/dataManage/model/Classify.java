package com.rc.server.modules.dataManage.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户信息
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rc_classify")
public class Classify {

    @TableId("classify_id")
    private String classifyId;


    @TableField("classify_code")
    private String classifyCode;

    @TableField("classify_name")
    private String classifyName;

    @TableField("remark")
    private String remark;

    @TableField("oper_permission")
    private String operPermission;

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("modify_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

}

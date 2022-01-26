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
@TableName("rc_rubbish")
public class Rubbish {

    @TableId("rubbish_id")
    private String rubbishId;

    @TableField("rubbish_code")
    private String rubbishCode;

    @TableField("rubbish_name")
    private String rubbishName;

    @TableField("rubbish_pic")
    private String rubbishPic;


    @TableField("classify_id")
    private String classifyId;

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("modify_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

}

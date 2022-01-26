package com.rc.server.modules.systemManage.model;

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
@TableName("rc_user")
public class RcUser {

    @TableId("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("user_pwd")
    private String userPwd;

    @TableField("user_role")
    private String userRole;

    @TableField("user_status")
    private String userStatus;

    @TableField("extend_prop")
    private String extendProp;

    @TableField("remark")
    private String remark;

    @TableField("email")
    private String email;

    @TableField("mobile")
    private String mobile;

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("modify_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @TableField("modify_by")
    private Date modifyBy;

}

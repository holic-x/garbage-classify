package com.rc.modules.model;

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class RcUser {

    private String userId;

    private String userName;

    private String userPwd;

    private String userType;

    private String userStatus;

    private String extendProp;

    private String remark;

    // 扩展token属性
    private String token;

}

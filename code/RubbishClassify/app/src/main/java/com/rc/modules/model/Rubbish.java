package com.rc.modules.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 垃圾信息
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Rubbish {

    private String rubbishId;

    private String rubbishCode;

    private String rubbishName;

    private String rubbishPic;

    private String classifyId;

    private String remark;

    private Date createTime;

    private Date modifyTime;

    // 对应绑定图像信息
    private int imageId;

    // 相关扩展属性
    private String classifyCode;
    private String classifyName;


    public Rubbish(String rubbishName,int imageId){
        this.rubbishName = rubbishName;
        this.imageId = imageId;
    }

}

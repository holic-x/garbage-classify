package com.rc.server.framework.domain;

import java.io.Serializable;

/**
 * @ClassName LabelValueBean
 * @Description 定义数据字典专用标签类(用于前端数据封装) - 用于简单的数据封装
 * @Author
 * @Date 2020/5/12 16:21
 * @Version
 **/
public class LabelValueBean implements Serializable {
    private static final long serialVersionUID = -1211726511402154326L;

    // 标签内容(前端页面显示)
    private String label;

    // 标签value(后端数据交互)
    private String value;

    // 定义数据是否默认选中
    // private Boolean checked = false;

    public LabelValueBean(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LabelValueBean{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
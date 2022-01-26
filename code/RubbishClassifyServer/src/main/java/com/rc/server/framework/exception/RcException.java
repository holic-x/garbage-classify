package com.rc.server.framework.exception;

/**
 * @ClassName FrameworkException
 * @Description TODO
 * @Author
 * @Date 2020/5/6 9:33
 * @Version
 **/
public class RcException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * @MethodName 功能模块名称(EbProject)
     **/
    private static final String MODULE_NAME = "RC-SERVER";

    public RcException(Integer errCode, Object[] args, String errMsg) {
        super(MODULE_NAME, errCode, args, errMsg);
    }

    public RcException(Integer errCode, String errMsg) {
        super(MODULE_NAME, errCode, null, errMsg);
    }

    public RcException(String errMsg) {
        super(MODULE_NAME, errMsg);
    }

    public RcException(String errMsg, Object[] params) {
        super(MODULE_NAME, errMsg, params);
    }

}

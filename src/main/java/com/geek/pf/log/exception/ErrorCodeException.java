package com.geek.pf.log.exception;

import com.geek.pf.log.constant.CfgConstant;
import com.geek.pf.log.container.LogMessageContainers;

/**
 * Error code exception.
 *
 * @author xujinkai
 * @date 2018/08/07
 */
public class ErrorCodeException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3236339020606911092L;

    /**
     * Bound error code
     */
    public final int errorCode;

    /**
     * Bound error message arguments
     */
    public final Object[] args;

    /**
     * Constructor with error code & args
     */
    public ErrorCodeException(int errorCode, Object... args) {

        this.errorCode = errorCode;
        this.args = args;
    }

    public ErrorCodeException(int errorCode, Throwable cause, Object... args) {

        super(cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    @Override
    public String getMessage() {

        return LogMessageContainers.getMessage(CfgConstant.MESSAGE_KEY_ERROR, errorCode + "", args);
    }

    public int getErrorCode() {

        return this.errorCode;
    }

    public String getErrorCodeStr() {

        return String.valueOf(this.errorCode);
    }

}

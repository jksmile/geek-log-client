package com.geek.pf.log.container;

/**
 * Log msg exception.
 *
 * @author xujinkai
 * @date 2018/08/06
 */

public class MessageException extends RuntimeException {

    private static final long serialVersionUID = 173658773696463429L;

    public MessageException(String msg) {

        super(msg);
    }

}


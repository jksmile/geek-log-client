package com.geek.pf.log.container;

/**
 * Log msg result.
 *
 * @author xujinkai
 * @date 2018/08/06
 */

public class MessageResult {

    private String messageId;

    private Object[] logArgs;

    public String getMessageId() {

        return messageId;
    }

    public void setMessageId(String messageId) {

        this.messageId = messageId;
    }

    public Object[] getLogArgs() {

        return logArgs;
    }

    public void setLogArgs(Object[] logArgs) {

        this.logArgs = logArgs;
    }
}

package com.geek.pf.log;

import org.slf4j.Logger;

/**
 * Log interface.
 *
 * @author xujinkai
 * @date 2018/08/07
 */
public interface FLogger extends Logger {

    void log(String messageId, Object... args);

    void log(Object obj);
}

package com.geek.pf.log.loader;

import java.util.List;

/**
 * Log loader.
 *
 * @author xujinkai
 * @date 2018/08/06
 */
public interface ILoggerLoader {

    List<IMessageLoader> getLoaders();
}

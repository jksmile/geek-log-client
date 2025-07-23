package com.geek.pf.log;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Log Factory.
 *
 * @author xujinkai
 * @date 2018/08/07
 */
public class FLoggerFactory {

    private static final Map<String, Object> loggerMap = new WeakHashMap<String, Object>();

    static {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        loggerContext.getFrameworkPackages().add("com.geek.pf.log.LoggerHandler");

        loggerContext.getFrameworkPackages().add("com.sun.proxy.$Proxy");
    }

    @SuppressWarnings("unchecked")
    public static <T> T getLogger(Class<?> cz) {

        return (T) getLogger(cz.getName());
    }

    @SuppressWarnings("unchecked")
    private static <T> T getLogger(String name) {

        Object logger = loggerMap.get(name);

        if (logger == null) {

            synchronized(name.intern()) {

                logger = loggerMap.get(name);

                if (logger == null) {

                    Logger target = LoggerFactory.getLogger(name);

                    logger = new LoggerHandler(target).getFLogger();

                    loggerMap.put(name, logger);
                }
            }
        }

        return (T) logger;
    }
}

package com.geek.pf.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;

import com.geek.pf.log.constant.CfgConstant;
import com.geek.pf.log.container.FormatLogMessage;
import com.geek.pf.log.convert.LogConverter;

/**
 * Log handler.
 *
 * @author xujinkai
 * @date 2018/08/07
 */
public class LoggerHandler implements InvocationHandler {

    private final Logger target;

    private final FLogger proxy;

    private final static String METHOD_NAME_LOG = "log";

    /**
     * Construct.
     *
     * @param target target
     */
    public LoggerHandler(Logger target) {

        this.target = target;

        Class<?>[] targetInterfaces = target.getClass().getInterfaces();

        Class<?>[] proxyInterfaces = new Class<?>[target.getClass().getInterfaces().length + 1];

        System.arraycopy(targetInterfaces, 0, proxyInterfaces, 0, targetInterfaces.length);

        proxyInterfaces[proxyInterfaces.length - 1] = FLogger.class;

        this.proxy = (FLogger) Proxy.newProxyInstance(target.getClass().getClassLoader(), proxyInterfaces, this);
    }

    /**
     * Get proxy class.
     *
     * @return FLogger
     */
    public FLogger getFLogger() {

        return proxy;
    }

    /**
     * Invoke for  target.
     *
     * @param proxy  proxy
     * @param method method
     * @param args   args
     *
     * @return Object
     *
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String mName = method.getName();

        if (!mName.equals(METHOD_NAME_LOG)) {

            return method.invoke(target, args);
        }

        FormatLogMessage logMessage;

        if (null == args || null == args[0]) {

            String text = "description:Missing the message id";

            logMessage =
                    FormatLogMessage.build(null, text, CfgConstant.ALARM_OPENED_FLAG, CfgConstant.MESSAGE_KEY_ERROR);

        } else {

            logMessage = LogConverter.convert(method, args);
        }

        String level = logMessage.level;

        String logMsg = logMessage.formatMsg;

        switch (level) {

            case CfgConstant.LOG_LEVEL_TRACE:

                target.trace(logMsg);

                break;
            case CfgConstant.LOG_LEVEL_DEBUG:

                target.debug(logMsg);

                break;
            case CfgConstant.LOG_LEVEL_WARN:

                target.warn(logMsg);

                break;
            case CfgConstant.LOG_LEVEL_INFO:

                target.info(logMsg);

                break;
            case CfgConstant.LOG_LEVEL_ERROR:

                target.error(logMsg);
                break;
        }

        return null;
    }

}

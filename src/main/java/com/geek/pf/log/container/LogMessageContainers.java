package com.geek.pf.log.container;

import com.geek.pf.log.alarm.IAlarm;
import com.geek.pf.log.constant.CfgConstant;
import com.geek.pf.log.loader.ILoggerLoader;
import com.geek.pf.log.loader.IMessageLoader;
import com.geek.pf.log.loader.LogServiceLoader;
import com.geek.pf.log.validator.IMessageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Msg container.
 *
 * @author xujinkai
 * @date 2018/08/06
 */

public class LogMessageContainers {

    private static Logger logger = LoggerFactory.getLogger(LogMessageContainers.class);

    private static final Map<String, Map<String, Message>> containerCache;

    static {

        containerCache = new HashMap<>();

        List<IMessageValidator> validators = LogServiceLoader.loadMessageValidator();

        List<ILoggerLoader> loaderList = LogServiceLoader.loadLoggerLoader();

        List<IAlarm> alarmList = LogServiceLoader.loadAlarm();

        for (ILoggerLoader loader : loaderList) {

            if (loader.getLoaders() == null) {

                continue;
            }

            for (IMessageLoader msgLoader : loader.getLoaders()) {

                String type = msgLoader.getType();

                if (!containerCache.containsKey(type)) {

                    containerCache.put(type, new HashMap<String, Message>());
                }
            }
        }

        for (ILoggerLoader loader : loaderList) {

            if (loader.getLoaders() == null) {

                continue;
            }

            for (IMessageLoader msgLoader : loader.getLoaders()) {

                String type = msgLoader.getType();

                List<Message> messageList = msgLoader.load();

                if (messageList == null) {

                    continue;
                }

                Map<String, Message> messageCache = containerCache.get(type);

                for (Message message : messageList) {

                    for (IMessageValidator validator : validators) {

                        String result = validator.validate(message);

                        if (result != null) {

                            throw new MessageException(message.getId() + " validate error:" + result);
                        }
                    }

                    String msgId = message.getId();

                    if (!messageCache.containsKey(msgId)) {

                        messageCache.put(msgId, message);

                    } else {

                        logger.error("container type:{},msgId:{} already exists.", type, msgId);
                    }
                }
            }
        }

        if (null != alarmList && !alarmList.isEmpty()) {

            for (ILoggerLoader loader : loaderList) {

                if (loader.getLoaders() == null) {
                    continue;
                }

                for (IMessageLoader msgLoader : loader.getLoaders()) {

                    List<Message> messageList = msgLoader.load();

                    for (IAlarm alarm : alarmList) {

                        alarm.addAlarm(messageList);
                    }
                }
            }
        }
    }

    /**
     * @param containerType 容器类型
     * @param messageId     消息ID
     *
     * @return Message
     */
    public static Message getLogMessage(String containerType, String messageId) {

        Map<String, Message> messageCache = containerCache.get(containerType);

        if (messageCache == null) {

            return null;
        }

        return messageCache.get(messageId);
    }

    /**
     * @param containerType 容器类型
     * @param messageId     消息ID
     * @param args          记录参数
     *
     * @return FormatLogMessage
     */
    public static FormatLogMessage getFormatLogMessage(String containerType, String messageId, Object[] args) {

        Message logMessage = getLogMessage(containerType, messageId);

        if (logMessage == null) {

            String text = "description:not found log message, messageId:" + messageId;

            return FormatLogMessage.build(messageId, text, "Y", CfgConstant.LOG_LEVEL_ERROR);
        }

        return new FormatLogMessage(logMessage, args);
    }

    public static String getFormatMessage(String containerKey, String messageId, Object... args) {

        return getMessage(containerKey, messageId, args);
    }

    public static String getFormatMessage(String containerKey, int messageId, Object... args) {

        return getMessage(containerKey, messageId, args);
    }

    public static String getFormatMessage(String containerKey, long messageId, Object... args) {

        return getMessage(containerKey, messageId, args);
    }

    public static String getMessage(String containerType, String messageId, Object... args) {

        return getFormatLogMessage(containerType, messageId, args).formatMsg;
    }

    public static String getMessage(String containerType, int messageId, Object... args) {

        return getFormatLogMessage(containerType, messageId + "", args).formatMsg;
    }

    public static String getMessage(String containerType, long messageId, Object... args) {

        return getFormatLogMessage(containerType, messageId + "", args).formatMsg;
    }

}

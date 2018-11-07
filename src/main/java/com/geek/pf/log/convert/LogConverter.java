package com.geek.pf.log.convert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.geek.pf.log.constant.CfgConstant;
import com.geek.pf.log.container.FormatLogMessage;
import com.geek.pf.log.container.LogMessageContainers;
import com.geek.pf.log.container.MessageResult;
import com.geek.pf.log.loader.LogServiceLoader;

public class LogConverter {

    private static final List<ICustomConverter> converts = new ArrayList<ICustomConverter>();

    static {

        List<ICustomConverter> convertLoader = LogServiceLoader.loadCustomConvertor();

        converts.addAll(convertLoader);
    }

    public static FormatLogMessage convert(Method method, Object[] args) {

        Class<?> firstParamType = args[0].getClass();

        MessageResult result = new MessageResult();

        if (firstParamType == String.class) {

            String messageId = (String) args[0];

            result.setMessageId(messageId);

            if (args.length > 1) {

                Object[] logArgs = (Object[]) args[1];

                result.setLogArgs(logArgs);
            }

        } else {

            for (ICustomConverter c : converts) {

                result = c.convert(method, args);

                if (result != null) {

                    break;
                }
            }
        }

        return LogMessageContainers
                .getFormatLogMessage(CfgConstant.MESSAGE_KEY_LOG, result.getMessageId(), result.getLogArgs());
    }
}

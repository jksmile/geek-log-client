package com.geek.pf.log.loader.local;

import com.geek.pf.log.constant.CfgConstant;
import com.geek.pf.log.loader.ILoggerLoader;
import com.geek.pf.log.loader.IMessageLoader;
import com.geek.pf.log.loader.LogServiceLoader;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Load local log loader.
 *
 * @author xujinkai
 * @date 2018/08/07
 */

public class LocalLoggerLoader implements ILoggerLoader {

    private final static String[] logLocation = {"classpath*:META-INF/log/*.log.xml"};

    private final static String[] errorLocation = {"classpath*:META-INF/error/*.error.xml"};

    private static List<IMessageLoader> messageLoaderList;

    public List<IMessageLoader> getLoaders() {

        if (!CollectionUtils.isEmpty(messageLoaderList)) {

            return messageLoaderList;
        }

        messageLoaderList = new ArrayList<>();

        String logCfgLocation = LogServiceLoader.props.getProperty(CfgConstant.LOG_FILE_LOCATION);

        if (null != logCfgLocation) {

            messageLoaderList
                    .add(new ResourceMessageLoader(CfgConstant.MESSAGE_KEY_LOG, logCfgLocation.split(",")));

        } else {

            messageLoaderList.add(new ResourceMessageLoader(CfgConstant.MESSAGE_KEY_LOG, logLocation));

        }

        String errorCfgLocation = LogServiceLoader.props.getProperty(CfgConstant.ERROR_FILE_LOCATION);

        if (null != errorCfgLocation) {

            messageLoaderList
                    .add(new ResourceMessageLoader(CfgConstant.MESSAGE_KEY_ERROR, errorCfgLocation.split(",")));

        } else {

            messageLoaderList.add(new ResourceMessageLoader(CfgConstant.MESSAGE_KEY_ERROR, errorLocation));
        }

        return messageLoaderList;
    }

}

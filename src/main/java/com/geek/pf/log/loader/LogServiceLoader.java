package com.geek.pf.log.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.geek.pf.log.alarm.IAlarm;
import com.geek.pf.log.alarm.MonitorAlarm;
import com.geek.pf.log.constant.CfgConstant;
import com.geek.pf.log.container.LogMessageContainers;
import com.geek.pf.log.convert.DefaultConverter;
import com.geek.pf.log.convert.ICustomConverter;
import com.geek.pf.log.loader.local.LocalLoggerLoader;
import com.geek.pf.log.validator.DefaultMessageValidator;
import com.geek.pf.log.validator.IMessageValidator;

/**
 * Log service loader.
 *
 * @author xujinkai
 * @date 2018/08/06
 */

public class LogServiceLoader {

    public static Properties props = null;

    private static Logger logger = LoggerFactory.getLogger(LogServiceLoader.class);

    /**
     * 日志加载源，无配置时使用本地xml加载源
     *
     * @return List<ILoggerLoader>
     */
    public static List<ILoggerLoader> loadLoggerLoader() {

        List<ILoggerLoader> vs = loadResource(ILoggerLoader.class, CfgConstant.LOADER_KEY_PREFIX);

        if (vs.size() == 0) {

            vs.add(new LocalLoggerLoader());
        }

        return vs;
    }

    /**
     * 日志格式验证器，默认json格式验证
     *
     * @return List<IMessageValidator>
     */
    public static List<IMessageValidator> loadMessageValidator() {

        List<IMessageValidator> vs = loadResource(IMessageValidator.class, CfgConstant.VALIDATOR_KEY_PREFIX);

        if (vs.size() == 0) {

            vs.add(new DefaultMessageValidator());
        }

        return vs;
    }

    /**
     * 日志解析转换器
     *
     * @return List<ICustomConvertor>
     */
    public static List<ICustomConverter> loadCustomConvertor() {

        List<ICustomConverter> cc = loadResource(ICustomConverter.class, CfgConstant.CONVERT_KEY_PREFIX);

        if (cc.size() == 0) {

            cc.add(new DefaultConverter());
        }

        return cc;
    }

    /**
     * 报警配置相关
     *
     * @return List<IAlarm>
     */
    public static List<IAlarm> loadAlarm() {

        List<IAlarm> alarmList = new ArrayList<>();

        if (alarm()) {

            alarmList = loadResource(IAlarm.class, CfgConstant.LOG_ALARM_PREFIX);
        }

        if (CollectionUtils.isEmpty(alarmList)) {

            alarmList.add(new MonitorAlarm());
        }

        return alarmList;
    }

    private static boolean alarm() {

        loadConfig();

        boolean alarm = false;

        if (props.containsKey(CfgConstant.OPEN_ALARM_PREFIX)
                && props.getProperty(CfgConstant.OPEN_ALARM_PREFIX).trim().equals(CfgConstant.ALARM_OPENED_FLAG)) {

            alarm = true;
        }

        return alarm;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadResource(Class<T> t, String prefix) {

        loadConfig();

        List<T> handlerList = new ArrayList<>();

        for (Object key : props.keySet()) {

            Object value = props.get(key);

            if (key.toString().startsWith(prefix)) {

                try {

                    Object obj = Class.forName(value.toString().trim()).newInstance();

                    handlerList.add((T) obj);

                } catch (Exception e) {

                    logger.error("Error", e);
                }
            }
        }

        return handlerList;
    }

    private static void loadConfig() {

        if (props == null || props.size() == 0) {

            InputStream inputStream = LogMessageContainers.class.getResourceAsStream(CfgConstant.LOG_DEFAULT_CFG_PATH);

            props = new Properties();

            if (inputStream != null) {

                try {

                    props.load(inputStream);

                } catch (IOException e) {

                    logger.error("Error", e);
                }

            }
        }

    }

}

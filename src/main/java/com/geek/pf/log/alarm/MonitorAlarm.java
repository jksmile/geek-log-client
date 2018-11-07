package com.geek.pf.log.alarm;

import java.util.List;

import com.geek.pf.log.constant.CfgConstant;
import com.geek.pf.log.container.Message;

/**
 * monitor alarm.
 *
 * @author xujinkai
 * @date 2018/08/07
 */

public class MonitorAlarm implements IAlarm {

    @Override
    public void addAlarm(List<Message> messageList) {

        if (null != messageList && messageList.size() > 0) {

            messageList.stream()
                    .filter(message -> message.getAlarm() != null && message.getAlarm()
                            .equals(CfgConstant.ALARM_OPENED_FLAG))
                    .forEach(message -> message.setText(message.getText() + ",alarm:" + message.getAlarm()));
        }

    }

    @Override
    public void cleanAlarm() {

    }

}

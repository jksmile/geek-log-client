package com.geek.pf.log.alarm;

import java.util.List;

import com.geek.pf.log.container.Message;

/**
 * Alarm interface.
 *
 * @author xujinkai
 * @date 2018/08/07
 */

public interface IAlarm {

    void addAlarm(List<Message> messageList);

    void cleanAlarm();
}

package com.geek.pf.log.container;

import org.apache.log4j.Level;

/**
 * Log message field.
 *
 * @author xujinkai
 * @date 2018/08/06
 */

public class Message {

    private String id;

    private String level;

    private String text;

    private String alarm;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getLevel() {

        return level;
    }

    public Level getLog4jLevel() {

        return Level.toLevel(level);
    }

    public void setLevel(String level) {

        this.level = level;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public String getAlarm() {

        return alarm;
    }

    public void setAlarm(String alarm) {

        this.alarm = alarm;
    }

}

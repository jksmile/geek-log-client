package com.geek.pf.log.container;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

/**
 * Format log msg.
 *
 * @author xujinkai
 * @date 2018/08/06
 */

public class FormatLogMessage {

    public final String level;

    public final String formatMsg;

    private final Object[] formatArgs;

    private final Throwable t;

    private static final String DEFAULT_LOG_LEVEL = "DEBUG";

    private static final String LINE_BREAK = "\r\n";

    public FormatLogMessage(Message logMessage, Object[] args) {

        if (args == null || args.length == 0) {

            this.formatArgs = null;

            this.t = null;

        } else {

            if (args[args.length - 1] instanceof Throwable) {

                this.t = (Throwable) args[args.length - 1];

                if (args.length > 1) {

                    this.formatArgs = new Object[args.length - 1];

                    System.arraycopy(args, 0, this.formatArgs, 0, this.formatArgs.length);

                } else {

                    this.formatArgs = null;
                }

            } else {

                this.formatArgs = args;

                this.t = null;
            }
        }

        if (this.formatArgs != null) {

            for (int i = 0; i < formatArgs.length; i++) {

                Object arg = formatArgs[i];

                if (arg instanceof Number) {

                    formatArgs[i] = arg.toString();
                }
            }
        }

        String strMsg = MessageFormat.format(logMessage.getText(), formatArgs);

        if (this.t != null) {

            StringWriter sw = new StringWriter();

            PrintWriter pw = new PrintWriter(sw);

            this.t.printStackTrace(pw);

            strMsg += LINE_BREAK + sw;
        }

        String level = logMessage.getLevel();

        if (level == null) {

            level = DEFAULT_LOG_LEVEL;
        }

        this.level = level;

        this.formatMsg = strMsg;
    }

    /**
     * build format log msg.
     *
     * @param id    日志ID
     * @param text  日志信息
     * @param alarm 报警标识
     * @param level 日志等级
     *
     * @return FormatLogMessage
     */
    public static FormatLogMessage build(String id, String text, String alarm, String level) {

        Message message = new Message();

        message.setId(id);

        message.setText(text + ",alarm:" + alarm);

        message.setAlarm(alarm);

        message.setLevel(level);

        return new FormatLogMessage(message, null);
    }
}

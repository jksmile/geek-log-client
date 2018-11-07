package com.geek.pf.log.loader;

import java.util.List;

import com.geek.pf.log.container.Message;

/**
 * Msg loader.
 *
 * @author xujinkai
 * @date 2018/08/06
 */
public interface IMessageLoader {

    List<Message> load();

    String getType();
}

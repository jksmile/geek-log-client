package com.geek.pf.log.validator;

import com.geek.pf.log.container.Message;

/**
 * Msg validator interface.
 *
 * @author xujinkai
 * @date 2018/08/06
 */

public interface IMessageValidator {

    String validate(Message m);
}

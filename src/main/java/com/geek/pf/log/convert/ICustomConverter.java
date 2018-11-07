package com.geek.pf.log.convert;

import java.lang.reflect.Method;

import com.geek.pf.log.container.MessageResult;

/**
 * Custom converter.
 *
 * @author xujinkai
 * @date 2018/08/07
 */
public interface ICustomConverter {

    MessageResult convert(Method method, Object[] args);
}

package com.geek.pf.log.convert;

import java.lang.reflect.Method;

import org.omg.CORBA.SystemException;

import com.geek.pf.log.container.MessageResult;
import com.geek.pf.log.exception.ErrorCodeException;

/**
 * Default convert.
 *
 * @author xujinkai
 * @date 2018/08/07
 */

public class DefaultConverter implements ICustomConverter {

    public MessageResult convert(Method method, Object[] args) {

        Class<?>[] paramTypes = method.getParameterTypes();

        Class<?> firstParamType = paramTypes[0];

        MessageResult result = null;

        if (firstParamType == Object.class && args[0].getClass() == SystemException.class) {

            result = new MessageResult();

            ErrorCodeException e = (ErrorCodeException) args[0];

            if (e != null) {

                result.setMessageId(e.errorCode + "");

                result.setLogArgs(e.args);
            }
        }

        return result;
    }

}
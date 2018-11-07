package com.geek.pf.log.validator;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.geek.pf.log.container.Message;

/**
 * Default msg validator.
 *
 * @author xujinkai
 * @date 2018/08/06
 */
public class DefaultMessageValidator implements IMessageValidator {

    private static final String ID_PATTERN = "\\w*";

    private static final String TEXT_KEY_PATTERN = "\\w*";

    private static final String TEXT_SPILT_FLAG = ",";

    private static final String TEXT_K_V_SPLIT = ":";

    public String validate(Message m) {

        String id = m.getId();

        String text = m.getText();

        if (StringUtils.isEmpty(id)) {

            return "ID is null";
        }

        if (StringUtils.isEmpty(text)) {

            return "Text is null";
        }

        if (!Pattern.matches(ID_PATTERN, id)) {

            return "ID validate fail.";
        }

        m.setText(text.trim());

        text = m.getText();

        if (!text.startsWith("description:")) {

            return "Not found description.";
        }

        String[] props = text.split(TEXT_SPILT_FLAG);

        for (int i = 1; i < props.length; i++) {

            String prop = props[i].trim();

            String[] vs = prop.split(TEXT_K_V_SPLIT);

            if (vs.length != 2) {

                return "text:" + prop + " validate fail.";
            }

            if (!Pattern.matches(TEXT_KEY_PATTERN, vs[0].trim())) {

                return "text:" + prop + " validate fail.";
            }
        }

        return null;
    }
}

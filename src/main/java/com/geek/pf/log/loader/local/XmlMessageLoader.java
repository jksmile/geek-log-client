package com.geek.pf.log.loader.local;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;

import com.geek.pf.log.container.Message;
import com.geek.pf.log.exception.LoadException;
import com.geek.pf.log.loader.IMessageLoader;

/**
 * Xml msg loader.
 *
 * @author xujinkai
 * @date 2018/08/07
 */
public class XmlMessageLoader implements IMessageLoader {

    private String type;

    private List<Message> msgList = new ArrayList<Message>();

    public XmlMessageLoader(String type) {

        this.type = type;
    }

    public XmlMessageLoader(String type, InputStream inputStream) {

        this.type = type;

        try {

            this.parseResource(inputStream);

        } catch (Exception e) {

            throw new LoadException(e.getMessage());
        }
    }

    protected void parseResource(InputStream inputStream) throws Exception {

        Digester digester = createDigester();

        digester.push(this);

        digester.parse(inputStream);
    }

    private Digester createDigester() {

        Digester digester = new Digester();

        digester.addObjectCreate("messages/message", Message.class);

        digester.addSetProperties("messages/message", "id", "id");

        digester.addSetProperties("messages/message", "level", "level");

        digester.addSetProperties("messages/message", "text", "text");

        digester.addSetProperties("messages/message", "alarm", "alarm");

        digester.addSetNext("messages/message", "addLogMessage");

        return digester;
    }

    public void addLogMessage(Message message) {

        msgList.add(message);
    }

    public List<Message> load() {

        return msgList;
    }

    public String getType() {

        return type;
    }

}

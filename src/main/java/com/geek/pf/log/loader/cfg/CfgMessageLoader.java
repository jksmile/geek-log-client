package com.geek.pf.log.loader.cfg;

import java.util.ArrayList;
import java.util.List;

import com.geek.pf.log.container.Message;
import com.geek.pf.log.exception.LoadException;
import com.geek.pf.log.loader.IMessageLoader;

/**
 * Remote config service loader.
 *
 * @author xujinkai
 * @date 2018/08/07
 */
public class CfgMessageLoader implements IMessageLoader {

    private String type;

    private List<Message> msgList = new ArrayList<Message>();

    public CfgMessageLoader(String type, String env, String appName, String deployType) {

        this.type = type;

        try {

            this.loadCfg(env, appName, deployType);

        } catch (Exception e) {

            throw new LoadException(e.getMessage());
        }
    }

    private void loadCfg(String ctxEnv, String ctxAppName, String ctxDeployType) throws Exception {

        // Todo. Load from remote config service.
    }

    @Override
    public List<Message> load() {

        return msgList;
    }

    @Override
    public String getType() {

        return type;
    }

}

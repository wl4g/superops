package com.wl4g.devops.umc.store.opentsdb.client;

/**
 * @Description: openstadb客户端工厂类
 * @Author: jinyao
 * @CreateDate: 2019/2/21 下午9:13
 * @Version: 1.0
 */
public class OpenTSDBClientFactory {

    public static OpenTSDBClient connect(OpenTSDBConfig config)  {
        return new OpenTSDBClient(config);
    }

}

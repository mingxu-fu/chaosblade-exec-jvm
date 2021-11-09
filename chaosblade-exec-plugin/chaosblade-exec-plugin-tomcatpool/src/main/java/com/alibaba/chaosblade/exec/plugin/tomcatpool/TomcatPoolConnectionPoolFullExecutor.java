package com.alibaba.chaosblade.exec.plugin.tomcatpool;

import com.alibaba.chaosblade.exec.common.model.action.connpool.WaitingTriggerConnectionPoolFullExecutor;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mingxu Fu
 */
public class TomcatPoolConnectionPoolFullExecutor extends WaitingTriggerConnectionPoolFullExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatPoolConnectionPoolFullExecutor.class);

    public static TomcatPoolConnectionPoolFullExecutor INSTANCE = new TomcatPoolConnectionPoolFullExecutor();

    @Override
    public int getMaxPoolSize() {
        if (dataSource != null) {
            try {
                Object maxActive = ReflectUtil.invokeMethod(dataSource, "getMaxActive", new Object[0], true);
                if (maxActive != null) {
                    return (Integer)maxActive;
                }
            } catch (Exception e) {
                LOGGER.warn("invoke getMaxActive method of {} exception.", dataSource.getClass().getName(), e);
            }
        }
        return DEFAULT_MAX_POOL_SIZE;
    }

    @Override
    protected void doRevoke() {
    }

}

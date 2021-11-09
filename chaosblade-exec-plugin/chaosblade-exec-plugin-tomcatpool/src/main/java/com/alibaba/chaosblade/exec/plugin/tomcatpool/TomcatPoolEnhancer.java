package com.alibaba.chaosblade.exec.plugin.tomcatpool;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/**
 * @author Mingxu Fu
 */
public class TomcatPoolEnhancer extends BeforeEnhancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatPoolEnhancer.class);

    @Override
    public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
                                        Object[] methodArguments) throws Exception {
        if (object != null && DataSource.class.isInstance(object)) {
            LOGGER.debug("match the tomcatpool dataSource, object: {}", className);
            TomcatPoolConnectionPoolFullExecutor.INSTANCE.setDataSource(object);
        } else {
            LOGGER.debug("the object is null or is not instance of DataSource class, object: {}", object ==
                null ? null : object.getClass().getName());
        }
        MatcherModel matcherModel = new MatcherModel();

        return null;
    }
}

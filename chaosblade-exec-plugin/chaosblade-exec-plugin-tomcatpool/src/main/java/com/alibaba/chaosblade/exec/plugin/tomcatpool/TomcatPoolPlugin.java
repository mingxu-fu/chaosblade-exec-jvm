package com.alibaba.chaosblade.exec.plugin.tomcatpool;

import com.alibaba.chaosblade.exec.common.aop.Enhancer;
import com.alibaba.chaosblade.exec.common.aop.Plugin;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.model.ModelSpec;

/**
 * @author Mingxu Fu
 */
public class TomcatPoolPlugin implements Plugin {

    @Override
    public String getName() {
        return TomcatPoolConstant.TomcatPool_PLUGIN_NAME;
    }

    @Override
    public ModelSpec getModelSpec() {
        return new TomcatPoolModelSpec();
    }

    @Override
    public PointCut getPointCut() {
        return new TomcatPoolPointCut();
    }

    @Override
    public Enhancer getEnhancer() {
        return new TomcatPoolEnhancer();
    }
}

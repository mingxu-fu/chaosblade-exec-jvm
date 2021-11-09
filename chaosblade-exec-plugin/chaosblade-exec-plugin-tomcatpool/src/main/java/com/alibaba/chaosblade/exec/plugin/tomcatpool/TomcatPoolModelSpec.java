package com.alibaba.chaosblade.exec.plugin.tomcatpool;

import com.alibaba.chaosblade.exec.common.aop.PredicateResult;
import com.alibaba.chaosblade.exec.common.exception.ExperimentException;
import com.alibaba.chaosblade.exec.common.model.BaseModelSpec;
import com.alibaba.chaosblade.exec.common.model.Model;
import com.alibaba.chaosblade.exec.common.model.action.ActionExecutor;
import com.alibaba.chaosblade.exec.common.model.action.ActionSpec;
import com.alibaba.chaosblade.exec.common.model.action.connpool.ConnectionPoolFullActionSpec;
import com.alibaba.chaosblade.exec.common.model.handler.PreCreateInjectionModelHandler;
import com.alibaba.chaosblade.exec.common.model.handler.PreDestroyInjectionModelHandler;

/**
 * @author Mingxu Fu
 */
public class TomcatPoolModelSpec extends BaseModelSpec implements PreCreateInjectionModelHandler, PreDestroyInjectionModelHandler {

    public TomcatPoolModelSpec(){
        super();
        addConnectionPoolFullAction();
    }

    @Override
    protected PredicateResult preMatcherPredicate(Model matcherSpecs) {
        return PredicateResult.success();
    }

    @Override
    public String getTarget() {
        return TomcatPoolConstant.TARGET_NAME;
    }

    @Override
    public String getShortDesc() {
        return "Experiment with the tomcatpool";
    }

    @Override
    public String getLongDesc() {
        return "Experiment with the tomcat connection pool, For example `blade create tomcatpool connectionpoolfull`";
    }

    @Override
    public void preCreate(String suid, Model model) throws ExperimentException {
        if (ConnectionPoolFullActionSpec.NAME.equals(model.getActionName())) {
            ActionSpec actionSpec = getActionSpec(model.getActionName());
            ActionExecutor actionExecutor = actionSpec.getActionExecutor();
            if (actionExecutor instanceof TomcatPoolConnectionPoolFullExecutor) {
                TomcatPoolConnectionPoolFullExecutor executor = (TomcatPoolConnectionPoolFullExecutor) actionExecutor;
                executor.setExpReceived(true);
            } else {
                throw new ExperimentException("The executor about tomcat connection pool full for tddl is error when "
                        + "creating");
            }
        }


    }

    @Override
    public void preDestroy(String suid, Model model) throws ExperimentException {
        if (ConnectionPoolFullActionSpec.NAME.equals(model.getActionName())) {
            ActionSpec actionSpec = getActionSpec(model.getActionName());
            ActionExecutor actionExecutor = actionSpec.getActionExecutor();
            if (actionExecutor instanceof TomcatPoolConnectionPoolFullExecutor) {
                TomcatPoolConnectionPoolFullExecutor executor = (TomcatPoolConnectionPoolFullExecutor)actionExecutor;
                executor.revoke();
            } else {
                throw new ExperimentException("The executor about tomcat connection pool full for tddl is error when "
                        + "destroying");
            }
        }
    }

    private void addConnectionPoolFullAction() {
        ConnectionPoolFullActionSpec actionSpec = new ConnectionPoolFullActionSpec(TomcatPoolConnectionPoolFullExecutor.INSTANCE);
        actionSpec.setExample("# Do a full load experiment on the tomcat connection pool\n" +
                "blade create tomcatpool connectionpoolfull");
        addActionSpec(actionSpec);
    }
}


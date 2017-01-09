package com.hqyg.disjob.java.spring;

import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.job.AbstractSharingJobCommand;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.quence.Log;

/**
 * 处理分片的任务
 * @author Disjob
 *
 */
public class SpringSharingJobCommand extends AbstractSharingJobCommand{

	public SpringSharingJobCommand(RpcContainer rpcContainer) {
		super(rpcContainer);
	}

	@Override
	public EJob getEjobAction(String className, String methodName) {
		if("execute".equals(methodName)){
			return SpringWorkFactory.getInstance().getEjob(className);
		}else{
			Log.warn("[ java ] 注册job的 method 参数类型错误，不是 execute。the error method is："+methodName);
			return null ;
		}
	}
}

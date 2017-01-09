package com.hqyg.disjob.java.job;

import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.service.JobService;

public class JobAction extends AbstractJobAction{
	public JobAction(RpcContainer rpcContainer) {
		super(rpcContainer);
	}
	public EJob getEjobAction(String className, String methodName){
		
		return JobService.getEJobAction(className, methodName);
	}
	
}

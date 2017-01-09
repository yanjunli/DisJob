package com.hqyg.disjob.java.job;

import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.service.JobService;

public class SharingJobCommand extends AbstractSharingJobCommand{

	public SharingJobCommand(RpcContainer rpcContainer) {
		super(rpcContainer);
	}

	@Override
	public EJob getEjobAction(String className, String methodName) {
		
		return JobService.getEJobAction(className, methodName);
	}

}

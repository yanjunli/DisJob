package com.hqyg.disjob.java.job;

import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.core.rpc.RpcRequest;
import com.hqyg.disjob.java.service.JobService;
import com.hqyg.disjob.quence.Command;
import com.hqyg.disjob.quence.TaskExecuteException;

public abstract class AbstractSharingJobCommand extends Command implements JobProvider{

	private RpcContainer rpcContainer ;
	
	public AbstractSharingJobCommand(RpcContainer rpcContainer) {
		super();
		this.rpcContainer = rpcContainer;
	}

	@Override
	public void executeException(String execeptionMsg) {
		
	}

	@Override
	public void execute() throws TaskExecuteException {
		RpcRequest request = rpcContainer.getMsg();
		String className = request.getData().getClassName();
		String methodName = request.getData().getMethodName();
		EJob action = getEjobAction(className, methodName);
		JobService.handlerExecuter(rpcContainer, action, rpcContainer.getCtx().channel());
	}

	@Override
	public void executeSuccess() {
		
	}

	public abstract EJob getEjobAction(String className, String methodName) ;
}

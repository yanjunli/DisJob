package com.hqyg.disjob.java.action;

import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.core.rpc.RpcResponse;
import com.hqyg.disjob.quence.TaskExecuteException;

public class SendCompleteTimeAction extends SendTimeAction {
	private RpcResponse rpcResponse;
	public SendCompleteTimeAction(RpcContainer rpcContiner, RpcResponse rpcResponse) {
		super(rpcContiner);
		this.rpcResponse = rpcResponse ;
	}

	@Override
	public void execute() throws TaskExecuteException {
		sendRpcResponse(rpcResponse);
	}

}

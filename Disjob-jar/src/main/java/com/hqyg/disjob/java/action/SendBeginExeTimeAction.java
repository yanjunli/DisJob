package com.hqyg.disjob.java.action;

import java.util.Date;

import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.core.rpc.RpcResponse;
import com.hqyg.disjob.java.job.JobExecuteStatus;
import com.hqyg.disjob.java.utils.TimeUtils;
import com.hqyg.disjob.quence.TaskExecuteException;

/**
 * 向 ejob 调度中发送 job 的开始执行时间
 * @author Disjob
 *
 */
public class SendBeginExeTimeAction extends SendTimeAction{

	private Date beginExeTime ;
	public SendBeginExeTimeAction(RpcContainer rpcContiner,Date beginExeTime) {
		super(rpcContiner);
		this.beginExeTime = beginExeTime ;
	}
	@Override
	public void execute() throws TaskExecuteException {
		RpcResponse rpcResponse = new RpcResponse() ;
		rpcResponse.setJobBegingTime(TimeUtils.local2Utc(beginExeTime));
		rpcResponse.setRequestId(requestId);
		rpcResponse.setCode(String.valueOf(JobExecuteStatus.SUCCESS));
		sendRpcResponse(rpcResponse);
	}
	
}

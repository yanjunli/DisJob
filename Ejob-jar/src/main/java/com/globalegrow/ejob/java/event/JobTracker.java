package com.hqyg.disjob.java.event;

import com.hqyg.disjob.event.AbstractEventObject;
import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.event.ObjectListener;
import com.hqyg.disjob.java.EjobConstants;
import com.hqyg.disjob.java.ExecutorBuilder;
import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.job.JobAction;
import com.hqyg.disjob.java.job.SharingJobCommand;
import com.hqyg.disjob.java.service.EjobConfigService;
import com.hqyg.disjob.java.spring.SpringJobAction;
import com.hqyg.disjob.java.spring.SpringSharingJobCommand;
import com.hqyg.disjob.java.utils.StringUtils;
import com.hqyg.disjob.quence.Action;
import com.hqyg.disjob.quence.ActionQueue;
import com.hqyg.disjob.quence.BaseActionQueue;

public class JobTracker extends AbstractEventObject<RpcContainer>{

	private BaseActionQueue baseActionQueue ;
	public JobTracker() {
		this.baseActionQueue = new BaseActionQueue(ExecutorBuilder.getExecutor());
	}
	
	@Override
	public void attachListener() {
		this.addListener(new ObjectListener<RpcContainer>() {
			
			public void onEvent(ObjectEvent<RpcContainer> event) {
				RpcContainer rpcContainer = event.getValue();
				if(rpcContainer.getMsg() == null){
					return ;
				}
				
				if(rpcContainer.getMsg().getData() == null){
					return ;
				}
				
				//SharingRequestId 为null 表示不是分片的job,否则是
				if(StringUtils.isEmpty(rpcContainer.getMsg().getData().getSharingRequestId())){
					if(EjobConfigService.getStartupType() == EjobConstants.StartUpType.SPRING_START_UP){
						enQueue(new SpringJobAction(rpcContainer));
					}else{
						enQueue(new JobAction(rpcContainer));
					}
				}else{
					if(EjobConfigService.getStartupType() == EjobConstants.StartUpType.SPRING_START_UP){
						ExecutorBuilder.getSharingExecutor().execute(new SpringSharingJobCommand(rpcContainer));
					}else{
						ExecutorBuilder.getSharingExecutor().execute(new SharingJobCommand(rpcContainer));
					}
				}
			}
		}, EventType.RPC_REQUEST_HANDLER);
	}
	
	private void enQueue(Action action){
		if(action.getActionQueue() ==null){
			action.setActionQueue(baseActionQueue);
		}
		this.baseActionQueue.enqueue(action);
	}
	
	public void notifyRpcHandler(RpcContainer rpcContiner){
		ObjectEvent<RpcContainer> objectEvent = new ObjectEvent<RpcContainer>(rpcContiner, EventType.RPC_REQUEST_HANDLER);
		notifyListeners(objectEvent);
	}
	
	public ActionQueue getActionQueue(){
		
		return this.baseActionQueue;
	}
}

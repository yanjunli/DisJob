package com.hqyg.disjob.register.core.util;

import com.hqyg.disjob.common.util.SpringWorkFactory;
import com.hqyg.disjob.register.center.ServerZKRegistry;
import com.hqyg.disjob.register.core.service.GeneralSchedulerService;
import com.hqyg.disjob.register.core.service.JobExecutedThreadPoolService;
import com.hqyg.disjob.register.job.JobOperationService;
import com.hqyg.disjob.register.repository.ZnodeApi;
import com.hqyg.disjob.register.rpc.SubscribeService;

public class RegisterSpringWorkFactory extends SpringWorkFactory {

	public static JobExecutedThreadPoolService getJobExecutedThreadPoolService(){

		return (JobExecutedThreadPoolService)getWorkObject("jobExecutedThreadPoolService");
	}
	
	public static JobOperationService getJobOperationService(){
		
		return (JobOperationService) getWorkObject("jobOperationService");
	}
	
	public static ServerZKRegistry getServerZKRegistry(){
		
		return (ServerZKRegistry) getWorkObject("serverZKRegistry");
	}
	
	public static SubscribeService getSubscribeService(){
		
		return (SubscribeService) getWorkObject("subscribeService");
	} 
	
	public static ZnodeApi getZnodeApi(){
		
		return (ZnodeApi) getWorkObject("znodeApi");
	}
	
	public static GeneralSchedulerService getGeneralSchedulerService(){
		
		return (GeneralSchedulerService) getWorkObject("generalSchedulerService");
	}
}

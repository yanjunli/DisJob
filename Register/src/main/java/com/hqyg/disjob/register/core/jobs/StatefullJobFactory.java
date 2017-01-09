package com.hqyg.disjob.register.core.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * <pre>
 * 
 *  File: ScheduleJobFactory.java
 * 
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  任务调度分发工厂类:负责任务的分配
 * 
 *  Revision History
 *
 *  Date：		2016年5月23日
 *  Author：		Disjob
 *
 * </pre>
 */
@DisallowConcurrentExecution
public class StatefullJobFactory extends AbstractJobFactory {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		executeJobInternal(context.getJobDetail().getJobDataMap());
	}
}

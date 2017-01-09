package com.hqyg.disjob.register.core.action;

import org.apache.commons.lang3.StringUtils;

import com.hqyg.disjob.quence.Action;
import com.hqyg.disjob.quence.TaskExecuteException;
import com.hqyg.disjob.register.core.util.RegisterSpringWorkFactory;
import com.hqyg.disjob.register.domain.Job;
import com.hqyg.disjob.slaver.utils.SlaveUtils;

public class UpdateLastFireTimeAction extends Action{
	private Job job ;
	public UpdateLastFireTimeAction(Job job) {
		this.job = job ;
	}
	@Override
	public void execute() throws TaskExecuteException {
		if(job!=null&&StringUtils.isNotEmpty(job.getGroupName())&&StringUtils.isNotEmpty(job.getJobName())){
			SlaveUtils.setLastFireTimeByGroupNameAndJobName(RegisterSpringWorkFactory.getJobExecutedThreadPoolService().getClient(), job.getGroupName(), job.getJobName());
		}
	}

}

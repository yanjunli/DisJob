package com.hqyg.disjob.graph;

import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.job.DependEJob;
import com.hqyg.disjob.quence.TaskExecuteException;

public class JobD extends DependEJob{

	private String name;
	public JobD(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getKey() {
		return "depend_"+this.name;
	}

	public void beforeExecute(SchedulerParam schedulerParam) {
		
	}

	public void execute(SchedulerParam schedulerParam)
			throws TaskExecuteException {
		
	}

	public void executeSuccess(SchedulerParam schedulerParam) {
		
	}

	public void executeFail(SchedulerParam schedulerParam) {
		
	}
}

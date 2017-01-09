package com.hqyg.disjob.servlet;

import java.util.Date;

import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.core.annotation.JobDec;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.java.utils.DebugInfoPrintUtil;
import com.hqyg.disjob.java.utils.TimeUtils;
import com.hqyg.disjob.quence.TaskExecuteException;

@JobDec(group="servletCronJob_1",jobName="fireNowJobServlet_14",quartz="0/10 * * * * ?",fireNow=true)
public class ServletFireNowJob_14 implements EJob{

	@Override
	public void beforeExecute(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void execute(SchedulerParam schedulerParam)
			throws TaskExecuteException {
		DebugInfoPrintUtil.debug("D:/servlet_cron_firenow.log", this.getClass().getName()+"; at time:"+TimeUtils.getFormat(new Date(), TimeUtils.YYYY_MM_DD_HH_MM_SS));
	}

	@Override
	public void executeSuccess(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void executeFail(SchedulerParam schedulerParam) {
		
	}

}

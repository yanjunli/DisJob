package com.hqyg.disjob.spring;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.hqyg.disjob.AlamerLogWriter;
import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.java.utils.DebugInfoPrintUtil;
import com.hqyg.disjob.java.utils.TimeUtils;
import com.hqyg.disjob.quence.TaskExecuteException;

public class SpringJob_0 implements EJob{
	public SpringJob_0() {
		System.err.println("spring 启动... SpringJob_0 ");
	}

	@Override
	public void beforeExecute(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void execute(SchedulerParam schedulerParam)
			throws TaskExecuteException {
		int time = new Random().nextInt(3);
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AlamerLogWriter.writer.println(schedulerParam.getRequestId()+" "+this.getClass().getName()+"; take time :"+ time + "s .time:"+TimeUtils.getFormat(new Date(), TimeUtils.YYYY_MM_DD_HH_MM_SS));
		AlamerLogWriter.writer.flush();	
		DebugInfoPrintUtil.debug("D:/spring_object.log", this.toString());
	}

	@Override
	public void executeSuccess(SchedulerParam schedulerParam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeFail(SchedulerParam schedulerParam) {
		
	}
	
}

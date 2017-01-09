package com.hqyg.disjob.job;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.hqyg.disjob.AlamerLogWriter;
import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.java.utils.TimeUtils;
import com.hqyg.disjob.quence.TaskExecuteException;

/**
 * 设置的超时时间：10 s
 * @author Disjob
 *
 */
public class UpdateData implements EJob {

	@Override
	public void beforeExecute(SchedulerParam schedulerParam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(SchedulerParam schedulerParam)
			throws TaskExecuteException {
		int time = new Random().nextInt(80);
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AlamerLogWriter.writer.println(schedulerParam.getRequestId()+" "+this.getClass().getName()+"; take time :"+ time + "s .time:"+TimeUtils.getFormat(new Date(), TimeUtils.YYYY_MM_DD_HH_MM_SS));
		AlamerLogWriter.writer.flush();
	}

	@Override
	public void executeSuccess(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void executeFail(SchedulerParam schedulerParam) {
		
	}

}

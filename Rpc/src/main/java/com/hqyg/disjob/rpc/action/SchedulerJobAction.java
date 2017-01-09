package com.hqyg.disjob.rpc.action;

import com.hqyg.disjob.common.Constants;
import com.hqyg.disjob.common.exception.TransportException;
import com.hqyg.disjob.common.util.DateUtil;
import com.hqyg.disjob.common.util.LoggerUtil;
import com.hqyg.disjob.monitor.db.service.DBJobBasicInfoService;
import com.hqyg.disjob.monitor.pojo.MessagePiple;
import com.hqyg.disjob.monitor.service.JobService;
import com.hqyg.disjob.monitor.util.MonitorSpringWorkFactory;
import com.hqyg.disjob.quence.Action;
import com.hqyg.disjob.quence.TaskExecuteException;
import com.hqyg.disjob.rpc.client.proxy.RpcClient;
import com.hqyg.disjob.rpc.codec.Response;
import com.hqyg.disjob.rpc.codec.RpcRequest;
import com.hqyg.disjob.rpc.utils.ScheduleResponse;
import com.hqyg.disjob.monitor.alarm.pojo.AlarmType;
import com.hqyg.disjob.monitor.alarm.service.MsgPushService;
import com.hqyg.disjob.monitor.db.domain.DBJobBasicInfo;

public class SchedulerJobAction extends Action {
	private ScheduleResponse response ;
	private RpcClient rpcClient ;
	private RpcRequest request ;
	private DBJobBasicInfoService dbJobService ;
	private MsgPushService msgPushService ;
	private DBJobBasicInfo info ;
	public SchedulerJobAction(ScheduleResponse response,RpcClient rpcClient,RpcRequest request) {
		this.response = response ;
		this.rpcClient = rpcClient ;
		this.request = request ;
		this.dbJobService = MonitorSpringWorkFactory.getDBJobBasicInfoService() ;
		info = new DBJobBasicInfo();
	}
	
	@Override
	public void execute() throws TaskExecuteException {
		String groupName = response.getGroupName();
		String jobName = response.getJobName();
		
		info.setUuid(response.getRequestId());
		info.setGroupName(groupName);
		info.setJobName(jobName);
		if (response.getScheduleStartTime() != null) {
			info.setScheduleStart(DateUtil.local2Utc(response.getScheduleStartTime()));
		}
		if (response.getScheduleEndTime() != null) {
			info.setScheduleEnd(DateUtil.local2Utc(response.getScheduleEndTime()));
		}
		info.setScheduleSip(response.getScheduleServerIp());
		info.setBusinessSip(response.getExecuteServerIp());
		info.setErrorReason(response.getException());
		//1、
		JobService jobService = MonitorSpringWorkFactory.getJobService();
		jobService.setJobSchedulerTime(response.getRequestId());
		jobService.newJobTracker(response.getRequestId(),groupName,jobName).notifyCreateDBBasicInfoEvent(info);
		
		MonitorSpringWorkFactory.getMessagePipleService().putMessagePiple(new MessagePiple(response.getRequestId(),response.getGroupName(),response.getJobName(),response.getTimeout()));
		LoggerUtil.debug(response.getRequestId() + "   save: " + info.toString());
		
		//2、
		if(Constants.isCanConnPool){
			try {
				Response re = rpcClient.request(request);
				info.setErrorLocation(re.getException());
			} catch (TransportException e) {
				e.printStackTrace();
			}
		}else{
			info.setErrorLocation(rpcClient.writerAndFlush(request).getException());
		}
	}
	
	@Override
	public void execeptionCaught(Exception e) {
		info.setCurrentStatus('0');
		info.setErrorType(String.valueOf(AlarmType.EXECEPTION));
		LoggerUtil.error("rpc data error,groupName=" + info.getGroupName() + ",jobName=" + info.getJobName() + ",requestId=" + request.getData().getRequestId() + " error:" + response.getException());
 		msgPushService.notify(info.getGroupName(),"group:" + info.getGroupName() + ",job:" + info.getJobName(), info.getErrorType(), "requestId:" + request.getData().getRequestId());
		info.setErrorReason(e.getMessage());
		// 因为先保存后发送,发送过程中如果存在失败则更新基本信息表
		dbJobService.update(info);
	}
}

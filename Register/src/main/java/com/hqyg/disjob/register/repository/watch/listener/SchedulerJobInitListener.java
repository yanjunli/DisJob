package com.hqyg.disjob.register.repository.watch.listener;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;

import com.hqyg.disjob.common.Constants;
import com.hqyg.disjob.common.model.JobInfo;
import com.hqyg.disjob.common.util.LoggerUtil;
import com.hqyg.disjob.register.core.jobs.StatelessJobFactory;
import com.hqyg.disjob.register.core.service.GeneralSchedulerService;
import com.hqyg.disjob.register.repository.ZnodeApi;

/**
 * PathChildrenCache监听的listener 
 * 监听路径：/ejob/scheduler/slave/ip/execution
 * 目的：如果路径的子节点发生变更，说明ejob slave server的job发生变更 在这里要动态对quartz
 * scheduler中的job进行更新，更新必须实时
 * 
 * @author Disjob
 */
public class SchedulerJobInitListener implements PathChildrenCacheListener {

	private GeneralSchedulerService generalSchedulerService;

	private ZnodeApi nodeApi;

	private CuratorFramework client;

	public SchedulerJobInitListener(CuratorFramework client, GeneralSchedulerService generalSchedulerService, ZnodeApi nodeApi) {
		this.client = client;
		this.generalSchedulerService = generalSchedulerService;
		this.nodeApi = nodeApi;
	}

	@Override
	public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) {
		String groupPath = event.getData().getPath();
		 
		synchronized (SchedulerJobInitListener.class) {
			String groupNode = ZKPaths.getNodeFromPath(event.getData().getPath());
			String jobNameStr = nodeApi.getData(client, groupPath);
			String [] jobNameArray = {};
			
			switch (event.getType()) {
			case CHILD_ADDED:
				LoggerUtil.info("JOB_ADDED:" + event.getData().getPath());
				updateSchedulerJob(jobNameStr, jobNameArray, groupNode);
				break;
			case CHILD_UPDATED:
				LoggerUtil.info("JOB_UPDATED:" + event.getData().getPath());
				updateSchedulerJob(jobNameStr, jobNameArray, groupNode);
				break;
			case CHILD_REMOVED:
				LoggerUtil.info("JOB_REMOVED:" + event.getData().getPath());
				generalSchedulerService.deleteJobGroup(groupNode);
				break;
			default:
				break;
			}
		}
	}
	
	private void updateSchedulerJob(String jobNameStr, String [] jobNameArray, String groupNode){
		
		generalSchedulerService.deleteJobGroup(groupNode);
		if(StringUtils.isEmpty(jobNameStr)){
			LoggerUtil.debug(" updateSchedulerJob returned jobNameArray : " + jobNameStr + " | groupNode : " + groupNode);
			return ;
		}

		jobNameArray = jobNameStr.split(Constants.TRANSFER_CHAR + Constants.JOB_SEPARATOR);
		LoggerUtil.debug(" updateSchedulerJob jobNameArray : " + jobNameStr + " | groupNode : " + groupNode);
		for (String jobName : jobNameArray) {
			if(StringUtils.isEmpty(jobName)){
				continue;
			}

			String jobRootPath = ZKPaths.makePath(Constants.ROOT, Constants.APP_JOB_NODE_ROOT);
			String jobPath = ZKPaths.makePath(jobRootPath, Constants.PATH_SEPARATOR + groupNode,
					Constants.PATH_SEPARATOR + jobName, Constants.APP_JOB_NODE_CONFIG);
			JobInfo job = nodeApi.getData(client, jobPath, JobInfo.class);
			//如果该job是暂停状态,则不构造job来执行
			if(job !=null && (job.getJobStatus() == 3 || job.getJobStatus() == 0)){	//未激活 or 仅提交,还没有cron表达式
				LoggerUtil.warn("jobinfo schedule failure job status:" + job.getJobStatus() + " jobGroup:" + groupNode + " jobName:" + jobName);
				continue;
			}
			if(job != null){
				LoggerUtil.debug("jobinfo path:" + jobPath);
				LoggerUtil.debug("jobinfo json:" + nodeApi.getData(client, jobPath));
				LoggerUtil.debug("jobinfo string:" + job.toString());
				 
			    job.setJobClass(StatelessJobFactory.class);
			    LoggerUtil.debug("Init StatelessJob job to shceduler, jobGroup:" + groupNode + " jobName:" + jobName);
				generalSchedulerService.update(job);
				LoggerUtil.debug("Init job to shceduler, jobGroup:" + groupNode + " jobName:" + jobName);
			}else{
				LoggerUtil.warn("Can not find job, groupName:" + groupNode + " jobName:" + jobName +" on /ejob/job node");
			}	
		}
	}
}

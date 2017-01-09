package com.hqyg.disjob.register.repository.watch.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import com.hqyg.disjob.common.Constants;
import com.hqyg.disjob.common.util.LocalHost;
import com.hqyg.disjob.common.util.LoggerUtil;
import com.hqyg.disjob.register.cache.ZKJobCache;
import com.hqyg.disjob.register.domain.Job;
import com.hqyg.disjob.register.repository.ZnodeApiCuratorImpl;
import com.hqyg.disjob.register.repository.watch.WatchApiCuratorImpl;
import com.hqyg.disjob.register.rpc.NotifyListener;
import com.hqyg.disjob.register.rpc.ZkNodeType;
import com.hqyg.disjob.register.utils.RegisterUtils;
import com.hqyg.disjob.rpc.client.HURL;
import com.hqyg.disjob.slaver.utils.SlaveUtils;

/**
 * <pre>
 * 
 *  File: JobGroupListener.java
 * 
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  监听ejob/rpc/group子节点变化转化为Job节点,维护本地Group和Service的缓存信息
 *  功能：1 有变化后自动在对应ejob/job/group上创建任务信息 
 *  2.创建任务后在该任务后自动加上servernodelistener监听
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2016年5月24日				Disjob				Initial.
 *
 * </pre>
 */
public class JobNameListener extends AbstractJobBuild{
	String groupNode = null;

	public JobNameListener(String groupNode) {
		this.groupNode = groupNode;
	}

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception{    
    	boolean isMaster = false;
    	String serverNode = event.getData().getPath();
		if (org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_ADDED == event.getType()) {
			LoggerUtil.debug("add serverNode is ," + event.getData().getPath());
			String[] temp = serverNode.split(Constants.PATH_SEPARATOR);
			if (StringUtils.isNoneEmpty(serverNode) && temp != null&& temp.length >= 4) {
				HURL hurl = new HURL(temp[3], new LocalHost().getIp(), temp[4]);
				String serverTypePath = RegisterUtils.toNodeTypePath(hurl,ZkNodeType.PROVIDER);
				WatchApiCuratorImpl watchApiCuratorImpl = new WatchApiCuratorImpl();
				/**
				 * 这里监听server节点的子节点变化,目的是为了监控到没有被监听的group/server的节点,
				 * 因为只要直接注册如/ejob
				 * /group/service/providers/ip->rpcurl必定会触发GroupListener
				 * ,故也会触发到本监听器 通过本监听器将service的rpcURL等信息添加到本地缓存中
				 */
				watchApiCuratorImpl.pathChildrenWatch(client, serverTypePath,
						false, new ServerNodeListener(hurl,new NotifyListener() {
									@Override
									public void notify(HURL registryUrl,List<HURL> urls) {}
								}));
			}
			String masterIp = SlaveUtils.leaderLatch.getLeader().getId();
			if (StringUtils.isNotEmpty(masterIp)
					&& masterIp.equals(new LocalHost().getIp())) { // master断线后选出新的master然后旧的master恢复连接,此时已经不再是master,不能创建job
				isMaster = true;
			}
			if (isMaster) {
				buildJobByRPC(client, serverNode);
			}
			addGroupJobMap(serverNode, client);// 存入groupJobMap
			addServerMap(serverNode); // 维护本地服务分组节点和服务节点缓存信息
		}
         
        if(org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_REMOVED == event.getType()){
            LoggerUtil.debug("remove job is ,"+event.getData().getPath());
             List<String> serverNames = ZKJobCache.serverMap.get(groupNode);
            if(CollectionUtils.isEmpty(serverNames)){
                serverNames = new ArrayList<String>();
                ZKJobCache.serverMap.put(groupNode, serverNames);
            }
            if(serverNames.contains(serverNode)){
                serverNames.remove(serverNode);
            }
            
            String [] array = serverNode.split(Constants.PATH_SEPARATOR);
            String groupName = array[3];
            String jobName = array[4];
            Set<Job> jobList =  ZKJobCache.groupJobMap.get(groupName);
            if(!CollectionUtils.isEmpty(jobList)){
                 if(jobList.contains(new Job(groupName, jobName))){
                    jobList.remove(jobName);
                 }
            }
          }
        
        if(org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_UPDATED == event.getType()){
              LoggerUtil.info("update,"+event.getData().getPath());
        }
       }

    private void addServerMap(String serverNode){
        List<String> serverNames = ZKJobCache.serverMap.get(groupNode);
        if(CollectionUtils.isEmpty(serverNames)){
            serverNames = new ArrayList<String>();
            ZKJobCache.serverMap.put(groupNode, serverNames);
        }
        if(!serverNames.contains(serverNode)){
            serverNames.add(serverNode);
        }
    }

    /**
     * 根据服务节点构造groupJobMap对象的值
     * @param serverNode
     */
    private void addGroupJobMap(String serverNode,CuratorFramework client) {
		String[] array = serverNode.split(Constants.PATH_SEPARATOR);
		String groupName = array[3];
		String jobName = array[4];
		Set<Job> jobList = ZKJobCache.groupJobMap.get(groupName);
		if (CollectionUtils.isEmpty(jobList)) {
			jobList = new HashSet<Job>();
			ZKJobCache.groupJobMap.put(groupName, jobList);
		}
		ZnodeApiCuratorImpl nodeApi = new ZnodeApiCuratorImpl();
        String jobPath = nodeApi.makePath(Constants.ROOT, Constants.APP_JOB_NODE_ROOT,Constants.PATH_SEPARATOR+groupName,Constants.PATH_SEPARATOR+jobName, Constants.APP_JOB_NODE_CONFIG);
        
        Job job = nodeApi.getData(client, jobPath, Job.class);
        if(job == null){
        	job = new Job(groupName, jobName);
        }
		if (!jobList.contains(job)) {
			jobList.add(job);
		}
    }
}


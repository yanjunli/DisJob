package com.hqyg.disjob.java;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.hqyg.disjob.java.service.EjobConfigService;
import com.hqyg.disjob.java.utils.StringUtils;

public class CuratorClientBuilder {
	
	private static CuratorFramework client = null;
	private final static CuratorClientBuilder zkClientBuilder = new CuratorClientBuilder();
	
	private CuratorClientBuilder(){}
	
	public static void initCurator(String zkHost,String zkRootNode){
		if(client == null){
			synchronized (zkClientBuilder) {
				if(client == null){
					client =CuratorFrameworkFactory.builder().connectString(zkHost).namespace(zkRootNode).sessionTimeoutMs(3000).retryPolicy(new ExponentialBackoffRetry(3000, 10)).build();
					client.start();
				}
			}
		}
	}
	
	public static CuratorClientBuilder getInstance(){
		
		return zkClientBuilder ;
	}
	
	public CuratorFramework getCuratorFramework(){
		if(client == null){
			String zkhost = EjobConfigService.getZkHost();
			if(StringUtils.isEmpty(zkhost)){
				throw new RuntimeException(CuratorClientBuilder.class.getName() + "; start ejob fail because the config of zkhost is null.");
			}
			String zkrootnode = EjobConfigService.getZKRootNode();
			synchronized (zkClientBuilder) {
				if(client == null){
					initCurator(zkhost, zkrootnode);
				}
			}
		}
		return client ;
	}
	
	public CuratorFramework getCuratorFramework(String nameSpace){
		CuratorFramework client =CuratorFrameworkFactory.builder().connectString(EjobConfigService.getZkHost())
				.namespace(nameSpace).sessionTimeoutMs(3000).retryPolicy(new ExponentialBackoffRetry(3000, 10)).build();
		client.start();
		return client;
	}
}

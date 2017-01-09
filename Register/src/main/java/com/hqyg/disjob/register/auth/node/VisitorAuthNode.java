package com.hqyg.disjob.register.auth.node;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.ACL;

import com.hqyg.disjob.register.auth.AuthConstants;
import com.hqyg.disjob.register.auth.AuthUtil;
import com.hqyg.disjob.register.auth.AuthZKRegistry;
import com.hqyg.disjob.register.auth.EjobAuthInfo;
import com.hqyg.disjob.register.auth.EjobReaderACL;
import com.hqyg.disjob.register.repository.ZnodeApi;
import com.hqyg.disjob.register.repository.ZnodeApiCuratorImpl;

class VisitorAuthNode {

	protected CuratorFramework client;

	protected ZnodeApi znode = new ZnodeApiCuratorImpl();
	
	private String nodepath = AuthConstants.visitorRootPath;
	
	public VisitorAuthNode(String ZKHost){
		client = AuthUtil.getClient(ZKHost);
	}
	public VisitorAuthNode(CuratorFramework client){
		this.client = client;
	}
	
	public List<AuthInfo> getAuthInfos(){
		List<AuthInfo> authInfos = new ArrayList<>();
		if(znode.getData(client, nodepath) == null){
			new AuthZKRegistry(client).init();
		}
		authInfos.add(new EjobAuthInfo(znode.getByteData(client, nodepath)));
		return authInfos;
	}
	
	public List<ACL> getACLs() {
		List<ACL> acllist = new ArrayList<>();
		String adminAccount = znode.getData(client, nodepath);
		acllist.add(new EjobReaderACL(adminAccount));
		return acllist;
	}
	
}

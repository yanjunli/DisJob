package com.hqyg.disjob.register.auth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqyg.disjob.register.auth.ZKAuthOperator;
import com.hqyg.disjob.register.auth.node.GroupAuthNode;
import com.hqyg.disjob.register.auth.node.UserAuthNode;
import com.hqyg.disjob.register.auth.service.AuthService;
import com.hqyg.disjob.register.center.pool.ThreadLocalClient;

@Service("authService")
public class AuthServiceImpl implements AuthService {

	@Resource
	private ThreadLocalClient threadLocalClient;
	
	@Override
	public void assign(String username, String group, String type) {
		ZKAuthOperator op = new ZKAuthOperator(username, threadLocalClient.getCuratorClient().getCuratorClient());
		op.assign(group, type);
	}

	@Override
	public void unAssign(String username, String group, String type) {
		ZKAuthOperator op = new ZKAuthOperator(username, threadLocalClient.getCuratorClient().getCuratorClient());
		op.unAssign(group, type);
	}

	@Override
	public List<String> getAuthAvailableJobGroup() {
		return GroupAuthNode.getAuthAvailableJobGroup(threadLocalClient.getCuratorClient().getCuratorClient());
	}
	
	@Override
	public boolean[] getAuthByUsernameAndJobgroup(String username, String jobgroup) {
		return new UserAuthNode(threadLocalClient.getCuratorClient().getCuratorClient(), username).getAuthInfo(jobgroup);
	}
}

package com.hqyg.disjob.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hqyg.disjob.common.model.JobGroup;
import com.hqyg.disjob.common.model.Result;
import com.hqyg.disjob.console.SystemDefault;
import com.hqyg.disjob.register.job.EjobServerService;
import com.hqyg.disjob.register.job.JobOperationService;
import com.google.gson.Gson;
import com.hqyg.disjob.monitor.db.domain.PageResult;

@Controller
@RequestMapping("/service/job/bind")
@SessionAttributes(SystemDefault.USER_SESSION_KEY)
public class JobBindController {

	@Resource
	private EjobServerService ejobServerService;
	
	@Resource
	private JobOperationService jobOperationService;
	
	@RequestMapping("/getBindSession")
	@ResponseBody
	public List<String> getGroupList(HttpSession session) {
		
		return ejobServerService.getSessionsList();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/doBind")
	@ResponseBody
	public Result doBind(HttpSession session,
			@RequestParam(value="sessions", required=true)String sessions,
			@RequestParam(value="groupNames", required=true)String groupNames) {
		if(!StringUtils.isEmpty(sessions) && !StringUtils.isEmpty(groupNames)){
			List<String> sessionList = new Gson().fromJson(sessions, List.class);
			List<String> groupNameList = new Gson().fromJson(groupNames, List.class);
			jobOperationService.bindJob(sessionList, groupNameList);
			return new Result();			
		}else{
			return new Result(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/doReBind")
	@ResponseBody
	public Result doReBind(HttpSession session,
			@RequestParam(value="sessions", required=true)String sessions,
			@RequestParam(value="groupNames", required=true)String groupNames) {
		if(!StringUtils.isEmpty(sessions) && !StringUtils.isEmpty(groupNames)){
			List<String> sessionList = new Gson().fromJson(sessions, List.class);
			List<String> groupNameList = new Gson().fromJson(groupNames, List.class);
			jobOperationService.reBindJob(sessionList, groupNameList);
			return new Result();			
		}else{
			return new Result(false);
		}
	}
	
	@RequestMapping("/getJobGroupList")
	@ResponseBody
	public List<JobGroup> getJobGroupList(@RequestParam(value="search", required=false) String search) {
		List<JobGroup> groupNameList = jobOperationService.getAllJobGroupForPageList();
		return groupNameList;
	}
}

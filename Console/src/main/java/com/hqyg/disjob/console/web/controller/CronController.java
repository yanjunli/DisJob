package com.hqyg.disjob.console.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyg.disjob.console.util.CronUtils;
import com.hqyg.disjob.register.domain.CronInfo;

@Controller
@RequestMapping("/service/job/cron")
public class CronController {

	@RequestMapping("/transferFromCron")
	@ResponseBody
	public CronInfo transferFromCron(@RequestParam(value="cronExpression", required=true) String cronExpression){
		if(StringUtils.isEmpty(cronExpression)){
			CronInfo cronInfo = new CronInfo();
			cronInfo.setChooseSpecial(true);
			cronInfo.setSpecial(CronUtils.defaultSpecial);
			return cronInfo;
		}
		CronInfo cronInfo = CronUtils.transferFromCronExpression(cronExpression);
		return cronInfo;
	}
}

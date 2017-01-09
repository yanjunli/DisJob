package com.hqyg.test;

import com.google.gson.Gson;
import com.hqyg.disjob.console.util.CronUtils;
import com.hqyg.disjob.register.domain.CronInfo;

public class CronTest {

	public static void main(String[] args){
		
		System.out.println(CronUtils.captureName("ahgjk"));
		
		System.out.println(CronUtils.transferFromCronExpression("0 * * * * ?"));
		
		CronInfo cronInfo = new CronInfo();
		cronInfo.setAllDays(true);
		cronInfo.setAllHours(false);
		cronInfo.setHours(new String[]{"1","3","4","23"});
		System.out.println(new Gson().toJson(cronInfo));
		System.out.println(CronUtils.transferToCronExpression(cronInfo ));
	}
	
	
}

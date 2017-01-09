package com.hqyg.disjob.monitor.db.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hqyg.disjob.monitor.db.domain.DBMonitorAlarmInfo;
import com.hqyg.disjob.monitor.db.service.DBMonitorAlarmService;

public class DBMonitorAlarmMappingServiceTest extends AbstractTest{

	@Autowired
	private DBMonitorAlarmService service;
	
	@Test
	public void test() {
		DBMonitorAlarmInfo info = service.find("2");
		
		System.out.println(info.isAvailable());
	}
}

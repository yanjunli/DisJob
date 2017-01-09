package com.hqyg.disjob.monitor.db.mappers;

import com.hqyg.disjob.monitor.db.domain.DBMonitorAlarmInfo;

public interface DBMonitorAlarmMapper {

	public void insert(DBMonitorAlarmInfo monitorAlarmInfo);
	
	public DBMonitorAlarmInfo findByIndex(DBMonitorAlarmInfo dbMonitorAlarmInfo);
}

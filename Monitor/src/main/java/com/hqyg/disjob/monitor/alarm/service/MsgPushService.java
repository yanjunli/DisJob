package com.hqyg.disjob.monitor.alarm.service;

import com.hqyg.disjob.monitor.alarm.pojo.AlarmInfo;

public interface MsgPushService {

	public void notify(String jobGroup, String location, String type,String reason);

	public void notify(AlarmInfo alarmInfo);
}

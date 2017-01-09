package com.hqyg.disjob.monitor.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hqyg.disjob.common.util.DateUtil;
import com.hqyg.disjob.common.util.UUIDHexGenerator;
import com.hqyg.disjob.monitor.db.mappers.DBUserActionRecordMapper;
import com.hqyg.disjob.monitor.db.domain.DBUserActionRecord;

@Service("dbUserActionRecordService")
public class DBUserActionRecordService {

	@Autowired
	private DBUserActionRecordMapper mapper;
	
	public void createUserActionRecord(DBUserActionRecord record){
		record.setActionDate(DateUtil.now());
		record.setId(UUIDHexGenerator.generate());
		mapper.insert(record);
	}

	public void createUserActionRecord(String username, String pathInfo) {
		DBUserActionRecord record = new DBUserActionRecord();
		record.setUsername(username);
		record.setPermitItem(pathInfo);
		this.createUserActionRecord(record);
	}
	
	public List<DBUserActionRecord> selectUserActionRecordList(int limit, int offset, String search){
		return mapper.selectUserActionRecordByPaging(limit, offset, search);
	}
	
	public int selectUserActionRecordCount(String search){
		return mapper.selectUserActionRecordCount(search);
	}
}

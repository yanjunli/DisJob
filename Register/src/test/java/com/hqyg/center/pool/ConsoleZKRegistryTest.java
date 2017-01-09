package com.hqyg.center.pool;

import javax.annotation.Resource;

import org.junit.Test;

import com.hqyg.disjob.register.center.pool.ConsoleZKRegistry;
import com.hqyg.BaseJunitTest;

public class ConsoleZKRegistryTest extends BaseJunitTest {
	
	@Resource
	private ConsoleZKRegistry consoleZKRegistry;
     
	@Test
	public void initTest(){
		consoleZKRegistry.init();
	}
}

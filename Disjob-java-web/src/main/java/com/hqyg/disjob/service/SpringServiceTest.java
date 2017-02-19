package com.hqyg.disjob.service;

import org.springframework.stereotype.Service;

import com.hqyg.disjob.AlamerLogWriter;

@Service("springServiceTest")
public class SpringServiceTest {
	public SpringServiceTest() {
		System.out.println("-----");
	}
	public void writer(String info){
		AlamerLogWriter.writer.println(info);
		AlamerLogWriter.writer.flush();
	}
}  

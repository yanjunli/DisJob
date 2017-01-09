package com.hqyg.disjob.java.listener;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.event.ObjectListener;
import com.hqyg.disjob.java.dynamic.WatchServiceReactor;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.java.service.JobService;
import com.hqyg.disjob.java.utils.ClasspathPackageScanner;
import com.hqyg.disjob.java.utils.FileUtils;
import com.hqyg.disjob.java.utils.Log;
import com.hqyg.disjob.java.utils.StringUtils;

public class DynamicJobListener implements ObjectListener<String> {

	@SuppressWarnings({ "unchecked", "resource" })
	public void onEvent(ObjectEvent<String> event) {
		String path = event.getValue();
		if(StringUtils.isEmpty(path) || !FileUtils.isJarFile(path)){
			
			return ;
		}
		
		try {
			String src = event.getValue() ;
			URL url1 = new URL("file:"+src);
			URLClassLoader jobClassLoarder = new URLClassLoader(new URL[] { url1 }, Thread.currentThread().getContextClassLoader());
			List<String> classNames = new ClasspathPackageScanner(src).getClassNameList();
			for(String cn:classNames){
				Log.debug("dynamic job have fount :"+cn);
				Class<? extends EJob> clazz = (Class<? extends EJob>) jobClassLoarder.loadClass(cn);//load class to memory
				JobService.setEjobClass(cn, clazz);
			}
			
			JobService.initJob(classNames);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("dynamic init ejob error",e);
		}
		
	}
	public static void main(String[] args) {
		
		new Thread(new WatchServiceReactor("F:/__my_src/dynamic/")).start();
	}
}

package com.hqyg.disjob.java.listener;

import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.event.ObjectListener;
import com.hqyg.disjob.java.EjobConstants;
import com.hqyg.disjob.java.bean.StartUpConfig;
import com.hqyg.disjob.java.service.StartUpService;

/**
 * 这里只有 不是 spring 项目的才需要初始化 解析 properties 配置文件.spring 项目的话在 EjobSpringStartUp里面就已经帮我们初始化好了
 * @author Disjob
 *
 */
public class EjobInitConfigListener implements ObjectListener<StartUpConfig>{

	public void onEvent(ObjectEvent<StartUpConfig> event) {
		short type = event.getValue().getType();
		if(EjobConstants.StartUpType.JAVA_APPLICATION==type || EjobConstants.StartUpType.WEB_SERVLET_START_UP==type){
			boolean isWright = StartUpService.check(event.getValue().getEjobConfigPath(), event.getValue().getLog4jProperties());
			if(isWright){
				StartUpService.initEjobConfig(event.getValue().getEjobConfigPath());
				StartUpService.initLog4j(event.getValue().getLog4jProperties());
			}else{
				StartUpService.isInitSuccess = false ;
				throw new RuntimeException(this.getClass().getName() + "; start ejob fail because the config is error.");
			}
		}		
	}

}

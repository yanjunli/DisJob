package com.hqyg.disjob.java.core.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hqyg.disjob.java.EjobConstants;
import com.hqyg.disjob.java.bean.StartUpConfig;
import com.hqyg.disjob.java.core.dispatcher.EventObjectDispatcher;

public class EjobServletContextListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent event) {
		
		EventObjectDispatcher.dispatcherEjobStop();
	}

	public void contextInitialized(ServletContextEvent event) {
		String ejobConfig = event.getServletContext().getInitParameter(EjobConstants.Config.EJOB_CONFIG_PATH);
		EventObjectDispatcher.dispatcherEjobStartUp(new StartUpConfig(EjobConstants.StartUpType.WEB_SERVLET_START_UP,ejobConfig));
	}
}

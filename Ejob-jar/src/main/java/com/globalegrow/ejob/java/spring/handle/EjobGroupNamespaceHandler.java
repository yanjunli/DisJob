package com.hqyg.disjob.java.spring.handle;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.hqyg.disjob.java.spring.parser.EjobSpringCfgBeanDefinitionParser;

public class EjobGroupNamespaceHandler extends NamespaceHandlerSupport {
	public void init() {
		registerBeanDefinitionParser("cfg", new EjobSpringCfgBeanDefinitionParser());
 	}

}

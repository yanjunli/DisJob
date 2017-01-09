package com.hqyg.disjob.java.job;

public interface JobProvider {

	public EJob getEjobAction(String className, String methodName) ;
}

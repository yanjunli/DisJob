package com.hqyg.disjob.java.dynamic;

import com.hqyg.disjob.java.core.dispatcher.EventObjectDispatcher;
import com.hqyg.disjob.java.utils.FileUtils;
import com.hqyg.disjob.java.utils.Log;
import com.hqyg.disjob.quence.TaskExecuteException;

public class FileModifyHandler extends FileHandler{
	public FileModifyHandler(String dir,String filePath){
		super(dir,filePath);
	}
	
	@Override
	public void execute(String dir, String fileName) throws TaskExecuteException {
		if(!FileUtils.isJarFile(fileName)){
			return ;
		}
		
		String absPath = "";
		if(dir.endsWith("/")){
			absPath = dir + fileName;
		}else{
			absPath = dir + "/" + fileName ;
		}
		EventObjectDispatcher.dispatcherDynamicJob(absPath);
		Log.debug(getClassName()+"; dir:"+dir+"; file name:"+absPath);
	}

}

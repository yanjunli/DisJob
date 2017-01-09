package com.hqyg.disjob.java.dynamic;

import com.hqyg.disjob.java.utils.Log;
import com.hqyg.disjob.quence.TaskExecuteException;

public class FileDeleteHandler extends FileHandler{

	public FileDeleteHandler(String dir,String filePath){
		super(dir,filePath);
	}
	
	@Override
	public void execute(String dir, String fileName) throws TaskExecuteException {
		
		Log.error(getClassName()+"; dir:"+dir+"; file name:"+fileName);
	}
}

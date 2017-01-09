package com.hqyg.disjob.rpc.utils;

import com.hqyg.disjob.common.util.SpringWorkFactory;
import com.hqyg.disjob.rpc.support.StoreRepThreadPoolService;

public class RpcSpringWorkFactory extends SpringWorkFactory{

	public static StoreRepThreadPoolService getStoreRepThreadPoolService(){
		
		return (StoreRepThreadPoolService) getWorkObject("storeRepThreadPoolService");
	}
	
}

package com.hqyg.disjob.register.rpc;

import java.util.List;

import com.hqyg.disjob.rpc.client.HURL;

/**
 * <pre>
 * 
 *  File: NotifyListener.java
 * 
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2016年5月12日				Disjob				Initial.
 *
 * </pre>
 */
public interface NotifyListener {

    void notify(HURL registryUrl, List<HURL> urls);
}



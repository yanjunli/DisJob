package com.hqyg.disjob.java.core.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.hqyg.disjob.java.EjobConstants;
import com.hqyg.disjob.java.ExecutorBuilder;
import com.hqyg.disjob.java.bean.RpcContainer;
import com.hqyg.disjob.java.event.JobTracker;
import com.hqyg.disjob.java.service.ClientLinkedService;
import com.hqyg.disjob.java.service.JobService;
import com.hqyg.disjob.pojo.ThreadPoolContainer;
import com.hqyg.disjob.quence.Log;

public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
		if(msg==null){
			return ;
		}
		if(msg.getHeader().getType() > 0 && msg.getData()!=null){
			JobTracker jobTracker = JobService.getJobTracker(msg.getData().getClassName());
			if(jobTracker == null){
				return ;
			}
			jobTracker.notifyRpcHandler(new RpcContainer(ctx, msg));//
			if(EjobConstants.isDebug){
				ThreadPoolContainer container = ExecutorBuilder.getJobExecutor().getThreadPollContainer();
				Log.info("thread pool container:"+container.toString());
			}
		}
		ClientLinkedService.putChannel(ctx.channel());
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
		cause.printStackTrace();
		StackTraceElement[] staele = cause.getStackTrace();
		for (StackTraceElement se : staele) {
			se.toString();
		}
		ctx.close();
	}
}

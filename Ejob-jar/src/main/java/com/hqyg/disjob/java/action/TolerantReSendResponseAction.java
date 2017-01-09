package com.hqyg.disjob.java.action;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.List;

import com.hqyg.disjob.java.core.rpc.ReSendFutureListener;
import com.hqyg.disjob.java.core.rpc.RpcResponse;
import com.hqyg.disjob.java.service.ClientLinkedService;
import com.hqyg.disjob.java.service.JobService;
import com.hqyg.disjob.quence.Action;
import com.hqyg.disjob.quence.TaskExecuteException;

public class TolerantReSendResponseAction extends Action{
	private Channel socketChannel ;//优先使用这根管道
	List<RpcResponse> failMsgs ;
	public TolerantReSendResponseAction(Channel channel,List<RpcResponse> failMsgs) {
		this.socketChannel = channel ;
		this.failMsgs = failMsgs;
	}
	@Override
	public void execute() throws TaskExecuteException {
		if(socketChannel == null){
			return ;
		}
		Channel channel = socketChannel;
		if(!channel.isActive()){
			channel = ClientLinkedService.getChannel(ClientLinkedService.getRemoterAddress(channel));
			if(channel == null){
				return ;
			}
		}
		for(RpcResponse rpcResponse : failMsgs){
			if(channel.isActive()){
				ChannelFuture channelFuture = channel.writeAndFlush(rpcResponse);
				channelFuture.addListener(new ReSendFutureListener(rpcResponse,channel));
			}else{
				JobService.putFailRpcResponse(channel, rpcResponse);
			}
		}
	}
}

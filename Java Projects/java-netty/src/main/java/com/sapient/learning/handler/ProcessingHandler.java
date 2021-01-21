package com.sapient.learning.handler;

import com.sapient.learning.data.RequestData;
import com.sapient.learning.data.ResponseData;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		System.out.println("Handler added");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		System.out.println("Handler removed");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		RequestData requestData = (RequestData) msg;

		System.out.println("Request Data: " + requestData.getIntValue());

		ResponseData responseData = new ResponseData();
		responseData.setIntValue(requestData.getIntValue() * 2);
		ChannelFuture future = ctx.writeAndFlush(responseData);
		future.addListener(ChannelFutureListener.CLOSE);

	}
}
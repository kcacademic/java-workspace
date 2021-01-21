package com.sapient.learning.handler;

import com.sapient.learning.data.RequestData;
import com.sapient.learning.data.ResponseData;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		RequestData msg = new RequestData();
		msg.setIntValue(123);
		msg.setStringValue("all work and no play makes jack a dull boy");
		ctx.writeAndFlush(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ResponseData responseData = (ResponseData) msg;
		System.out.println("Response Data: " + responseData.getIntValue());
		ctx.close();
	}
}
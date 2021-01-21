package com.sapient.learning.decoder;

import java.util.List;

import com.sapient.learning.data.ResponseData;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class ResponseDataDecoder extends ReplayingDecoder<ResponseData> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		ResponseData data = new ResponseData();
		data.setIntValue(in.readInt());
		out.add(data);
	}
}

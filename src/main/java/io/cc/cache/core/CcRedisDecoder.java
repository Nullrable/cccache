package io.cc.cache.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class CcRedisDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf in, final List<Object> list) throws Exception {

        if(in.readableBytes() <= 0) return;
        int count = in.readableBytes();
        int index = in.readerIndex();
        System.out.println("count:" + count + ",index:" + index);

        byte[] bytes = new byte[count];
        in.readBytes(bytes);
        String ret = new String(bytes);
        System.out.println("ret: \r\n" + ret);

        list.add(ret);
    }
}

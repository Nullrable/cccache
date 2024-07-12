package io.cc.cache.reply;

import io.cc.cache.core.Reply;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nhsoft.lsd
 */
public class IntegerReply extends Reply<Integer> {

    public IntegerReply(final Integer value) {
        super(value);
    }

    @Override
    public void execute(final ChannelHandlerContext ctx) {
        integer(ctx, content);
    }
}

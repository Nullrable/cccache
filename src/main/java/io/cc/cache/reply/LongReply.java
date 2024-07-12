package io.cc.cache.reply;

import io.cc.cache.core.Reply;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nhsoft.lsd
 */
public class LongReply extends Reply<Long> {

    public LongReply(final Long value) {
        super(value);
    }

    @Override
    public void execute(final ChannelHandlerContext ctx) {
        longer(ctx, content);
    }
}

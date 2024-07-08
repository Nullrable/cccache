package io.cc.cache.reply;

import io.cc.cache.core.Reply;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nhsoft.lsd
 */
public class BlukStringReply extends Reply<String> {

    public BlukStringReply(final String content) {
        super(content);
    }

    @Override
    public void execute(final ChannelHandlerContext ctx) {
        bulkString(ctx, content);
    }
}

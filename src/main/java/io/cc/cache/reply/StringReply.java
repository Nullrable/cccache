package io.cc.cache.reply;

import io.cc.cache.core.Reply;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nhsoft.lsd
 */
public class StringReply extends Reply<String> {

    public StringReply(final String content) {
        super(content);
    }

    @Override
    public void execute(final ChannelHandlerContext ctx) {
        simpleString(ctx, content);
    }
}

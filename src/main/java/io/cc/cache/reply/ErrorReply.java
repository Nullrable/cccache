package io.cc.cache.reply;

import io.cc.cache.core.Reply;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nhsoft.lsd
 */
public class ErrorReply extends Reply<String> {

    public ErrorReply() {
        super(null);
    }

    public ErrorReply(final String content) {
        super(content);
    }

    @Override
    public void execute(final ChannelHandlerContext ctx) {
        if (content == null) {
            error(ctx);
        } else  {
            error(ctx, content);
        }
    }
}

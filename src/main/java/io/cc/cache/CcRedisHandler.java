package io.cc.cache;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Commands;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ErrorReply;
import io.cc.cache.reply.StringReply;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author nhsoft.lsd
 */
public class CcRedisHandler extends SimpleChannelInboundHandler<String> {

    private CcCache cache = new CcCache();

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final String string) throws Exception {

        String[] args = string.split("\r\n");

        String command = args[2];

        Command commander = Commands.get(command);

        try {
            if (commander != null) {
                Reply<?> reply = commander.execute(cache, args);
                reply.execute(ctx);
            } else {
                new StringReply("OK").execute(ctx);
            }
        } catch (Exception e) {
            new ErrorReply(e.getMessage()).execute(ctx);
        }
    }
}

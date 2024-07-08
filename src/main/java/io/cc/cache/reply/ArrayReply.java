package io.cc.cache.reply;

import io.cc.cache.core.Reply;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/**
 * @author nhsoft.lsd
 */

public class ArrayReply extends Reply<List<String>> {

    public ArrayReply(List<String> value) {
       super(value);
    }

    @Override
    public void execute(final ChannelHandlerContext ctx) {
        array(ctx, content);
    }
}

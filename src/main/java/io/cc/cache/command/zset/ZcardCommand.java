package io.cc.cache.command.zset;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class ZcardCommand implements Command {
    @Override
    public String getName() {
        return "ZCARD";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        int size = cache.zcard(key);
        return new IntegerReply(size);
    }
}

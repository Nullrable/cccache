package io.cc.cache.command.hash;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class HlenCommand implements Command {
    @Override
    public String getName() {
        return "HLEN";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        int ret = cache.hlen(key);
        return new IntegerReply(ret);
    }
}

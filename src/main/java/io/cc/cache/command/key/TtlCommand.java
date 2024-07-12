package io.cc.cache.command.key;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.LongReply;

/**
 * @author nhsoft.lsd
 */
public class TtlCommand implements Command {
    @Override
    public String getName() {
        return "TTL";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        return new LongReply(cache.ttl(key));
    }
}

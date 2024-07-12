package io.cc.cache.command.key;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.LongReply;

/**
 * @author nhsoft.lsd
 */
public class PttlCommand implements Command {
    @Override
    public String getName() {
        return "PTTL";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];

        return new LongReply(cache.pttl(key));
    }
}

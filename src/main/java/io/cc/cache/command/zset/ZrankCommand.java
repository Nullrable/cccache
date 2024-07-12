package io.cc.cache.command.zset;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class ZrankCommand implements Command {
    @Override
    public String getName() {
        return "ZRANK";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        String member = args[6];

        Integer rank = cache.zrank(key, member);

        return new IntegerReply(rank);
    }
}

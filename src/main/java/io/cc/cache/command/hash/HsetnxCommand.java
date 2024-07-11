package io.cc.cache.command.hash;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class HsetnxCommand implements Command {
    @Override
    public String getName() {
        return "HSETXN";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        String field = args[6];
        String value = args[8];
        int ret = cache.hsetnx(key, field, value);
        return new IntegerReply(ret);
    }
}

package io.cc.cache.command.hash;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class HexistsCommand implements Command {
    @Override
    public String getName() {
        return "HEXISTS";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        String field = args[6];
        int ret = cache.hexists(key, field);
        return new IntegerReply(ret);
    }
}

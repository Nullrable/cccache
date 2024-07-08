package io.cc.cache.command;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class ExistsCommand implements Command {

    public static final String NAME = "EXISTS";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        boolean exists = cache.containsKey(key);
        int ret = exists ? 1 : 0;
        return new IntegerReply(ret);
    }
}

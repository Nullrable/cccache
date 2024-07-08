package io.cc.cache.command;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class InrcCommand implements Command {

    public static final String NAME = "INCR";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        return new IntegerReply(cache.incr(key));
    }
}

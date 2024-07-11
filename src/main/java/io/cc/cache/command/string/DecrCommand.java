package io.cc.cache.command.string;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class DecrCommand implements Command {

    public static final String NAME = "DECR";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        return new IntegerReply(cache.decr(key));
    }
}

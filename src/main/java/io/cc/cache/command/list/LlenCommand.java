package io.cc.cache.command.list;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class LlenCommand implements Command {

    public static final String NAME = "LLEN";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];

        int size = cache.llen(key);

        return new IntegerReply(size);
    }
}

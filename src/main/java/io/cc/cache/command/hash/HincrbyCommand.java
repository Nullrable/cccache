package io.cc.cache.command.hash;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class HincrbyCommand implements Command {
    @Override
    public String getName() {
        return "HINCRBY";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        String field = args[6];
        String value = args[8];

        int ret = cache.hincrby(key, field, Integer.parseInt(value));
        return new IntegerReply(ret);
    }
}

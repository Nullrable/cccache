package io.cc.cache.command.hash;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;

/**
 * @author nhsoft.lsd
 */
public class HgetCommand implements Command {
    @Override
    public String getName() {
        return "HGET";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        String field = args[6];

        String ret = cache.hget(key, field);

        return new BlukStringReply(ret);
    }
}

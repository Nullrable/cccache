package io.cc.cache.command.string;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;

/**
 * @author nhsoft.lsd
 */
public class GetSetCommand implements Command {
    @Override
    public String getName() {
        return "GETSET";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        String value = args[6];
        String oldValue = cache.getset(key, value);
        return new BlukStringReply(oldValue);
    }
}

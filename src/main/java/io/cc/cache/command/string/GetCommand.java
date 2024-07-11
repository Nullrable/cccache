package io.cc.cache.command.string;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;

/**
 * @author nhsoft.lsd
 */
public class GetCommand implements Command {

    public static final String NAME = "GET";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];

        String value = cache.get(key);
        return new BlukStringReply(value);
    }
}

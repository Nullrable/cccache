package io.cc.cache.command.string;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class StrlenCommand implements Command {

    public static final String NAME = "STRLEN";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        String value = cache.get(key);
        int len = value == null ? 0 : value.length();
        return new IntegerReply(len);
    }
}

package io.cc.cache.command.hash;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.StringReply;

/**
 * @author nhsoft.lsd
 */
public class HsetCommand implements Command {
    @Override
    public String getName() {
        return "HSET";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        String field = args[6];
        String value = args[8];
        cache.hset(key, field, value);
        return new StringReply("OK");
    }
}

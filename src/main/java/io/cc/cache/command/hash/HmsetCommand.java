package io.cc.cache.command.hash;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.StringReply;

/**
 * @author nhsoft.lsd
 */
public class HmsetCommand implements Command {
    @Override
    public String getName() {
        return "HMSET";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];

        for (int i = 6; i < args.length; i+=4) {
            String field = args[i];
            String value = args[i + 2];
            cache.hset(key, field, value);
        }

        return new StringReply("OK");
    }
}

package io.cc.cache.command.zset;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class ZaddCommand implements Command {
    @Override
    public String getName() {
        return "ZADD";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];

        int count = 0;
        for (int i = 6; i < args.length; i+=4) {
            int ret = cache.zadd(key, new Cache.ZsetEntry(Double.parseDouble(args[i]), args[i + 2]));
            count = count + ret;
        }

        return new IntegerReply(count);
    }
}

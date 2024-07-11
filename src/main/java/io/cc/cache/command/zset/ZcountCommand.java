package io.cc.cache.command.zset;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class ZcountCommand implements Command {
    @Override
    public String getName() {
        return "ZCOUNT";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        double min = Double.parseDouble(args[6]);
        double max = Double.parseDouble(args[8]);
        int count = cache.zcount(key, min, max);
        return new IntegerReply(count);
    }
}

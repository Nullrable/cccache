package io.cc.cache.command.key;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class ExpireAtCommand implements Command {
    @Override
    public String getName() {
        return "EXPIREAT";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        long expireAt = Long.parseLong(args[6]);
        int ret = cache.setExpireAt(key, expireAt);
        return new IntegerReply(ret);
    }
}

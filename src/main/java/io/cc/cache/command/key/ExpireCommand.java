package io.cc.cache.command.key;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;
import java.util.concurrent.TimeUnit;

/**
 * @author nhsoft.lsd
 */
public class ExpireCommand implements Command {

    @Override
    public String getName() {
        return "EXPIRE";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        long expire = Long.parseLong(args[6]);

        int ret = cache.setExpire(key, expire, TimeUnit.SECONDS);
        return new IntegerReply(ret);
    }
}

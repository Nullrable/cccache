package io.cc.cache.command.string;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.StringReply;
import java.util.concurrent.TimeUnit;

/**
 * @author nhsoft.lsd
 */
public class SetExCommand implements Command {
    @Override
    public String getName() {
        return "SETEX";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        long expire = Long.parseLong(args[6]);
        String value = args[8];

        cache.set(key, value);
        cache.setExpire(key, expire, TimeUnit.SECONDS);

        return new StringReply("OK");
    }
}

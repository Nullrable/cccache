package io.cc.cache.command.string;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class SetNxCommand implements Command {
    @Override
    public String getName() {
        return "SETNX";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        String value = args[6];

        if (cache.exists(key) ){
            return new IntegerReply(0);
        }

        cache.set(key, value);

        return new IntegerReply(1);
    }
}

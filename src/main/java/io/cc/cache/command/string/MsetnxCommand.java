package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class MsetnxCommand implements Command {

    @Override
    public String getName() {
        return "MSETNX";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        for (int i = 4; i < args.length; i += 4) {
            String key = args[i];
            if (cache.exists(key) ){
               return new IntegerReply(0);
            }
        }

        for (int i = 4; i < args.length; i += 4) {
            String key = args[i];
            String value = args[i + 2];
            cache.set(key, value);
        }

        return new IntegerReply(1);
    }
}

package io.cc.cache.command.hash;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class HdelCommand implements Command {
    @Override
    public String getName() {
        return "HDEL";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];

        int counter = 0;
        for (int i = 6; i < args.length; i += 2) {
            String field = args[i];
            int ret = cache.hdel(key, field);

            counter = counter + ret;

        }
        return new IntegerReply(counter);
    }
}

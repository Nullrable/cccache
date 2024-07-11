package io.cc.cache.command.zset;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.StringReply;

/**
 * @author nhsoft.lsd
 */
public class ZincrbyCommand implements Command {
    @Override
    public String getName() {
        return "ZINCRBY";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        double increment = Double.parseDouble(args[6]);
        String member = args[8];

        double score = cache.zincrby(key, increment, member);

        return new StringReply(String.valueOf(score));
    }
}

package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class AppendCommand implements Command {
    @Override
    public String getName() {
        return "APPEND";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        String value = args[6];
        int len = cache.append(key, value);
        return new IntegerReply(len);
    }
}

package io.cc.cache.command.hash;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class HvalsCommand implements Command
{
    @Override
    public String getName() {
        return "HVALS";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];
        List<String> vals = cache.hvals(key);
        return new ArrayReply(vals);
    }
}

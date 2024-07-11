package io.cc.cache.command.set;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author nhsoft.lsd
 */
public class SmembersCommand implements Command {
    @Override
    public String getName() {
        return "SMEMBERS";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        Set<String> sets = cache.smembers(key);
        return new ArrayReply(new ArrayList<>(sets));
    }
}

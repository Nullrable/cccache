package io.cc.cache.command.set;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;

/**
 * @author nhsoft.lsd
 */
public class SpopCommand implements Command {
    @Override
    public String getName() {
        return "SPOP";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        String key = args[4];
        String val = cache.spop(key);
        return new BlukStringReply(val);
    }
}

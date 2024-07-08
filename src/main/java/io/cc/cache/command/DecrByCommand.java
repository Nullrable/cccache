package io.cc.cache.command;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ErrorReply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class DecrByCommand implements Command {

    public static final String NAME = "DECRBY";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        if (args.length < 6) {
            return new ErrorReply("ERR wrong number of arguments for 'decrby' command");
        }
        String key = args[4];
        String val = args[6];
        return new IntegerReply(cache.decrby(key, val));
    }
}

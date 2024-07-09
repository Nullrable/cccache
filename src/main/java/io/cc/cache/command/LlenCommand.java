package io.cc.cache.command;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;
import io.cc.cache.reply.ErrorReply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class LlenCommand implements Command {

    public static final String NAME = "LLEN";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        if (args.length <= 4) {
            return new ErrorReply("ERR wrong number of arguments for 'llen' command");
        }

        String key = args[4];

        int size = cache.llen(key);

        return new IntegerReply(size);
    }
}

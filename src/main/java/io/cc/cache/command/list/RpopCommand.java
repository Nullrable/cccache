package io.cc.cache.command.list;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;
import io.cc.cache.reply.ErrorReply;

/**
 * @author nhsoft.lsd
 */
public class RpopCommand implements Command {

    public static final String NAME = "RPOP";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        String key = args[4];

        String value = cache.rpop(key);

        if (value == null) {
            return new ErrorReply();
        }
        return new BlukStringReply(value);
    }
}

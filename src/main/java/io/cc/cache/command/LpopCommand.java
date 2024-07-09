package io.cc.cache.command;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;
import io.cc.cache.reply.ErrorReply;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class LpopCommand implements Command {

    public static final String NAME = "LPOP";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        if (args.length <= 4) {
            return new ErrorReply("ERR wrong number of arguments for 'lpop' command");
        }

        String key = args[4];

        String value = cache.lpop(key);

        if (value == null) {
            return new ErrorReply();
        }
        return new BlukStringReply(value);
    }
}

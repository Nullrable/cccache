package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;
import io.cc.cache.reply.ErrorReply;

/**
 * @author nhsoft.lsd
 */
public class GetSetCommand implements Command {
    @Override
    public String getName() {
        return "GETSET";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        if (args.length <= 6) {
            return new ErrorReply("ERR wrong number of arguments for 'getset' command");
        }
        String key = args[4];
        String value = args[6];
        String oldValue = cache.getset(key, value);
        return new BlukStringReply(oldValue);
    }
}

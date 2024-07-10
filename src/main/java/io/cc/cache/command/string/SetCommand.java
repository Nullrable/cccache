package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ErrorReply;
import io.cc.cache.reply.StringReply;

/**
 * @author nhsoft.lsd
 */
public class SetCommand implements Command {

    public static final String NAME = "SET";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        if (args.length <= 6) {
           return new ErrorReply("ERR wrong number of arguments for 'set' command");
        }
        String key = args[4];
        String value = args[6];
        cache.set(key, value);
        return new StringReply("OK");
    }
}

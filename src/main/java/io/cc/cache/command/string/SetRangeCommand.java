package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ErrorReply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class SetRangeCommand implements Command {
    @Override
    public String getName() {
        return "SETRANGE";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        if (args.length <= 6) {
            return new ErrorReply("ERR wrong number of arguments for 'set' command");
        }
        String key = args[4];
        int start = Integer.parseInt(args[6]);
        String value = args[8];
        int len = cache.setrange(key, value, start);
        return new IntegerReply(len);
    }
}

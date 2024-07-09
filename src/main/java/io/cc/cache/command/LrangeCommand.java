package io.cc.cache.command;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import io.cc.cache.reply.BlukStringReply;
import io.cc.cache.reply.ErrorReply;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class LrangeCommand implements Command {

    public static final String NAME = "LRANGE";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        if (args.length <= 8) {
            return new ErrorReply("ERR wrong number of arguments for 'lrange' command");
        }

        String key = args[4];

        int start = Integer.parseInt(args[6]);

        int end = Integer.parseInt(args[8]);

        List<String> values = cache.lrange(key, start, end);

        if (values == null) {
            return new ErrorReply();
        }
        return new ArrayReply(values);
    }
}

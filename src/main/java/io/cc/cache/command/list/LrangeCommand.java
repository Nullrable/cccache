package io.cc.cache.command.list;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
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
    public Reply<?> execute(final Cache cache, final String[] args) {

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

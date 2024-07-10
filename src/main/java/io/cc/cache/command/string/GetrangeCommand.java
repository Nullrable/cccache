package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;

/**
 * @author nhsoft.lsd
 */
public class GetrangeCommand implements Command {
    @Override
    public String getName() {
        return "GETRANGE";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];

        int start = Integer.parseInt(args[6]);

        int end = Integer.parseInt(args[8]);

        String value = cache.getrange(key, start, end);

        return new BlukStringReply(value);
    }
}

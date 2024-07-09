package io.cc.cache.command;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ErrorReply;
import io.cc.cache.reply.IntegerReply;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class RpushCommand implements Command {

    public static final String NAME = "RPUSH";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        if (args.length <= 6) {
            return new ErrorReply("ERR wrong number of arguments for 'rpush' command");
        }

        String key = args[4];

        List<String> values = new ArrayList<>();
        for (int i = 6; i < args.length; i = i + 2) {
            values.add(args[i]);
        }

        int size = cache.rpush(key, values.toArray(new String[0]));
        return new IntegerReply(size);
    }
}

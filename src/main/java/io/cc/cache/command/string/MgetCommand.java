package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class MgetCommand implements Command {

    @Override
    public String getName() {
        return "MGET";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        List<String> values = new LinkedList<>();
        for (int i = 4; i < args.length; i += 2) {
            String key = args[i];
            String value = cache.get(key);
            values.add(value);
        }
        return new ArrayReply(values);
    }
}

package io.cc.cache.command.zset;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class ZinterstoreCommand implements Command {
    @Override
    public String getName() {
        return "ZINTERSTORE";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String destination = args[4];
        int numkeys = Integer.parseInt(args[6]);

        List<String> keys = new ArrayList<>();
        for (int i = 8; i < args.length; i += 2) {
            keys.add(args[i]);
        }
        int ret = cache.zinterstore(destination, numkeys, keys.toArray(new String[0]));

        return new IntegerReply(ret);
    }
}

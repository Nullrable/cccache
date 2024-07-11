package io.cc.cache.command.hash;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class HmgetCommand implements Command {
    @Override
    public String getName() {
        return "HMGET";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];

        List<String> list = new ArrayList<>();
        for (int i = 6; i < args.length; i+=2) {
            String field = args[i];
            list.add(cache.hget(key, field));
        }
        return new ArrayReply(list);
    }
}

package io.cc.cache.command.set;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class SaddCommand implements Command {
    @Override
    public String getName() {
        return "SADD";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String key = args[4];

        List<String> members = new ArrayList<>();
        for (int i = 6; i < args.length; i+=2) {
            members.add(args[i]);
        }

        int count = cache.sadd(key, members.toArray(new String[0]));

        return new IntegerReply(count);
    }
}

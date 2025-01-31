package io.cc.cache.command.set;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author nhsoft.lsd
 */
public class SinterStoreCommand implements Command {
    @Override
    public String getName() {
        return "SINTERSTORE";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String destination = args[4];

        List<String> keys = new ArrayList<>();
        for (int i = 6; i < args.length; i += 2) {
            keys.add(args[i]);
        }
        Set<String> diffs = cache.sinterstore(destination, keys.toArray(new String[0]));
        return new ArrayReply(new ArrayList<>(diffs));
    }
}

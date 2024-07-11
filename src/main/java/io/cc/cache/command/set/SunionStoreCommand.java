package io.cc.cache.command.set;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class SunionStoreCommand implements Command {
    @Override
    public String getName() {
        return "SUNIONSTORE";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        String destination = args[4];
        List<String> keys = new ArrayList<>();
        for (int i = 6; i < args.length; i += 2) {
            keys.add(args[i]);
        }
        int ret = cache.sunionstore(destination, keys.toArray(new String[0]));
        return new IntegerReply(ret);
    }
}

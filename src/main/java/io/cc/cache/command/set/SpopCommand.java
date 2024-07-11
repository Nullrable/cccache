package io.cc.cache.command.set;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import io.cc.cache.reply.BlukStringReply;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class SpopCommand implements Command {
    @Override
    public String getName() {
        return "SPOP";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        if (args.length > 6) {
            int count = Integer.parseInt(args[6]);
            List<String> val = cache.spop(key, count);
            return new ArrayReply(val);
        } else {
            List<String> val = cache.spop(key, 1);
            return new BlukStringReply(val == null ? null : val.get(0));
        }
    }
}

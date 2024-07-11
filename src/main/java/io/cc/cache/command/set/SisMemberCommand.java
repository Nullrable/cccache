package io.cc.cache.command.set;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class SisMemberCommand implements Command {
    @Override
    public String getName() {
        return "SISMEMBER";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        String member = args[6];

        int ret = cache.sismember(key, member);

        return new IntegerReply(ret);
    }
}
